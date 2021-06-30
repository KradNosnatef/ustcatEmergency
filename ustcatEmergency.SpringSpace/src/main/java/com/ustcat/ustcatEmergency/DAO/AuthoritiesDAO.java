package com.ustcat.ustcatEmergency.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthoritiesDAO {
    @Autowired
    public JdbcTemplate jdbcTemplate;
    //简单查询区
    public List<Map<String, Object>> queryByUID(String uid) {
        String sql = "select authorities.authority from authorities,users where users.uid=? and users.username=authorities.username";
        List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList(sql, uid);
        return (list);
    }
    public List<Map<String, Object>> queryByUsername(String username) {
        String sql = "select authorities.authority from authorities where authorities.username=?";
        List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList(sql, username);
        return (list);
    }
    public List<Map<String,Object>> queryByAuthority(String authority){
        String sql="select authorities.username from authorities where authorities.authority=?";
        List<Map<String,Object>>list;
        list=jdbcTemplate.queryForList(sql,authority);
        return(list);
    }
    //自定义功能区
}
