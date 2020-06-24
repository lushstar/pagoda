package com.github.lushstar.pagoda.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>description : PagodaServiceApplication, service 服务提供层，也可以与第三方系统进行交互
 * <p>{@link EnableJpaRepositories} 表示扫描 JPA 的 Repository
 * <p>{@link EntityScan} 表示扫描 @Entity 注解
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 14:07
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.lushstar.pagoda.dal.repository")
@EntityScan(basePackages = "com.lushstar.pagoda.dal.model")
public class PagodaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagodaServiceApplication.class, args);
    }

}
