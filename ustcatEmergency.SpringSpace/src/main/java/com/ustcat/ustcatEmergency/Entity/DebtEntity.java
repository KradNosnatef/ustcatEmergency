package com.ustcat.ustcatEmergency.Entity;

import java.util.Map;

public class DebtEntity {
    public String debid, bid, username, arrive, payoff;
    public String value=null, time=null, remark=null;

    public DebtEntity(Map<String, Object> map) {
        debid = map.get("debid").toString();
        username = map.get("username").toString();
        arrive = map.get("arrive").toString();
        payoff = map.get("payoff").toString();

    }

    public void setArrivedParam(Map<String, Object> map) {

        bid = map.get("bid").toString();
        value = map.get("value").toString();
        time = map.get("time").toString();
        remark = map.get("remark").toString();
    }
}
