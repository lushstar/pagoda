package pers.masteryourself.lushstar.pagoda.service.controller;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.masteryourself.lushstar.pagoda.config.model.AppEntity;
import pers.masteryourself.lushstar.pagoda.config.model.AppPluginEntity;
import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;
import pers.masteryourself.lushstar.pagoda.service.bo.AppPluginBo;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginChangeMetadata;
import pers.masteryourself.lushstar.pagoda.service.bo.SourceType;
import pers.masteryourself.lushstar.pagoda.service.event.PluginChangeEvent;
import pers.masteryourself.lushstar.pagoda.service.response.ServiceResponse;
import pers.masteryourself.lushstar.pagoda.service.service.AppPluginService;
import pers.masteryourself.lushstar.pagoda.service.service.AppService;
import pers.masteryourself.lushstar.pagoda.service.service.EventService;
import pers.masteryourself.lushstar.pagoda.service.service.PluginService;

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
    private AppService appService;

    @Autowired
    private PluginService pluginService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "findByAppId/{appId}")
    public ServiceResponse<List<AppPluginBo>> findByAppId(@PathVariable Long appId) {
        List<AppPluginEntity> appPluginEntityList = appPluginService.findByAppId(appId);
        return ServiceResponse.success(mapperFacade.mapAsList(appPluginEntityList, AppPluginBo.class));
    }

    @GetMapping(value = "install/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> install(@PathVariable Long appId, @PathVariable Long pluginId) {
        Date now = new Date();
        AppPluginEntity oldAppPluginEntity = new AppPluginEntity();
        oldAppPluginEntity.setAppId(appId);
        oldAppPluginEntity.setPluginId(pluginId);
        oldAppPluginEntity.setCreateTime(now);
        oldAppPluginEntity.setUpdateTime(now);
        oldAppPluginEntity.setDel(false);
        oldAppPluginEntity.setActive(false);
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.INSTALL);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginBo.class));
    }


    @GetMapping(value = "active/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> active(@PathVariable Long appId, @PathVariable Long pluginId) {
        AppPluginEntity oldAppPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        oldAppPluginEntity.setActive(true);
        oldAppPluginEntity.setUpdateTime(new Date());
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.ACTIVE);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginBo.class));
    }

    @GetMapping(value = "disable/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> disable(@PathVariable Long appId, @PathVariable Long pluginId) {
        AppPluginEntity oldAppPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        oldAppPluginEntity.setActive(false);
        oldAppPluginEntity.setUpdateTime(new Date());
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.DISABLE);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginBo.class));
    }

    @GetMapping(value = "uninstall/{appId}/{pluginId}")
    public ServiceResponse<AppPluginBo> uninstall(@PathVariable Long appId, @PathVariable Long pluginId) {
        AppPluginEntity oldAppPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        oldAppPluginEntity.setDel(true);
        oldAppPluginEntity.setUpdateTime(new Date());
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.UNINSTALL);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginBo.class));
    }

    /**
     * 事件通知
     *
     * @param appPluginEntity
     * @param sourceType
     */
    private void sendPluginChangeEvent(AppPluginEntity appPluginEntity, SourceType sourceType) {
        AppEntity appEntity = appService.findById(appPluginEntity.getAppId());
        PluginEntity pluginEntity = pluginService.findById(appPluginEntity.getPluginId());
        PluginChangeMetadata pluginChangeMetadata = new PluginChangeMetadata();
        pluginChangeMetadata.setId(appPluginEntity.getId());
        pluginChangeMetadata.setActive(appPluginEntity.isActive());
        pluginChangeMetadata.setAddress(pluginEntity.getAddress());
        pluginChangeMetadata.setClassName(pluginEntity.getClassName());
        pluginChangeMetadata.setSourceType(sourceType);
        pluginChangeMetadata.setAppName(appEntity.getName());
        eventService.sendEvent(new PluginChangeEvent(pluginChangeMetadata));
    }

}
