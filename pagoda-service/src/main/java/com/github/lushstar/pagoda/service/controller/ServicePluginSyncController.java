package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.PluginSyncRemote;
import com.github.lushstar.pagoda.api.response.PluginChangeMetadata;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.enums.SourceType;
import com.github.lushstar.pagoda.dal.model.AppEntity;
import com.github.lushstar.pagoda.dal.model.AppPluginEntity;
import com.github.lushstar.pagoda.dal.model.PluginEntity;
import com.github.lushstar.pagoda.service.event.PluginContext;
import com.github.lushstar.pagoda.service.response.DeferredResultWrapper;
import com.github.lushstar.pagoda.service.service.AppPluginService;
import com.github.lushstar.pagoda.service.service.AppService;
import com.github.lushstar.pagoda.service.service.PluginService;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @RequestMapping("part/{appName}")
    public DeferredResult<ResponseEntity<PluginChangeMetadata>> partSync(@PathVariable(value = "appName") String appName) {
        // 判断是否已经缓存, 如果有了, 则直接响应结果
        if (PluginContext.CACHE_CONFIGS.containsKey(appName)) {
            DeferredResultWrapper deferredResult = new DeferredResultWrapper();
            deferredResult.setResult(PluginContext.CACHE_CONFIGS.remove(appName));
            return deferredResult.getResult();
        }
        // 如果没有缓存, hold 60s
        DeferredResultWrapper deferredResult = new DeferredResultWrapper();
        // 60s 到了, 移除
        deferredResult.onTimeout(() -> {
            // if timeout, print log
            log.info("instanceId >>> 【{}】 wait timeout", appName);
        });
        // 响应成功后操作
        deferredResult.onCompletion(() -> {
            // if completion(it contains two cases:1. timeout 2. return in 60 seconds), delete this cache request
            log.info("delete DeferredResultWrapper instanceId >>> 【{}】", appName);
            PluginContext.HOLD_REQUEST_CONFIGS.remove(appName, deferredResult);
        });
        // hold request
        PluginContext.HOLD_REQUEST_CONFIGS.put(appName, deferredResult);
        // 响应
        return deferredResult.getResult();
    }

    @Override
    @RequestMapping("full/{appName}")
    public ServiceResponse<List<PluginChangeMetadata>> fullSync(@PathVariable(value = "appName") String appName) {
        AppEntity appEntity = appService.findByName(appName);
        List<AppPluginEntity> appPluginEntityList = appPluginService.findByAppId(appEntity.getId());
        List<PluginChangeMetadata> result = new ArrayList<>();
        appPluginEntityList.forEach(appPluginEntity -> {
            PluginEntity pluginEntity = pluginService.findById(appPluginEntity.getPluginId());
            PluginChangeMetadata temp = new PluginChangeMetadata();
            temp.setId(appPluginEntity.getId());
            temp.setClassName(pluginEntity.getClassName());
            temp.setAddress(pluginEntity.getAddress());
            temp.setActive(appPluginEntity.isActive());
            if (appPluginEntity.isActive()) {
                temp.setSourceType(SourceType.ACTIVE);
            } else {
                temp.setSourceType(SourceType.DISABLE);
            }
            result.add(temp);
        });
        return ServiceResponse.success(result);
    }

}
