package com.github.lushstar.pagoda.api.remote;

import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.api.dto.AppPluginDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>description : AppPlugin
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:55
 */
public interface AppPluginRemote {

    @GetMapping(value = "findByAppId/{appId}")
    ServiceResponse<List<AppPluginDto>> findByAppId(@PathVariable(value = "appId") Long appId);

    @GetMapping(value = "install/{appId}/{pluginId}")
    ServiceResponse<AppPluginDto> install(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);

    @GetMapping(value = "active/{appId}/{pluginId}")
    ServiceResponse<AppPluginDto> active(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);

    @GetMapping(value = "disable/{appId}/{pluginId}")
    ServiceResponse<AppPluginDto> disable(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);

    @GetMapping(value = "uninstall/{appId}/{pluginId}")
    ServiceResponse<AppPluginDto> uninstall(@PathVariable(value = "appId") Long appId, @PathVariable(value = "pluginId") Long pluginId);
}
