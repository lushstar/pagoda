package pers.masteryourself.lushstar.pagoda.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;
import pers.masteryourself.lushstar.pagoda.service.service.AppService;

import java.util.List;

/**
 * <p>description : AppServiceController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/app")
public class AppServiceController {

    @Autowired
    private AppService appService;

    @GetMapping(value = "list")
    public List<PluginEntity> list() {
        return null;
    }

}
