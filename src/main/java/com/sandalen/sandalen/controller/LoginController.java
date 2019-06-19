package com.sandalen.sandalen.controller;

import com.sandalen.sandalen.Exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping("/hello")
    public String toHello(String user){
        if(!user.equals("aaaa"))
        {
            throw new UserNotExistException();
        }
        return "hello";
    }

    @GetMapping(value = "/user/login")
    public String login(@RequestParam(value = "username") String username, @RequestParam("password") String password,
                        Map<String,String> map, HttpServletRequest request){
        System.out.println("username = " + username);
        HttpSession session = request.getSession();
        if(!StringUtils.isEmpty(username) && password.equals("123456")){
            session.setAttribute("user",username);
            System.out.println("username = " + username);
            return "redirect:/toDashboard";
        }
        else
        {
            map.put("error","用户名密码不正确");
            return "index";
        }
    }

    @RequestMapping(value = "/toDashboard")
    public String toDashboard(){
        return "/dashboard";
    }
}
