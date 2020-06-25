package com.github.lushstar.pagoda.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>description : PagodaWebApplication, web 启动
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@SpringBootApplication
@EnableFeignClients
public class PagodaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagodaWebApplication.class, args);
    }

}
