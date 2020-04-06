package com.lushstar.pagoda.web.remote;

import com.lushstar.pagoda.web.vo.AppPluginVo;

import java.util.List;

/**
 * <p>description : AppPlugin
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:55
 */
public interface AppPluginRemote {

    List<AppPluginVo> findByAppId(Long id);

    AppPluginVo install(Long appId, Long pluginId);

    AppPluginVo active(Long appId, Long pluginId);

    AppPluginVo disable(Long appId, Long pluginId);

    AppPluginVo uninstall(Long appId, Long pluginId);
}
