package pers.masteryourself.lushstar.pagoda.service.controller;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;
import pers.masteryourself.lushstar.pagoda.service.response.ServiceResponse;
import pers.masteryourself.lushstar.pagoda.service.service.PluginService;

import java.util.Date;
import java.util.List;

/**
 * <p>description : PluginServiceController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/plugin")
public class PluginServiceController {

    @Autowired
    private PluginService pluginService;

    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping(value = "list")
    public ServiceResponse<List<PluginBo>> list() {
        List<PluginEntity> pluginEntityList = pluginService.list();
        return ServiceResponse.success(mapperFacade.mapAsList(pluginEntityList, PluginBo.class));
    }

    @PostMapping(value = "add")
    public ServiceResponse<PluginBo> add(@RequestBody PluginBo pluginBo) {
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(pluginBo, PluginEntity.class));
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginBo.class));
    }

    @GetMapping(value = "find/{id}")
    public ServiceResponse<PluginBo> findById(@PathVariable Long id) {
        PluginEntity pluginEntity = pluginService.findById(id);
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginBo.class));
    }

    @PostMapping(value = "update")
    public ServiceResponse<PluginBo> update(@RequestBody PluginBo pluginBo) {
        PluginEntity pluginEntity = pluginService.findById(pluginBo.getId());
        if (!StringUtils.isEmpty(pluginBo.getName())) {
            pluginEntity.setName(pluginBo.getName());
        }
        if (!StringUtils.isEmpty(pluginBo.getDescription())) {
            pluginEntity.setDescription(pluginBo.getDescription());
        }
        if (!StringUtils.isEmpty(pluginBo.getClassName())) {
            pluginEntity.setClassName(pluginBo.getClassName());
        }
        if (pluginBo.getUpdateTime() == null) {
            pluginEntity.setUpdateTime(new Date());
        } else {
            pluginEntity.setUpdateTime(pluginBo.getUpdateTime());
        }
        if (pluginBo.getDel() != null) {
            pluginEntity.setDel(pluginBo.getDel());
        }
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginBo.class));
    }

}
