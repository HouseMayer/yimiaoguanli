package com.gec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.entity.User;
import com.gec.entity.UserInfo;
import com.gec.service.IUserInfoService;
import com.gec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/gec/user-info")
public class UserInfoController {
    @Autowired
    IUserInfoService userInfoService;
    @Autowired
    IUserService userService;


    @RequestMapping("insert")
    public String insert(User user, UserInfo userInfo) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();


        boolean ok = userService.save(user);

        userInfo.setUserId(user.getId());

        boolean ok1 = userInfoService.save(userInfo);

        if ( !( ok && ok1) ){
            result.put("message", "增加失败！！！");
        }else {
            result.put("massage", "增加成功！");
            result.put("ok", true);
        }
        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("listInfo")
    public String listInfo2(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = true, defaultValue = "3") Integer pageSize,
                            @RequestParam(required = false) String keyWord
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        //定义一个map来存放json的键值对数据
        HashMap result = new HashMap();
        //查询构造条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 成立的表达式  字段名  数值
        //角色限制
        queryWrapper.notIn("role", 0);
        //LIKE
        queryWrapper.like(keyWord != null && !keyWord.equals(""), "name", keyWord);
        /*分页构造函数
        Params:
        current – 当前页
        size – 每页显示条数*/
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        //遍历结果集，然后根据分类id查询分类信息，返回回社到对象中
        page.getRecords().forEach(en -> {
            //根据id获取到分类的对象数据
            UserInfo userinfo = userInfoService.getById(en.getId());
            //把info、set到user对象中
            en.setUserInfo(userinfo);
        });
        //在结果中存放一个list集合
        result.put("list", page.getRecords());

        result.put("total", page.getTotal());
        //返回json
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("list")
    public String list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                       @RequestParam(required = false) String keyWord)
            throws JsonProcessingException {
        //JSON解析工具
        ObjectMapper objectMapper = new ObjectMapper();
        //定义map集合
        HashMap result = new HashMap();
        //使用业务层来查询数据【查询所有】
        //Page<VaccinumType> page= typeService.page(new Page<>(pageNum,pageSize));
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like( !keyWord.equals("")&&keyWord!=null ,"name" , keyWord);


        Page<UserInfo> page= userInfoService.page(new Page<>(pageNum,pageSize), queryWrapper);

        page.getRecords().forEach(en -> {
            //根据id获取到分类的对象数据
            User user = userService.getById(en.getUserId());
            //把info、set到user对象中
            en.setUser(user);
            en.setUserName(user.getName());
        });


        List<UserInfo> list = page.getRecords();
        result.put("list", list);
        result.put("total", page.getTotal());
        // 转换为json写出
        return objectMapper.writeValueAsString(result);
    }





    @RequestMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = userInfoService.removeById(id);
        boolean ok1 = userService.removeById(id);

        if ( !( ok && ok1) ){
            result.put("message", "删除失败！！！");

        }else {
            result.put("ok", ok && ok1);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("update")
    public String update(User user, UserInfo userInfo) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok1 = userService.updateById(user);

        boolean ok = userInfoService.updateById(userInfo);




        result.put("ok", ok&&ok1);


        return objectMapper.writeValueAsString(result);
    }


}
