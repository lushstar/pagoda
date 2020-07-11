package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.PluginSyncRemote;
import com.github.lushstar.pagoda.api.response.PluginNotifyResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.enums.SourceType;
import com.github.lushstar.pagoda.dal.model.AppEntity;
import com.github.lushstar.pagoda.dal.model.AppPluginEntity;
import com.github.lushstar.pagoda.dal.model.PluginEntity;
import com.github.lushstar.pagoda.service.listener.PluginNotifyMetadata;
import com.github.lushstar.pagoda.service.register.AppInfo;
import com.github.lushstar.pagoda.service.register.RegisterCenter;
import com.github.lushstar.pagoda.service.response.DeferredResultWrapper;
import com.github.lushstar.pagoda.service.service.AppPluginService;
import com.github.lushstar.pagoda.service.service.AppService;
import com.github.lushstar.pagoda.service.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>description : ServicePluginSyncController, 同步插件信息
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:39
 */
@RestController
@RequestMapping(value = "service/plugin/sync")
@Slf4j
public class ServicePluginSyncController implements PluginSyncRemote {

    @Autowired
    private AppPluginService appPluginService;

    @Autowired
    private AppService appService;

    @Autowired
    private PluginService pluginService;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    @RequestMapping("part/{appName}/{instanceId}")
    public DeferredResult<ResponseEntity<PluginNotifyResponse>> partSync(@PathVariable(value = "appName") String appName,
                                                                         @PathVariable(value = "instanceId") String instanceId) {
        // 判断是否注册过
        AppInfo appInfo = RegisterCenter.get(appName, instanceId);
        if (appInfo == null) {
            RegisterCenter.register(appName, instanceId);
        }
        // 判断是否已经缓存, 如果有了, 则直接响应结果
        if (RegisterCenter.CACHE_CONFIGS.containsKey(instanceId)) {
            DeferredResultWrapper deferredResult = new DeferredResultWrapper();
            deferredResult.setResult(mapperFacade.map(RegisterCenter.CACHE_CONFIGS.remove(instanceId), PluginNotifyResponse.class));
            return deferredResult.getResult();
        }
        // 如果没有缓存, hold 60s
        DeferredResultWrapper deferredResult = new DeferredResultWrapper();
        // 60s 到了, 移除
        deferredResult.onTimeout(() -> {
            // if timeout, print log
            log.info("instanceId >>> 【{}】 wait timeout", instanceId);
        });
        // 响应成功后操作
        deferredResult.onCompletion(() -> {
            // if completion(it contains two cases:1. timeout 2. return in 60 seconds), delete this cache request
            log.info("delete DeferredResultWrapper instanceId >>> 【{}】", instanceId);
            RegisterCenter.HOLD_REQUEST_CONFIGS.remove(instanceId, deferredResult);
        });
        // hold request
        RegisterCenter.HOLD_REQUEST_CONFIGS.put(instanceId, deferredResult);
        // 响应
        return deferredResult.getResult();
    }

    @Override
    @RequestMapping("full/{appName}")
    public ServiceResponse<List<PluginNotifyResponse>> fullSync(@PathVariable(value = "appName") String appName) {
        AppEntity appEntity = appService.findByName(appName);
        List<AppPluginEntity> appPluginEntityList = appPluginService.findByAppId(appEntity.getId());
        List<PluginNotifyResponse> result = new ArrayList<>();
        appPluginEntityList.forEach(appPluginEntity -> {
            PluginEntity pluginEntity = pluginService.findById(appPluginEntity.getPluginId());
            PluginNotifyMetadata pluginChangeMetadata = mapperFacade.map(pluginEntity, PluginNotifyMetadata.class);
            pluginChangeMetadata.setAppPluginId(appPluginEntity.getId());
            if (appPluginEntity.isActive()) {
                pluginChangeMetadata.setSourceType(SourceType.ACTIVE);
            } else {
                pluginChangeMetadata.setSourceType(SourceType.DISABLE);
            }
            result.add(mapperFacade.map(pluginChangeMetadata, PluginNotifyResponse.class));
        });
        return ServiceResponse.success(result);
    }

}
