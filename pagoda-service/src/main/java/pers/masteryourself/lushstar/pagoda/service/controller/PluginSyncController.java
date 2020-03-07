package pers.masteryourself.lushstar.pagoda.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;
import pers.masteryourself.lushstar.pagoda.service.event.PluginContext;
import pers.masteryourself.lushstar.pagoda.service.response.DeferredResultWrapper;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value = "service/plugin")
@Slf4j
public class PluginSyncController {

    @RequestMapping("sync/{appName}")
    public DeferredResult<ResponseEntity<PluginBo>> poll(
            HttpServletRequest request,
            @PathVariable(value = "appName") String appName) {
        // todo appName 鉴权
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

}
