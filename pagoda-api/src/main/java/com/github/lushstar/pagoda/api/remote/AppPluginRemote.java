package com.github.lushstar.pagoda.api.remote;

import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.api.response.AppPluginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>description : AppPluginRemote
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:55
 */
public interface AppPluginRemote {

    @GetMapping(value = "findByAppId/{appId}")
    ServiceResponse<List<AppPluginResponse>> findByAppId(@PathVariable(value = "appId") Long appId);

    @GetMapping(value = "install/{appId}/{pluginId}")
    ServiceResponse<AppPluginResponse> install(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);

    @GetMapping(value = "active/{appId}/{pluginId}")
    ServiceResponse<AppPluginResponse> active(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);

    @GetMapping(value = "disable/{appId}/{pluginId}")
    ServiceResponse<AppPluginResponse> disable(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);

    @GetMapping(value = "uninstall/{appId}/{pluginId}")
    ServiceResponse<AppPluginResponse> uninstall(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);
}
