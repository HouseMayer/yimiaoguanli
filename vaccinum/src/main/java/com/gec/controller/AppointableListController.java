package com.gec.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.entity.AppointableList;
import com.gec.entity.Appointment;
import com.gec.entity.User;
import com.gec.entity.Vaccinum;
import com.gec.service.IAppointableListService;
import com.gec.service.IAppointmentService;
import com.gec.service.IUserService;
import com.gec.service.IVaccinumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 可预约疫苗列表 前端控制器
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/gec/appointable-list")
public class AppointableListController {

    @Autowired
    IAppointableListService appointableService;
    @Autowired
    IAppointmentService appointmentService;
    @Autowired
    IVaccinumService vaccinumService;
    @Autowired
    IUserService userService;

    @RequestMapping("appointable")
    public String appointable(int id, String phone) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        AppointableList appointableList = appointableService.getById(id);

        if (appointableList.getNums() >= 1) {

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

            userQueryWrapper.eq("phone", phone);

            User user = userService.getOne(userQueryWrapper);

            Appointment appointment = new Appointment();

            appointment.setUserId(user.getId());
            appointment.setVaccinumId(appointableList.getVaccinumId());
            appointment.setAddress(appointableList.getAddress());
            appointment.setAppDate(appointableList.getAppDate());
            appointment.setPostTime(appointableList.getPostTime());
            appointment.setStatus( 0 );
            appointmentService.save(appointment);
            appointableList.setNums(appointableList.getNums() - 1);
            appointableList.setAppNum(appointableList.getAppNum() + 1);
            appointableService.updateById(appointableList);



            result.put("ok", true);
        }else
            result.put("ok", false);




        return objectMapper.writeValueAsString(result);



    }


    @RequestMapping("update")
    public String update(AppointableList appointableList) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = appointableService.updateById(appointableList);

        result.put("ok", ok);

        return objectMapper.writeValueAsString(result);
    }


    @RequestMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = appointableService.removeById(id);

        if ( !ok ){
            result.put("message", "删除失败！！！");
        }else {
            result.put("ok", ok);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("insert")
    public String insert(AppointableList appointableList) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap result = new HashMap();

        boolean ok = appointableService.save(appointableList);

        if ( !ok ){
            result.put("message", "增加失败！！！");
        }else {
            result.put("massage", "增加成功！");
            result.put("ok", true);
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

        QueryWrapper<AppointableList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!keyWord.equals("") && keyWord != null, "name", keyWord);

        Page<AppointableList> page = appointableService.page(new Page<>(pageNum, pageSize), queryWrapper);

        page.getRecords().forEach(en -> {
            //根据id获取到分类的对象数据
            Vaccinum vaccinum = vaccinumService.getById(en.getVaccinumId());
            //把info、set到user对象中
            en.setVaccinumName(vaccinum.getName());
        });

        List<AppointableList> list = page.getRecords();
        result.put("list", list);
        result.put("total", page.getTotal());
        // 转换为json写出
        return objectMapper.writeValueAsString(result);

    }


}