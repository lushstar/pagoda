package pers.masteryourself.lushstar.pagoda.service.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.masteryourself.lushstar.pagoda.config.dal.PluginRepository;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;
import pers.masteryourself.lushstar.pagoda.service.service.PluginService;

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
    private MapperFacade mapperFacade;

    @Autowired
    private PluginRepository pluginRepository;

    @Override
    public List<PluginBo> list() {
        return mapperFacade.mapAsList(pluginRepository.findAll(), PluginBo.class);
    }

    @Override
    public PluginBo findById(Long id) {
        return mapperFacade.map(pluginRepository.findById(id).orElse(null), PluginBo.class);
    }

}
