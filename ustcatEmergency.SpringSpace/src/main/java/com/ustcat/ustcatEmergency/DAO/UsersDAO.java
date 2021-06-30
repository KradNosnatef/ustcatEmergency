package com.ustcat.ustcatEmergency.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    //简单查询区
    public List<Map<String, Object>> queryByUsername(String username) {
        String sql = "select users.username,users.uid from users where users.username=?";
        List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList(sql, username);
        return (list);
    }
    // 自定义功能区
    public int userRegister(String username, String password, int mode) {// mode 0注册为用户，mode 1注册为管理员
        String sql = "insert into users (username,password,enabled) values (?,?,1);";

        sql += "insert into authorities (username,authority) values (?,'ROLE_USER');";
        if (mode == 1) {
            sql += "insert into authorities (username,authority) values (?,'ROLE_ADMIN');";
            jdbcTemplate.update(sql, username, password, username, username);
        } else {
            jdbcTemplate.update(sql, username, password, username);
        }
        return (0);
    }
}
