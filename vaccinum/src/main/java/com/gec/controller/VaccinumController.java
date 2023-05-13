package com.gec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.entity.Vaccinum;
import com.gec.entity.VaccinumType;
import com.gec.service.IVaccinumService;
import com.gec.service.IVaccinumTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 疫苗 前端控制器
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/gec/vaccinum")
public class VaccinumController {

    @Autowired
    IVaccinumService vaccinumService;
    @Autowired
    IVaccinumTypeService typeService;

    @RequestMapping("queryAll")
    public String queryAll() throws JsonProcessingException {
        //JSON解析工具
        ObjectMapper objectMapper = new ObjectMapper();
        //定义map集合
        HashMap result = new HashMap();
        //使用业务层来查询数据【查询所有】
        List<Vaccinum> list = vaccinumService.list();

        result.put("list", list);

        // 转换为json写出
        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("list")
    public String list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                       @RequestParam(required = false) String keyWord) throws JsonProcessingException {
        //定义json解析工具
        ObjectMapper objectMapper = new ObjectMapper();
        //key-value集合结果
        HashMap result = new HashMap();
        //调用业务层查询集合
        QueryWrapper<Vaccinum> queryWrapper = new QueryWrapper<>();

        queryWrapper.like( !keyWord.equals("")&&keyWord!=null ,"name" , keyWord);

        Page<Vaccinum> page= vaccinumService.page(new Page<>(pageNum,pageSize), queryWrapper);
        page.getRecords().forEach(en -> {
            //根据id获取到分类的对象数据
            VaccinumType type = typeService.getById(en.getType());
            //把info、set到user对象中
            en.setTypeName(type.getName());
        });


        List<Vaccinum> list = page.getRecords();
        //保存到map中
        result.put("list", list);
        result.put("total", page.getTotal());
        //返回解析的json数据
        return objectMapper.writeValueAsString(result);
    }



    @RequestMapping("insert")
    public String insert(Vaccinum vaccinum) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = vaccinumService.save(vaccinum);

        if ( !ok ){
            result.put("message", "增加失败！！！");
        }else {
            result.put("massage", "增加成功！");
            result.put("ok", true);
        }
        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = vaccinumService.removeById(id);

        if ( !ok ){
            result.put("message", "删除失败！！！");
        }else {
            result.put("ok", ok);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("update")
    public String update(Vaccinum vaccinum) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = vaccinumService.updateById(vaccinum);

        result.put("ok", ok);

        return objectMapper.writeValueAsString(result);
    }

}
