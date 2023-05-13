package com.gec.spring_jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;


@RestController

public class TestController {


    @RequestMapping("hello1")
    public String test(){

        return "hello w!!!";
    }

    @RequestMapping("userLogin")
    /*public String login(String username, String password){
        if (username.equals("admin") && password.equals("123456"))
            return "登陆成功！！！！";
        else
            return "登陆失败！！！！";
    }*/
    public String login(String username, String password) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap result = new HashMap();

        if (username.equals("admin") && password.equals("123456")) {
            result.put("ok", true);
            result.put("message", "登陆成功！！！");
        }
        else {
            result.put("ok", false);
            result.put("message", "登陆失败！！！");
        }
        return objectMapper.writeValueAsString(result);
    }

}
