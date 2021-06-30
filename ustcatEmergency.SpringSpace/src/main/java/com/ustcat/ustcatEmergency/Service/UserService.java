package com.ustcat.ustcatEmergency.Service;

import java.util.List;
import java.util.Map;

import com.ustcat.ustcatEmergency.DAO.AuthKeyDAO;
import com.ustcat.ustcatEmergency.DAO.AuthoritiesDAO;
import com.ustcat.ustcatEmergency.DAO.UsersDAO;
import com.ustcat.ustcatEmergency.ToolKit.IOToolKits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService {
    @Autowired
    UsersDAO usersDAO;
    @Autowired
    AuthoritiesDAO authoritiesDAO;
    @Autowired
    AuthKeyDAO authKeyDAO;

    public boolean adminCheck(){
        List<Map<String,Object>> list=authoritiesDAO.queryByUsername(IOToolKits.getName());
        for(int i=0;i<=list.size()-1;i++){
            String authority=list.get(i).get("authority").toString();
            if(authority.compareTo("ROLE_ADMIN")==0)return(true);
        }
        return(false);
    }
    
    public int register(String username, String password, String authKey) {// 成功返回0，因登录名重复失败返回1，授权码不正确返回2，其它问题返回-1
        

        // 诸检查
        List<Map<String, Object>> result = usersDAO.queryByUsername(username);
        if (result.size() != 0)
            return (1);

        result = authKeyDAO.queryByValue(authKey);
        if (result.size() == 0){
            System.out.println("authKey is:"+authKey);
            if(authKey.compareTo("null")!=0)return(2);
            String userPassword = "{noop}" + password;
            usersDAO.userRegister(username, userPassword, 0);
        }
        else{
            String userPassword = "{noop}" + password;
            usersDAO.userRegister(username, userPassword, 1);

        }

        return (0);
    }
}
