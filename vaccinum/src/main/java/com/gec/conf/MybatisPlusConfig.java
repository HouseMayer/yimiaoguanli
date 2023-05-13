package com.gec.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.gec.mapper")
public class MybatisPlusConfig {

    //定义一个工厂方法，返回bean【拦截器】
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //创建
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //Interceptor中添加分页的拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        //返回
        return mybatisPlusInterceptor;
    }

}