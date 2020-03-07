package pers.masteryourself.lushstar.pagoda.service.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;
import pers.masteryourself.lushstar.pagoda.service.event.PluginChangeEvent;
import pers.masteryourself.lushstar.pagoda.service.event.PluginContext;

/**
 * <p>description : EventListener
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
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
        if (source instanceof PluginBo) {
            PluginBo pluginBo = (PluginBo) source;
            // 先判断是否 HOLD_REQUEST_CONFIGS 是否有缓存
            if (PluginContext.HOLD_REQUEST_CONFIGS.containsKey(TEST_APP_NAME)) {
                PluginContext.HOLD_REQUEST_CONFIGS.get(TEST_APP_NAME).setResult(pluginBo);
            }
            // 如果 HOLD_REQUEST_CONFIGS 没有缓存, 就缓存这次变化
            else {
                PluginContext.CACHE_CONFIGS.put(TEST_APP_NAME, pluginBo);
            }
        }
    }

}
