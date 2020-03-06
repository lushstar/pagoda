package pers.masteryourself.lushstar.pagoda.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>description : PluginServiceApp
 * <p>{@link EnableJpaRepositories} 表示扫描 JPA 的 Repository
 * <p>{@link EntityScan} 表示扫描 @Entity 注解
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 14:07
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "pers.masteryourself.lushstar.pagoda.config")
@EntityScan(basePackages = "pers.masteryourself.lushstar.pagoda.config")
public class PluginServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PluginServiceApp.class, args);
    }

}
