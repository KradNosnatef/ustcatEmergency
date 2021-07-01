package com.ustcat.ustcatEmergency.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ustcat.ustcatEmergency.Entity.DebtEntity;
import com.ustcat.ustcatEmergency.ToolKit.IOToolKits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Xapi/Bill")
public class BillServiceController {
    @Autowired
    BillService billService;
    @Autowired
    UserService userService;

    @RequestMapping("/listAllDebts")
    public @ResponseBody JSONPObject IOlistAllArrivedDebts(HttpServletResponse httpServletResponse) {
        Map<String,List<DebtEntity>> response=billService.listAllDebts();

        return(IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOlistAllDebts"));
    }

    @RequestMapping("/sumDebts")
    public @ResponseBody JSONPObject IOsumDebts(HttpServletResponse httpServletResponse){
        Map<String,Integer> response=new HashMap<>();
        response.put("arrived", billService.sumAllArrivedDebts());
        response.put("unarrived", billService.sumAllUnarrivedDebts());

        return(IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOsumDebts"));
    }

    @Transactional
    @RequestMapping("/readyToPayForAdvance")
    public @ResponseBody JSONPObject IOreadyToPayForAdvance(HttpServletResponse httpServletResponse,@RequestParam("value")int value){
        boolean response=true;
        billService.readyToPayForAdvance(value);

        return(IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOreadyToPayForAdvance"));
    }
}
