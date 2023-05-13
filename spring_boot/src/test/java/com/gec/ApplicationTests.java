package com.gec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class ApplicationTests {
	@Autowired
	DataSource dataSource;


	@Test
	void contextLoads() throws SQLException {
		System.out.println("当前连接池为:"+dataSource);
		System.out.println("获取数据库链接:"+dataSource.getConnection());


	}

}
