package pers.masteryourself.lushstar.pagoda.sample.spring.boot.service;

import org.springframework.stereotype.Service;
import pers.masteryourself.lushstar.pagoda.client.annotation.Pagoda;

/**
 * <p>description : UserService
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@Service
@Pagoda
public class UserService {

    public String say() {
        return "hello，spring";
    }

}
