package com.gec.controller;

import com.gec.mapper.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//二维码的controller层接口
@RestController
public class QRController {

    //依赖业务层对象
    @Autowired
    QRService qrService;

    //定义二维码请求接口
    @RequestMapping("code")
    public void generateUserPass(Long id, HttpServletResponse servletResponse) throws IOException {
        //content是二维码存放的信息，可以用于存放用户的信息
        qrService.generateStream(id, servletResponse);
    }

}