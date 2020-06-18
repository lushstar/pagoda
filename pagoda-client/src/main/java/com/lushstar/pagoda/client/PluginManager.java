package com.lushstar.pagoda.client;

import com.lushstar.pagoda.api.dto.PluginChangeMetadata;

/**
 * <p>description : PluginManager
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 22:10
 */
public interface PluginManager {

    /**
     * 安装插件
     *
     * @param pluginChangeMetadata 插件元数据
     */
    void install(PluginChangeMetadata pluginChangeMetadata);

    /**
     * 判断是否有插件
     *
     * @param id 插件 Id
     * @return true 表示包含此插件
     */
    boolean hasPlugin(Long id);

    /**
     * 卸载插件
     *
     * @param id 插件 Id
     */
    void uninstall(Long id);

    /**
     * 激活插件
     *
     * @param id 插件 Id
     */
    void active(Long id);

    /**
     * 禁用插件
     *
     * @param id 插件 Id
     */
    void disable(Long id);

}
