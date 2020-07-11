package com.github.lushstar.pagoda.service.listener;

import com.github.lushstar.pagoda.api.response.PluginNotifyResponse;
import com.github.lushstar.pagoda.service.register.AppInfo;
import com.github.lushstar.pagoda.service.register.RegisterCenter;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>description : EventListener
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:49
 */
@Component
public class PluginNotifyEventListener {

    @Autowired
    private MapperFacade mapperFacade;

    @org.springframework.context.event.EventListener(classes = {PluginNotifyEvent.class})
    public void ruleNotifyEvent(ApplicationEvent event) {
        Object source = event.getSource();
        if (source instanceof PluginNotifyMetadata) {
            PluginNotifyMetadata metadata = (PluginNotifyMetadata) source;
            // 获取应用名称下的所有实例
            List<AppInfo> appInfoList = RegisterCenter.get(metadata.getAppName());
            if (CollectionUtils.isEmpty(appInfoList)) {
                return;
            }
            for (AppInfo appInfo : appInfoList) {
                // 先判断是否 HOLD_REQUEST_CONFIGS 是否有缓存
                String instanceId = appInfo.getInstanceId();
                if (RegisterCenter.HOLD_REQUEST_CONFIGS.containsKey(instanceId)) {
                    RegisterCenter.HOLD_REQUEST_CONFIGS.get(instanceId).setResult(mapperFacade.map(metadata, PluginNotifyResponse.class));
                }
                // 如果 HOLD_REQUEST_CONFIGS 没有缓存, 就缓存这次变化
                else {
                    RegisterCenter.CACHE_CONFIGS.put(instanceId, metadata);
                }
            }
        }
    }

}
