package com.github.lushstar.pagoda.api.response;

import com.github.lushstar.pagoda.common.enums.SourceType;
import lombok.Data;

/**
 * <p>description : PluginNotifyResponse
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 17:33
 */
@Data
public class PluginNotifyResponse {

    /**
     * 应用插件 Id
     */
    private Long appPluginId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 地址
     */
    private String address;

    /**
     * 插件类名
     */
    private String className;

    /**
     * 事件类型
     */
    private SourceType sourceType;

}
