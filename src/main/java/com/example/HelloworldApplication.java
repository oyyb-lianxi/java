package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// import java.lang.Thread;

@SpringBootApplication
//@MapperScan(basePackages = "com.example.mapper")
public class HelloworldApplication {

    @RestController
    class HelloworldController {
         @GetMapping("/")
          String hello() {
             return "欢迎使用微信云托管！";
          }
    }

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}

}
