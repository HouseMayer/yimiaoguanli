package com.gec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.entity.User;
import com.gec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jerry
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/gec/user")
public class UserController {
    @Autowired
    IUserService userService;

    @RequestMapping("list")
    public  String list() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        List<User> userList = userService.list();

        result.put("userList", userList);

        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("userLogin")
    public String login(String username, String password) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap result = new HashMap();
        QueryWrapper wrapper = new QueryWrapper<User>();

        wrapper.eq("name", username);
        wrapper.eq("password", password);

        User user = userService.getOne(wrapper);

        if (user != null) {
            result.put("ok", true);
            result.put("message", "登陆成功！！！");
        }
        else {
            result.put("ok", false);
            result.put("message", "登陆失败！！！");
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("insert")
    public String insert(User user) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();


        boolean ok = userService.save(user);

        if ( !ok )
            result.put("massage", "注册失败！！！");
        result.put("ok", ok);

        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();


        boolean ok = userService.removeById(id);


        if ( !ok )
            result.put("massage", "删除失败！请求重新操作！");
        result.put("ok", ok);

        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("update")
    public String update(User user) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();


        boolean ok = userService.save(user);

        if ( !ok )
            result.put("massage", "注册失败！！！");
        result.put("ok", ok);

        return objectMapper.writeValueAsString(result);
    }

}


