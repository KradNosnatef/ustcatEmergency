package com.ustcat.ustcatEmergency.Service;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ustcat.ustcatEmergency.ToolKit.IOToolKits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSessionController {
    @Autowired
    UserService userService;
    
    @RequestMapping("/Xapi/getUsername") // 获取登录状态
    @PreAuthorize("permitAll()")
    public @ResponseBody JSONPObject IOgetUsername(HttpServletResponse httpServletResponse) {
        String response;
        response = IOToolKits.getName();

        return (IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOgetUsername"));
    }

    @RequestMapping("/Xapi/login") // 拉起登录窗口
    public @ResponseBody JSONPObject IOlogin(HttpServletResponse httpServletResponse) {
        String response;
        response = IOToolKits.getName();

        return (IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOlogin"));
    }

    @RequestMapping("/Xapi/regist") // 注册API
    @PreAuthorize("permitAll()")
    public @ResponseBody JSONPObject IOregist(HttpServletResponse httpServletResponse,
            @RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("authKey") String authKey) {

        int response = userService.register(username, password, authKey);
        return (IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOregist"));
    }

    @RequestMapping("/Xapi/adminCheck") // 看看劳资是不是管理员
    public @ResponseBody JSONPObject IOadminCheck(HttpServletResponse httpServletResponse) {
        boolean response = userService.adminCheck();
        return (IOToolKits.myResponseGenerator(httpServletResponse, response, "_IOadminCheck"));
    }
}
