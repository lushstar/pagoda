package com.github.lushstar.pagoda.service.listener;

import com.github.lushstar.pagoda.api.response.PluginNotifyMetadata;
import org.springframework.context.ApplicationEvent;

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

    public PluginChangeEvent(PluginNotifyMetadata metadata) {
        super(metadata);
    }

}
