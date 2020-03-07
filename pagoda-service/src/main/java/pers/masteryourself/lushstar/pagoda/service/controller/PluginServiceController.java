package pers.masteryourself.lushstar.pagoda.service.controller;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;
import pers.masteryourself.lushstar.pagoda.service.bo.SourceType;
import pers.masteryourself.lushstar.pagoda.service.event.PluginChangeEvent;
import pers.masteryourself.lushstar.pagoda.service.response.ServiceResponse;
import pers.masteryourself.lushstar.pagoda.service.service.EventService;
import pers.masteryourself.lushstar.pagoda.service.service.PluginService;

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

    @Autowired
    private EventService eventService;

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
        pluginEntity.setName(pluginBo.getName());
        pluginEntity.setDescription(pluginBo.getDescription());
        pluginEntity.setClassName(pluginBo.getClassName());
        pluginEntity.setUpdateTime(pluginBo.getUpdateTime());
        pluginEntity.setActive(pluginBo.isActive());
        pluginBo.setSourceType(SourceType.ACTIVE);
        eventService.sendEvent(new PluginChangeEvent(pluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginBo.class));
    }

    @PostMapping(value = "install")
    public ServiceResponse<PluginBo> install(@RequestBody PluginBo pluginBo) {
        PluginEntity pluginEntity = pluginService.findById(pluginBo.getId());
        pluginEntity.setUpdateTime(pluginBo.getUpdateTime());
        pluginBo.setSourceType(SourceType.INSTALL);
        eventService.sendEvent(new PluginChangeEvent(pluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginBo.class));
    }

    @PostMapping(value = "active")
    public ServiceResponse<PluginBo> active(@RequestBody PluginBo pluginBo) {
        PluginEntity pluginEntity = pluginService.findById(pluginBo.getId());
        pluginEntity.setUpdateTime(pluginBo.getUpdateTime());
        pluginEntity.setActive(true);
        pluginBo.setSourceType(SourceType.ACTIVE);
        eventService.sendEvent(new PluginChangeEvent(pluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginBo.class));
    }

    @PostMapping(value = "disable")
    public ServiceResponse<PluginBo> disable(@RequestBody PluginBo pluginBo) {
        PluginEntity pluginEntity = pluginService.findById(pluginBo.getId());
        pluginEntity.setUpdateTime(pluginBo.getUpdateTime());
        pluginEntity.setActive(false);
        pluginBo.setSourceType(SourceType.DISABLE);
        eventService.sendEvent(new PluginChangeEvent(pluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginBo.class));
    }

    @PostMapping(value = "uninstall")
    public ServiceResponse<PluginBo> uninstall(@RequestBody PluginBo pluginBo) {
        PluginEntity pluginEntity = pluginService.findById(pluginBo.getId());
        pluginEntity.setUpdateTime(pluginBo.getUpdateTime());
        pluginBo.setSourceType(SourceType.UNINSTALL);
        eventService.sendEvent(new PluginChangeEvent(pluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginBo.class));
    }

}
