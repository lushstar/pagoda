package com.lushstar.pagoda.service.service;

import com.lushstar.pagoda.dal.model.AppEntity;

import java.util.List;

/**
 * <p>description : AppService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
public interface AppService {

    List<AppEntity> list();

    AppEntity findById(Long id);

    AppEntity save(AppEntity appEntity);

    AppEntity findByName(String appName);
}
