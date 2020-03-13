package com.lushstar.pagoda.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lushstar.pagoda.dal.repository.AppPluginRepository;
import com.lushstar.pagoda.dal.model.AppPluginEntity;
import com.lushstar.pagoda.service.service.AppPluginService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>description : AppPluginServiceImpl
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:10
 */
@Service
public class AppPluginServiceImpl implements AppPluginService {

    @Autowired
    private AppPluginRepository appPluginRepository;

    @Override
    public List<AppPluginEntity> findByAppId(Long appId) {
        return appPluginRepository.findByAppId(appId);
    }

    @Override
    @Transactional
    public AppPluginEntity save(AppPluginEntity appPluginEntity) {
        return appPluginRepository.save(appPluginEntity);
    }

    @Override
    public AppPluginEntity findByAppIdAndPluginId(Long appId, Long pluginId) {
        return appPluginRepository.findByAppIdAndPluginId(appId, pluginId);
    }

}
