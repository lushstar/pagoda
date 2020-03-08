package pers.masteryourself.lushstar.pagoda.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.masteryourself.lushstar.pagoda.config.dal.PluginRepository;
import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;
import pers.masteryourself.lushstar.pagoda.service.service.PluginService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>description : PluginServiceImpl
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@Service
public class PluginServiceImpl implements PluginService {

    @Autowired
    private PluginRepository pluginRepository;

    @Override
    public List<PluginEntity> list() {
        return pluginRepository.findAll();
    }

    @Override
    public PluginEntity findById(Long id) {
        return pluginRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public PluginEntity save(PluginEntity pluginEntity) {
        return pluginRepository.saveAndFlush(pluginEntity);
    }

}
