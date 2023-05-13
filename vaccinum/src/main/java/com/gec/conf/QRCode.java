package com.gec.conf;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class QRCode {

    //在springioc容器中配置一个二维码的config对象
    @Bean
    public QrConfig qrConfig() {


        //创建了QrConfig的对象
        QrConfig qrConfig = new QrConfig();
        //设置背景为白色
        qrConfig.setBackColor(Color.white.getRGB());
        //qrConfig.setForeColor(Color.black.getRGB());//方格的颜色
        return qrConfig;
    }
}