package pers.masteryourself.lushstar.pagoda.sample.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.masteryourself.lushstar.pagoda.sample.spring.boot.service.UserService;
import pers.masteryourself.lushstar.pagoda.client.PluginFactory;
import pers.masteryourself.lushstar.pagoda.client.po.Plugin;

import java.io.File;

/**
 * <p>description : TestController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@RestController
public class TestController {

    @Autowired
    private PluginFactory pluginFactory;

    @Autowired
    private UserService userService;

    @GetMapping(value = "test")
    public String test() {
        return userService.say();
    }

    @GetMapping(value = "install")
    public String install() {
        Plugin plugin = new Plugin();
        plugin.setId(1L);
        plugin.setClassName("pers.masteryourself.lushstar.pagoda.sample.embed.plugin.ParamPlugin");
        plugin.setAddress(System.getProperty("user.dir") + File.separator + "site" + File.separator + "pagoda-embed-plugin-1.0.0-SNAPSHOT.jar");
        pluginFactory.install(plugin);
        Plugin pluginBo2 = new Plugin();
        pluginBo2.setId(2L);
        pluginBo2.setClassName("pers.masteryourself.lushstar.pagoda.sample.embed.plugin.ReturnPlugin");
        pluginBo2.setAddress(System.getProperty("user.dir") + File.separator + "site" + File.separator + "pagoda-embed-plugin-1.0.0-SNAPSHOT.jar");
        pluginFactory.install(pluginBo2);
        return "success";
    }

    @GetMapping(value = "active")
    public String active() {
        pluginFactory.active(1L);
        pluginFactory.active(2L);
        return "success";
    }

    @GetMapping(value = "uninstall")
    public String uninstall() {
        pluginFactory.uninstall(1L);
        pluginFactory.uninstall(2L);
        return "success";
    }

    @GetMapping(value = "disable")
    public String disable() {
        pluginFactory.disable(1L);
        pluginFactory.disable(2L);
        return "success";
    }

}
