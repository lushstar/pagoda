package pers.masteryourself.lushstar.pagoda.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pers.masteryourself.lushstar.pagoda.web.vo.AppVo;

import java.util.List;

/**
 * <p>description : AppWebController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@RestController
@RequestMapping(value = "web/app")
public class AppWebController {

    @Value("${pagoda.service.url}")
    private String routeUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "findAll")
    public List<AppVo> findAll() {
        return null;
    }

}
