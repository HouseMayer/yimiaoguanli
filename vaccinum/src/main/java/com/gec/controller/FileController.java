package com.gec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    //测试上传操作请求
    @RequestMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        //1、随机数  2、时间戳  3、UUID
        String uuid = UUID.randomUUID().toString();
        //getOriginalFilename--文件名
        String fileName = file.getOriginalFilename();
        //后缀
        String subfix = fileName.substring(fileName.lastIndexOf("."));
        //拼接uuid和后缀名
        fileName = uuid + subfix;
        System.out.println("文件名：" + fileName);
        //文件上传
        file.transferTo(new File("C:\\Work\\WorkSpace\\IDEA WorkSpace\\shixun\\file\\" + fileName));
        // 返回图片访问的url
        return "http://localhost:8088/" + fileName;
    }

}