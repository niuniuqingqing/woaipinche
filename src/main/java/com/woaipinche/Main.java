package com.woaipinche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@EnableAutoConfiguration
@SpringBootApplication
//返回jsp页面必须继承SpringBootServletInitializer类重写里面的方法
public class Main extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        
    }
    
    protected SpringApplicationBuilder config(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(Main.class);
    }
}