package pers.masteryourself.lushstar.pagoda.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.masteryourself.lushstar.pagoda.web.remote.AppPluginRemote;
import pers.masteryourself.lushstar.pagoda.web.remote.AppRemote;
import pers.masteryourself.lushstar.pagoda.web.remote.PluginRemote;
import pers.masteryourself.lushstar.pagoda.web.vo.AppPluginVo;
import pers.masteryourself.lushstar.pagoda.web.vo.PluginVo;

import java.util.List;

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
    private AppRemote appRemote;

    @Autowired
    private AppPluginRemote appPluginRemote;

    @Autowired
    private PluginRemote pluginRemote;

    @GetMapping(value = "{appId}")
    public String plugin(@PathVariable Long appId, Model model) {
        List<PluginVo> pluginVoList = pluginRemote.list();
        List<AppPluginVo> appPluginVoList = appPluginRemote.findByAppId(appId);
        model.addAttribute("pluginVoList", pluginVoList);
        model.addAttribute("appVo", appRemote.find(appId));
        pluginVoList.forEach(pluginVo -> {
            appPluginVoList.forEach(appPluginVo -> {
                if (appPluginVo.getPluginId().equals(pluginVo.getId())) {
                    // 表示已经安装过
                    pluginVo.setInstall(true);
                    // 设置激活属性
                    pluginVo.setActive(appPluginVo.getActive());
                }
            });
        });
        return "app/plugin/list";
    }

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
