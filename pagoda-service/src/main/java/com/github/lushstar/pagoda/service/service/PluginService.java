package com.github.lushstar.pagoda.service.service;

import com.github.lushstar.pagoda.dal.model.PluginEntity;

import java.util.List;

/**
 * <p>description : PluginService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
public interface PluginService {

    List<PluginEntity> list();

    PluginEntity findById(Long id);

    PluginEntity save(PluginEntity pluginEntity);

    PluginEntity findByName(String name);

    PluginEntity findByClassName(String className);
}
