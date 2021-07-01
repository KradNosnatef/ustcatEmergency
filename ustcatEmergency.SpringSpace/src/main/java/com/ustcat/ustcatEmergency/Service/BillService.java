package com.ustcat.ustcatEmergency.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ustcat.ustcatEmergency.DAO.BillsDAO;
import com.ustcat.ustcatEmergency.DAO.DebtsDAO;
import com.ustcat.ustcatEmergency.DAO.DonationsDAO;
import com.ustcat.ustcatEmergency.Entity.DebtEntity;
import com.ustcat.ustcatEmergency.ToolKit.IOToolKits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("BillService")
public class BillService {
    @Autowired
    BillsDAO billsDAO;
    @Autowired
    DebtsDAO debtsDAO;
    @Autowired
    DonationsDAO donationsDAO;

    //非军事区
    public void readyToPayForAdvance(int value)throws DataAccessException{//声明预垫付
        String username=IOToolKits.getName();

        debtsDAO.insert(username,false,null,value);
    }

    public List<DebtEntity> listAllArrivedDebts(){//列出所有实垫付
        List<DebtEntity> list=new ArrayList<DebtEntity>();
        DebtEntity debtEntity;
        List<Map<String,Object>> lMaps=debtsDAO.listAllArrived();
        for(int i=0;i<=lMaps.size()-1;i++){
            debtEntity=new DebtEntity(lMaps.get(i));
            debtEntity.setArrivedParam(lMaps.get(i));
            list.add(debtEntity);
        }
        return(list);
    }

    public int sumAllArrivedDebts(){//求实垫付总额
        return(debtsDAO.sumAllArrived());
    }

    public List<DebtEntity> listAllUnarrivedDebts(){//列出所有预垫付
        List<DebtEntity> list=new ArrayList<DebtEntity>();
        DebtEntity debtEntity;
        List<Map<String,Object>> lMaps=debtsDAO.listAllUnarrived();
        for(int i=0;i<=lMaps.size()-1;i++){
            debtEntity=new DebtEntity(lMaps.get(i));
            list.add(debtEntity);
        }
        return(list);
    }

    public int sumAllUnarrivedDebts(){//求预垫付总额
        return(debtsDAO.sumAllUnarrived());
    }

    public Map<String,List<DebtEntity>> listAllDebts(){//利用map列出所有垫付单
        
        Map<String,List<DebtEntity>> map=new HashMap<>();
        List<DebtEntity> list=listAllArrivedDebts();
        map.put("arrived", list);
        list=listAllUnarrivedDebts();
        map.put("unarrived", list);

        return(map);
    }


    //特权区
    public void payForAdvance(int value,String username,UserService userService)throws DataAccessException{//声明实垫付,仅限管理员,填入垫付人username
        if(!userService.adminCheck())return;

        debtsDAO.insert(username, true, username+"垫付的款项", value);
    }
    
    public void changeReadyToReality(int debid,UserService userService)throws DataAccessException{//预垫付转入实垫付，仅限管理员
        if(!userService.adminCheck())return;

        List<Map<String,Object>> list=debtsDAO.queryByDEBID(debid);
        String username=list.get(0).get("username").toString();
        int value=Integer.parseInt(list.get(0).get("payoff").toString());
        debtsDAO.delete(debid);

        payForAdvance(value, username, userService);
    }

    public void income(int value,String remark,UserService userService)throws DataAccessException{//收入，仅限管理员
        if(!userService.adminCheck())return;

        donationsDAO.importDonation(IOToolKits.getName(), value, remark);
    }

    public void outcome(int value,String remark,UserService userService)throws DataAccessException{//支出，仅限管理员
        if(!userService.adminCheck())return;

        donationsDAO.exportDonation(IOToolKits.getName(), value, remark);
    }
}
