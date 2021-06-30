package com.ustcat.ustcatEmergency.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthKeyDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 简单查询区
    public List<Map<String, Object>> queryByValue(String value) {
        String sql = "select * from auth_key where auth_key.value=?";
        List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList(sql, value);
        return (list);
    }

}