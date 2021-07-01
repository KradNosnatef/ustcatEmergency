package com.ustcat.ustcatEmergency.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillsDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //简单查询区
    public List<Map<String,Object>> listAll(){//列出所有账单
        String sql="select bills.* from bills";
        
        return(jdbcTemplate.queryForList(sql));
    }

    public List<Map<String,Object>> querySummary(){//求和
        String sql="select sum(value) as sum from bills";

        return(jdbcTemplate.queryForList(sql));
    }

}
