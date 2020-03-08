package pers.masteryourself.lushstar.pagoda.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.masteryourself.lushstar.pagoda.config.dal.AppRepository;
import pers.masteryourself.lushstar.pagoda.config.model.AppEntity;
import pers.masteryourself.lushstar.pagoda.service.service.AppService;

import java.util.List;

/**
 * <p>description : AppServiceImpl
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
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
    public AppEntity save(AppEntity appEntity) {
        return appRepository.saveAndFlush(appEntity);
    }
}
