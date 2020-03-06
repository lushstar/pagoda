package pers.masteryourself.lushstar.pagoda.client;


import pers.masteryourself.lushstar.pagoda.client.po.Plugin;

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
     * @param plugin
     */
    void install(Plugin plugin);

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
