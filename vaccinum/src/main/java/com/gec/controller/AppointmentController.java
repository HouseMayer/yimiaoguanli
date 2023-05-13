package com.gec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.entity.AppointableList;
import com.gec.entity.Appointment;
import com.gec.service.IAppointableListService;
import com.gec.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 疫苗预约 前端控制器
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/gec/appointment")
public class AppointmentController {
    @Autowired
    IAppointmentService appointmentService;
    @Autowired
    IAppointableListService appointableService;

    @RequestMapping("inoculation")
    public String insert(Integer id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        Appointment appointment = appointmentService.getById(id);

        appointment.setStatus(1);

        boolean ok = appointmentService.updateById(appointment);

        if ( !ok ){
            result.put("message", "增加失败！！！");
        }else {
            result.put("massage", "增加成功！");
            result.put("ok", true);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("delete")
    public String delete(Integer id, Integer status, Integer vaccinumId) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        AppointableList appointableList = appointableService.getById(vaccinumId);

        boolean ok = appointmentService.removeById(id);

        if ( status == 1 ){
            result.put("ok", ok);
        }else {
            appointableList.setNums(appointableList.getNums() + 1);
            appointableList.setAppNum(appointableList.getAppNum() - 1);
            appointableService.updateById(appointableList);
            result.put("ok", ok);
        }

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

        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!keyWord.equals("") && keyWord != null, "name", keyWord);

        Page<Appointment> page = appointmentService.page(new Page<>(pageNum, pageSize), queryWrapper);


        List<Appointment> list = page.getRecords();
        result.put("list", list);
        result.put("total", page.getTotal());
        // 转换为json写出
        return objectMapper.writeValueAsString(result);

    }
}
