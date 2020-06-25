package com.github.lushstar.pagoda.service.service.impl;

import com.github.lushstar.pagoda.service.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.lushstar.pagoda.dal.repository.AppRepository;
import com.github.lushstar.pagoda.dal.model.AppEntity;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>description : AppServiceImpl
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;

    @Override
    public List<AppEntity> list() {
        return appRepository.findAll();
    }

    @Override
    public AppEntity findById(Long id) {
        return appRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public AppEntity save(AppEntity appEntity) {
        return appRepository.saveAndFlush(appEntity);
    }

    @Override
    public AppEntity findByName(String appName) {
        return appRepository.findByName(appName);
    }
}
