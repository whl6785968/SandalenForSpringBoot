package com.sandalen.sandalen.ExceptionHandler;

import com.sandalen.sandalen.Exception.UserNotExistException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

  /*  @ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String,Object> handlerException(Exception e){
        HashMap<String, Object> map = new HashMap();
        map.put("code","user not exist");
        map.put("message",e.getMessage());

        return map;

    }*/

    @ExceptionHandler(UserNotExistException.class)
    public String  handlerException(Exception e, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap();

        request.setAttribute("javax.servlet.error.status_code",400);
        map.put("code","user not exist");
        map.put("message",e.getMessage());

        request.setAttribute("ext",map);
        return "forward:/error";

    }


}
