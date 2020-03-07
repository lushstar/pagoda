package pers.masteryourself.lushstar.pagoda.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import pers.masteryourself.lushstar.pagoda.service.event.PluginContext;
import pers.masteryourself.lushstar.pagoda.service.response.DeferredResultWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @RequestMapping("/sync/{app}")
    public DeferredResult<ResponseEntity<Map<String, String>>> poll(
            HttpServletRequest request,
            @PathVariable(value = "app") String app) {
        // todo 鉴权
        // 判断是否已经缓存, 如果有了, 则直接响应结果
        if (PluginContext.CACHE_CONFIGS.containsKey(app)) {
            // check this ip has been updated
            if (!PluginContext.CACHE_CONFIGS.get(app).contains("")) {
                PluginContext.CACHE_CONFIGS.get(app).add("");
                DeferredResultWrapper deferredResult = new DeferredResultWrapper();
                deferredResult.setResult(PluginContext.MOCK_CONFIGS.get(app));
                return deferredResult.getResult();
            }
        }
        // 如果没有缓存, hold 60s
        DeferredResultWrapper deferredResult = new DeferredResultWrapper();
        // 60s 到了, 移除
        deferredResult.onTimeout(() -> {
            // if timeout, print log
            log.info("instanceId >>> 【{}】 wait timeout", app);
        });
        // 响应成功后操作
        deferredResult.onCompletion(() -> {
            // if completion(it contains two cases:1. timeout 2. return in 60 seconds), delete this cache request
            log.info("delete DeferredResultWrapper instanceId >>> 【{}】", app);
            PluginContext.HOLD_REQUEST_CONFIGS.remove(app, deferredResult);
        });
        // hold request
        PluginContext.HOLD_REQUEST_CONFIGS.put(app, deferredResult);
        // 响应
        return deferredResult.getResult();
    }

}
