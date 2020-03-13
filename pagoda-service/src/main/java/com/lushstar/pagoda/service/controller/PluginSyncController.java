package com.lushstar.pagoda.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import com.lushstar.pagoda.dal.model.AppEntity;
import com.lushstar.pagoda.dal.model.AppPluginEntity;
import com.lushstar.pagoda.dal.model.PluginEntity;
import com.lushstar.pagoda.service.bo.PluginChangeMetadata;
import com.lushstar.pagoda.service.bo.SourceType;
import com.lushstar.pagoda.service.event.PluginContext;
import com.lushstar.pagoda.service.response.DeferredResultWrapper;
import com.lushstar.pagoda.service.response.ServiceResponse;
import com.lushstar.pagoda.service.service.AppPluginService;
import com.lushstar.pagoda.service.service.AppService;
import com.lushstar.pagoda.service.service.PluginService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>description : PluginSyncController, 同步插件信息
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:39
 */
@RestController
@RequestMapping(value = "service/plugin/sync")
@Slf4j
public class PluginSyncController {

    @Autowired
    private AppPluginService appPluginService;

    @Autowired
    private AppService appService;

    @Autowired
    private PluginService pluginService;

    @RequestMapping("part/{appName}")
    public DeferredResult<ResponseEntity<PluginChangeMetadata>> partSync(HttpServletRequest request, @PathVariable(value = "appName") String appName) {
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

    @RequestMapping("full/{appName}")
    public ServiceResponse<List<PluginChangeMetadata>> fullSync(HttpServletRequest request, @PathVariable(value = "appName") String appName) {
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
