package com.lushstar.pagoda.client;


import com.lushstar.pagoda.client.core.PluginChangeMetadata;

/**
 * <p>description : PluginFactory
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 22:10
 */
public interface PluginFactory {

    /**
     * 安装插件
     *
     * @param pluginChangeMetadata
     */
    void install(PluginChangeMetadata pluginChangeMetadata);

    /**
     * 判断是否有插件
     *
     * @param id
     * @return
     */
    boolean hasPlugin(Long id);

    /**
     * 卸载插件
     *
     * @param id
     */
    void uninstall(Long id);

    /**
     * 激活插件
     *
     * @param id
     */
    void active(Long id);

    /**
     * 禁用插件
     *
     * @param id
     */
    void disable(Long id);

}
