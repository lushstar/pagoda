package com.lushstar.pagoda.sample.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.lushstar.pagoda.client.annotation.EnablePagoda;

/**
 * <p>description : PagodaDemoServiceApp
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@SpringBootApplication
@EnablePagoda
public class PagodaDemoServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PagodaDemoServiceApp.class, args);
    }

}
