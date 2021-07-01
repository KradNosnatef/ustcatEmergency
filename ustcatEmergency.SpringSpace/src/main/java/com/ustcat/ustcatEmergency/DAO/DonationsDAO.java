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
public class DonationsDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //简单查询区
    public List<Map<String,Object>> queryByDONID(int donid){
        String sql="select donations.*,bills.value,bills.remark,bills.time from donations,bills where donations.bid=bills.bid and donations.donid=?;";

        return(jdbcTemplate.queryForList(sql,donid));
    }
    public List<Map<String,Object>> queryByUsername(String username){
        String sql="select donations.*,bills.value,bills.remark,bills.time from donations,bills where donations.bid=bills.bid and donations.username=?;";

        return(jdbcTemplate.queryForList(sql,username));
    }
    public List<Map<String,Object>> listAll(){
        String sql="select donations.*,bills.value,bills.remark,bills.time from donations,bills where donations.bid=bills.bid;";

        return(jdbcTemplate.queryForList(sql));
    }

    //自定义功能区
    public void importDonation(String username,int value,String remark)throws DataAccessException{//收入
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

        String sql2="insert into donations (bid,username) values (?,?);";
        jdbcTemplate.update(sql2,bid,username);
    }

    public void exportDonation(String username,int value,String remark)throws DataAccessException{//支出，value填正整数
        importDonation(username, -value, remark);
    }
}
