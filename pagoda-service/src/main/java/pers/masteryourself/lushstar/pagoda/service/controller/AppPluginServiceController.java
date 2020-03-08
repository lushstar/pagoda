package pers.masteryourself.lushstar.pagoda.service.controller;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.masteryourself.lushstar.pagoda.config.model.AppPluginEntity;
import pers.masteryourself.lushstar.pagoda.service.bo.AppPluginBo;
import pers.masteryourself.lushstar.pagoda.service.response.ServiceResponse;
import pers.masteryourself.lushstar.pagoda.service.service.AppPluginService;

import java.util.Date;
import java.util.List;

/**
 * <p>description : AppPluginServiceController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:06
 */
@RestController
@RequestMapping(value = "service/app/plugin")
public class AppPluginServiceController {

    @Autowired
    private AppPluginService appPluginService;

    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping(value = "findByAppId/{appId}")
    public ServiceResponse<List<AppPluginBo>> findByAppId(@PathVariable Long appId) {
        List<AppPluginEntity> appPluginEntityList = appPluginService.findByAppId(appId);
        return ServiceResponse.success(mapperFacade.mapAsList(appPluginEntityList, AppPluginBo.class));
    }

    @GetMapping(value = "install/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> install(@PathVariable Long appId, @PathVariable Long pluginId) {
        Date now = new Date();
        AppPluginEntity appPluginEntity = new AppPluginEntity();
        appPluginEntity.setAppId(appId);
        appPluginEntity.setPluginId(pluginId);
        appPluginEntity.setCreateTime(now);
        appPluginEntity.setUpdateTime(now);
        appPluginEntity.setDel(false);
        appPluginEntity.setActive(false);
        return ServiceResponse.success(mapperFacade.map(appPluginService.save(appPluginEntity), AppPluginBo.class));
    }

    @GetMapping(value = "active/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> active(@PathVariable Long appId, @PathVariable Long pluginId) {
        AppPluginEntity appPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        appPluginEntity.setActive(true);
        appPluginEntity.setUpdateTime(new Date());
        return ServiceResponse.success(mapperFacade.map(appPluginService.save(appPluginEntity), AppPluginBo.class));
    }

    @GetMapping(value = "disable/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> disable(@PathVariable Long appId, @PathVariable Long pluginId) {
        AppPluginEntity appPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        appPluginEntity.setActive(false);
        appPluginEntity.setUpdateTime(new Date());
        return ServiceResponse.success(mapperFacade.map(appPluginService.save(appPluginEntity), AppPluginBo.class));
        /*PluginEntity pluginEntity = pluginService.findById(appPluginBo.getId());
        pluginEntity.setUpdateTime(appPluginBo.getUpdateTime());
        appPluginBo.setSourceType(SourceType.DISABLE);
        eventService.sendEvent(new PluginChangeEvent(appPluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), AppPluginBo.class));*/
    }

    @GetMapping(value = "uninstall/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> uninstall(@PathVariable Long appId, @PathVariable Long pluginId) {
        AppPluginEntity appPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        appPluginEntity.setDel(true);
        appPluginEntity.setUpdateTime(new Date());
        return ServiceResponse.success(mapperFacade.map(appPluginService.save(appPluginEntity), AppPluginBo.class));
        /*PluginEntity pluginEntity = pluginService.findById(appPluginBo.getId());
        pluginEntity.setUpdateTime(appPluginBo.getUpdateTime());
        appPluginBo.setSourceType(SourceType.UNINSTALL);
        appPluginBo.setDel(true);
        eventService.sendEvent(new PluginChangeEvent(appPluginBo));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), AppPluginBo.class));*/
    }

}
