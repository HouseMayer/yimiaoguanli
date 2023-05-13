package com.gec.mapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface QRService {

    //根据content（二维码中存储的信息）、HttpServletResponse（http的响应对象）
    public void generateStream(Long userid, HttpServletResponse response) throws IOException;
}