package com.lushstar.pagoda.service.event;

import org.springframework.context.ApplicationEvent;
import com.lushstar.pagoda.service.bo.PluginChangeMetadata;

/**
 * <p>description : PluginChangeEvent, 插件信息修改事件
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:42
 */
public class PluginChangeEvent extends ApplicationEvent {

    public PluginChangeEvent(PluginChangeMetadata pluginChangeMetadata) {
        super(pluginChangeMetadata);
    }

}
