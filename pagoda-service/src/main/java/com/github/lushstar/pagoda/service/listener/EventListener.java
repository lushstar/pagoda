package com.github.lushstar.pagoda.service.listener;

import com.github.lushstar.pagoda.api.dto.PluginChangeMetadata;
import com.github.lushstar.pagoda.service.event.PluginChangeEvent;
import com.github.lushstar.pagoda.service.event.PluginContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

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
public class EventListener {

    private static final String TEST_APP_NAME = "demo";

    @org.springframework.context.event.EventListener(classes = {PluginChangeEvent.class})
    public void mockRuleChangeEvent(ApplicationEvent event) {
        Object source = event.getSource();
        if (source instanceof PluginChangeMetadata) {
            PluginChangeMetadata pluginChangeMetadata = (PluginChangeMetadata) source;
            // 先判断是否 HOLD_REQUEST_CONFIGS 是否有缓存
            if (PluginContext.HOLD_REQUEST_CONFIGS.containsKey(TEST_APP_NAME)) {
                PluginContext.HOLD_REQUEST_CONFIGS.get(TEST_APP_NAME).setResult(pluginChangeMetadata);
            }
            // 如果 HOLD_REQUEST_CONFIGS 没有缓存, 就缓存这次变化
            else {
                PluginContext.CACHE_CONFIGS.put(TEST_APP_NAME, pluginChangeMetadata);
            }
        }
    }

}
