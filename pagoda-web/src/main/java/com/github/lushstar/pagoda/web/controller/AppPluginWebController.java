package com.github.lushstar.pagoda.web.controller;

import com.github.lushstar.pagoda.web.feign.AppPluginRemoteFeign;
import com.github.lushstar.pagoda.web.feign.AppRemoteFeign;
import com.github.lushstar.pagoda.web.feign.PluginRemoteFeign;
import com.github.lushstar.pagoda.web.vo.AppPluginVo;
import com.github.lushstar.pagoda.web.vo.PluginVo;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>description : AppPluginWebController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
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
    private AppRemoteFeign appRemoteFeign;

    @Autowired
    private AppPluginRemoteFeign appPluginRemoteFeign;

    @Autowired
    private PluginRemoteFeign pluginRemoteFeign;

    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping(value = "{appId}")
    public String plugin(@PathVariable Long appId, Model model) {
        List<PluginVo> pluginVoList = mapperFacade.mapAsList(pluginRemoteFeign.list().getData(), PluginVo.class);
        List<AppPluginVo> appPluginVoList = mapperFacade.mapAsList(appPluginRemoteFeign.findByAppId(appId).getData(), AppPluginVo.class);
        model.addAttribute("pluginVoList", pluginVoList);
        model.addAttribute("appVo", appRemoteFeign.find(appId).getData());
        pluginVoList.forEach(pluginVo -> {
            appPluginVoList.forEach(appPluginVo -> {
                if (appPluginVo.getPluginId().equals(pluginVo.getId())) {
                    // 表示已经安装过
                    pluginVo.setInstall(true);
                    // 设置激活属性
                    pluginVo.setActive(appPluginVo.isActive());
                }
            });
        });
        return "app/plugin/list";
    }

    @GetMapping(value = "{action}/{appId}/{pluginId}")
    public String action(@PathVariable String action, @PathVariable Long appId, @PathVariable Long pluginId) {
        switch (action) {
            case "install":
                appPluginRemoteFeign.install(appId, pluginId);
                break;
            case "active":
                appPluginRemoteFeign.active(appId, pluginId);
                break;
            case "disable":
                appPluginRemoteFeign.disable(appId, pluginId);
                break;
            case "uninstall":
                appPluginRemoteFeign.uninstall(appId, pluginId);
                break;
            default:
                log.warn("操作有问题 {}", action);
                break;
        }
        return "redirect:/web/app/plugin/" + appId;
    }

}
