package pers.masteryourself.lushstar.pagoda.service.service;

import pers.masteryourself.lushstar.pagoda.config.model.AppPluginEntity;

import java.util.List;

/**
 * <p>description : AppPluginService
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
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
