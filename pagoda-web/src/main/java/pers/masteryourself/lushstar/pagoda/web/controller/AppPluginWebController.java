package pers.masteryourself.lushstar.pagoda.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.masteryourself.lushstar.pagoda.web.remote.AppPluginRemote;

/**
 * <p>description : AppPluginWebController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:34
 */
@Controller
@RequestMapping(value = "web/app/plugin")
@Slf4j
public class AppPluginWebController {

    @Autowired
    private AppPluginRemote appPluginRemote;

    @GetMapping(value = "{action}/{appId}/{pluginId}")
    public String action(@PathVariable String action, @PathVariable Long appId, @PathVariable Long pluginId) {
        switch (action) {
            case "install":
                appPluginRemote.install(appId, pluginId);
                break;
            case "active":
                appPluginRemote.active(appId, pluginId);
                break;
            case "disable":
                appPluginRemote.disable(appId, pluginId);
                break;
            case "uninstall":
                appPluginRemote.uninstall(appId, pluginId);
                break;
            default:
                log.warn("操作有问题 {}", action);
                break;
        }
        return "redirect:/web/app/plugin/" + appId;
    }

}
