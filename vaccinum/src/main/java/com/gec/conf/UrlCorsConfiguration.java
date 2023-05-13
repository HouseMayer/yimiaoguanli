package com.gec.conf;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class UrlCorsConfiguration implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        //允许跨域的请求方法GET, POST, HEAD 等
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        //重新预检验跨域的缓存时间 (s)
        response.setHeader("Access-Control-Max-Age", "3600");
        //允许跨域的请求头
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
        //是否携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 设置响应的类型及字符集编码
        response.setContentType("text/json;charset=utf-8");
        //设置响应的中文编码
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}