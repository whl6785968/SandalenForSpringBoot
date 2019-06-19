package com.sandalen.sandalen.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Loginintercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        System.out.println("user"+user);
        if(!StringUtils.isEmpty(user)){
            return true;
        }
        else
        {
            request.setAttribute("error","请登录");
            request.getRequestDispatcher("/").forward(request,response);
            return false;

        }

    }
}
