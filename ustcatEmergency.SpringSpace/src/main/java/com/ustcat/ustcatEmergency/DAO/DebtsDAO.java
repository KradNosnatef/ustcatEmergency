package com.ustcat.ustcatEmergency.DAO;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DebtsDAO {
    //属性字段解释：
    //arrive为真代表这是一笔已经存进协会的垫付款，为假代表这笔钱可以用来垫付但是还没存进协会
    //payoff在arrive为真时代表已经归还的垫付款数目，在arrive为假时代表可以用来垫付的垫付款数量（正数）
    //预垫付转为实垫付时采取消去预垫付单再插入实垫付单的方法
    //实垫付还清后应当删去对应垫付单条项，保留账单条项


    @Autowired
    JdbcTemplate jdbcTemplate;

    //简单查询区
    public List<Map<String,Object>> listAllArrived(){//列出所有实垫付单
        String sql="select debts.*,bills.value,bills.time,bills.remark from debts,bills where debts.arrive=true and debts.bid=bills.bid";
        
        return(jdbcTemplate.queryForList(sql));
    }
    public int sumAllArrived(){//求所有实垫付单的value
        String sql="select sum(bills.value) as sum from debts,bills where debts.arrive=true and debts.bid=bills.bid";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);

        if(list.get(0).get("sum")==null)return(0);
        else return(Integer.parseInt(list.get(0).get("sum").toString()));
    }


    public List<Map<String,Object>> listAllUnarrived(){//列出所有预垫付单
        String sql="select debts.* from debts where debts.arrive=false";
        
        return(jdbcTemplate.queryForList(sql));
    }
    public int sumAllUnarrived(){//求所有预垫付单的value
        String sql="select sum(debts.payoff) as sum from debts where debts.arrive=false";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);

        if(list.get(0).get("sum")==null)return(0);
        else return(Integer.parseInt(list.get(0).get("sum").toString()));
    }

    public List<Map<String,Object>> queryByDEBID(int debid){
        String sql="select debts.* from debts where debts.debid=?;";

        return(jdbcTemplate.queryForList(sql,debid));
    }
    public List<Map<String,Object>> queryByUsername(String username){
        String sql="select debts.* from debts where debts.username=?;";

        return(jdbcTemplate.queryForList(sql,username));
    }

    //自定义功能区
    public int queryDebtSumRest(){//返回还差多少垫款要还
        String sql="select sum(bills.value) as sum from debts,bills where debts.arrive=true and debts.bid=bills.bid";
        int debtSum=Integer.parseInt(jdbcTemplate.queryForList(sql).get(0).get("sum").toString());

        sql="select sum(debts.payoff) as sum from debts where debts.arrive=true";
        int payoffSum=Integer.parseInt(jdbcTemplate.queryForList(sql).get(0).get("sum").toString());

        return(debtSum-payoffSum);
    }

    public void insert(String username,boolean arrive,String remark,int value)throws DataAccessException{//建立垫付单
        if(arrive){
            KeyHolder keyHolder=new GeneratedKeyHolder();
            String sql="insert into bills (value,remark) values (?,?);";
            PreparedStatementCreator preparedStatementCreator=con->{
                PreparedStatement preparedStatement=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, value);
                preparedStatement.setString(2, remark);
                return(preparedStatement);
            };

            jdbcTemplate.update(preparedStatementCreator, keyHolder);
            int bid=keyHolder.getKey().intValue();

            String sql2="insert into debts (bid,username,arrive,payoff) values (?,?,true,0);";
            jdbcTemplate.update(sql2,bid,username);
        }
        else{
            String sql="insert into debts (bid,username,arrive,payoff) values (NULL,?,false,?);";
            jdbcTemplate.update(sql,username,value);
        }
    }

    public void delete(int debid)throws DataAccessException{//危险的特权操作！！会没有任何检查地删去条项，调用前必须做好相关保护
        String sql="delete from debts where debts.debid=?;";

        jdbcTemplate.update(sql);
    }
}
