package com.github.lushstar.pagoda.sample.spring.boot.controller;

import com.github.lushstar.pagoda.sample.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>description : TestController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "test")
    public String test() {
        return userService.test(UUID.randomUUID().toString().substring(0, 5));
    }

}
