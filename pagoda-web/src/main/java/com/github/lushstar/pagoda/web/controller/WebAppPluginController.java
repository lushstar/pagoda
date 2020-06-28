package com.github.lushstar.pagoda.web.controller;

import com.github.lushstar.pagoda.api.response.AppPluginResponse;
import com.github.lushstar.pagoda.api.response.AppResponse;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import com.github.lushstar.pagoda.web.feign.AppPluginRemoteFeign;
import com.github.lushstar.pagoda.web.feign.AppRemoteFeign;
import com.github.lushstar.pagoda.web.feign.PluginRemoteFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>description : WebAppPluginController
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
public class WebAppPluginController {

    @Autowired
    private AppRemoteFeign appRemoteFeign;

    @Autowired
    private AppPluginRemoteFeign appPluginRemoteFeign;

    @Autowired
    private PluginRemoteFeign pluginRemoteFeign;

    @GetMapping(value = "{appId}")
    public String plugin(@PathVariable Long appId, Model model) {
        // 查询所有的插件
        List<PluginResponse> pluginResponseList = pluginRemoteFeign.list().getData();
        // 查询此应用对应的所有插件
        List<AppPluginResponse> appPluginResponseList = appPluginRemoteFeign.findByAppId(appId).getData();
        // 查询应用信息
        AppResponse appResponse = appRemoteFeign.find(appId).getData();
        // 判断是否安装/激活
        pluginResponseList.forEach(pluginVo -> {
            appPluginResponseList.forEach(appPluginVo -> {
                if (appPluginVo.getPluginId().equals(pluginVo.getId())) {
                    // 表示已经安装过
                    pluginVo.setInstall(true);
                    // 设置激活属性
                    pluginVo.setActive(appPluginVo.isActive());
                }
            });
        });
        model.addAttribute("pluginVoList", pluginResponseList);
        model.addAttribute("appVo", appResponse);
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
