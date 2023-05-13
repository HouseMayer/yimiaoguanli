package com.gec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.VaccinumApplication;
import com.gec.entity.Vaccinum;
import com.gec.entity.VaccinumType;
import com.gec.service.IVaccinumTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 疫苗类型 前端控制器
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/gec/vaccinum-type")
public class VaccinumTypeController {
    @Autowired
    IVaccinumTypeService typeService;

    @RequestMapping("queryAll")
    public String queryAll() throws JsonProcessingException {
        //JSON解析工具
        ObjectMapper objectMapper = new ObjectMapper();
        //定义map集合
        HashMap result = new HashMap();
        //使用业务层来查询数据【查询所有】
        List<VaccinumType> list = typeService.list();

        result.put("list", list);

        // 转换为json写出
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
        QueryWrapper<VaccinumType> queryWrapper = new QueryWrapper<>();

        queryWrapper.like( !keyWord.equals("")&&keyWord!=null ,"name" , keyWord);

        Page<VaccinumType> page= typeService.page(new Page<>(pageNum,pageSize), queryWrapper);


        List<VaccinumType> list = page.getRecords();

        result.put("list", list);
        result.put("total", page.getTotal());
        // 转换为json写出
        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = typeService.removeById(id);

        if ( !ok ){
            result.put("message", "删除失败！！！");
        }else {
            result.put("ok", ok);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("insert")
    public String insert(VaccinumType vaccinumType) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = typeService.save(vaccinumType);

        if ( !ok ){
            result.put("message", "增加失败！！！");
        }else {
            result.put("ok", ok);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("update")
    public String update(VaccinumType vaccinumType) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();



        boolean ok = typeService.updateById(vaccinumType);

            result.put("ok", ok);

        return objectMapper.writeValueAsString(result);
    }


}
