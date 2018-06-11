package com.dl.activity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dl.activity.config.Swagger2;
import com.dl.activity.core.ProjectConstant;
import com.dl.base.configurer.FeignConfiguration;
import com.dl.base.configurer.RestTemplateConfig;
import com.dl.base.configurer.WebMvcConfigurer;

@SpringBootApplication
@Import({ RestTemplateConfig.class, Swagger2.class, WebMvcConfigurer.class, FeignConfiguration.class })
@MapperScan(basePackages = { ProjectConstant.MAPPER_PACKAGE, "com.dl.activity.dao2" })
@EnableTransactionManagement
@EnableEurekaClient
@EnableFeignClients()
public class ActivityServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ActivityServiceApplication.class, args);
	}
}
