package com.github.lushstar.pagoda.service.service;

import com.github.lushstar.pagoda.dal.model.AppPluginEntity;

import java.util.List;

/**
 * <p>description : AppPluginService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:10
 */
public interface AppPluginService {

    List<AppPluginEntity> findByAppId(Long appId);

    AppPluginEntity save(AppPluginEntity appPluginEntity);

    AppPluginEntity findByAppIdAndPluginId(Long appId, Long pluginId);

}
