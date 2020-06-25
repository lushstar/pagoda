package com.github.lushstar.pagoda.sample.spring.boot.service;

import com.github.lushstar.pagoda.client.annotation.Pagoda;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>description : UserService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@Pagoda
@Slf4j
public class UserService {

    public String test(String str) {
        log.info("test 方法执行了:{}", str);
        return "hello，spring";
    }

}
