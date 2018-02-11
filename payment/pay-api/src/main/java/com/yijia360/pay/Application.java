package com.yijia360.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.yijia360.pay.core.service.SpringContextUtil;

@SpringBootApplication
@MapperScan("com.yijia360.pay.core.dao")
public class Application {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		SpringContextUtil.setApplicationContext(applicationContext);
	}
	
}