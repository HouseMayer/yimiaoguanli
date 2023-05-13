package com.gec.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.entity.UserInfo;
import com.gec.mapper.QRService;
import com.gec.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Service
public class QRServiceImpl implements QRService {

    @Autowired
    IUserInfoService userInfoService;
    //从ioc容器中获取二维码配置的对象
    @Resource
    QrConfig qrconig;

    //实现获取二维码的图片流的方法
    @Override
    public void generateStream(Long userid, HttpServletResponse response) throws IOException {

        UserInfo userInfo = userInfoService.getById(userid);

        Integer status = userInfo.getStatus();

        if (status==0){
            qrconig.setForeColor(Color.green.getRGB());
        } else if (status==1) {
            qrconig.setForeColor(Color.yellow.getRGB());
        }else {
            qrconig.setForeColor(Color.red.getRGB());
        }


        //设置当前通行码的背景颜色
//        qrconig.setBackColor(Color.green.getRGB());
//        qrconig.setForeColor(Color.green.getRGB());
        //使用QrCodeUtil工具类generate生成二维码的图片流
        String content = new ObjectMapper().writeValueAsString(userInfo);
        QrCodeUtil.generate(content, qrconig, "png", response.getOutputStream());
    }
}