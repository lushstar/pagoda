package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.dto.AppPluginDto;
import com.github.lushstar.pagoda.api.dto.PluginChangeMetadata;
import com.github.lushstar.pagoda.api.dto.SourceType;
import com.github.lushstar.pagoda.api.remote.AppPluginRemote;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.dal.model.AppEntity;
import com.github.lushstar.pagoda.dal.model.AppPluginEntity;
import com.github.lushstar.pagoda.dal.model.PluginEntity;
import com.github.lushstar.pagoda.service.event.PluginChangeEvent;
import com.github.lushstar.pagoda.service.service.AppPluginService;
import com.github.lushstar.pagoda.service.service.AppService;
import com.github.lushstar.pagoda.service.service.EventService;
import com.github.lushstar.pagoda.service.service.PluginService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>description : AppPluginServiceController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:06
 */
@RestController
@RequestMapping(value = "service/app/plugin")
public class AppPluginServiceController implements AppPluginRemote {

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

    @Override
    @GetMapping(value = "findByAppId/{appId}")
    public ServiceResponse<List<AppPluginDto>> findByAppId(Long appId) {
        List<AppPluginEntity> appPluginEntityList = appPluginService.findByAppId(appId);
        return ServiceResponse.success(mapperFacade.mapAsList(appPluginEntityList, AppPluginDto.class));
    }

    @Override
    @GetMapping(value = "install/{appId}/{pluginId}")
    public ServiceResponse<AppPluginDto> install(Long appId, Long pluginId) {
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
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginDto.class));
    }

    @Override
    @GetMapping(value = "active/{appId}/{pluginId}")
    public ServiceResponse<AppPluginDto> active(Long appId, Long pluginId) {
        AppPluginEntity oldAppPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        oldAppPluginEntity.setActive(true);
        oldAppPluginEntity.setUpdateTime(new Date());
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.ACTIVE);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginDto.class));
    }

    @Override
    @GetMapping(value = "disable/{appId}/{pluginId}")
    public ServiceResponse<AppPluginDto> disable(Long appId, Long pluginId) {
        AppPluginEntity oldAppPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        oldAppPluginEntity.setActive(false);
        oldAppPluginEntity.setUpdateTime(new Date());
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.DISABLE);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginDto.class));
    }

    @Override
    @GetMapping(value = "uninstall/{appId}/{pluginId}")
    public ServiceResponse<AppPluginDto> uninstall(Long appId, Long pluginId) {
        AppPluginEntity oldAppPluginEntity = appPluginService.findByAppIdAndPluginId(appId, pluginId);
        oldAppPluginEntity.setDel(true);
        oldAppPluginEntity.setUpdateTime(new Date());
        AppPluginEntity appPluginEntity = appPluginService.save(oldAppPluginEntity);
        this.sendPluginChangeEvent(appPluginEntity, SourceType.UNINSTALL);
        return ServiceResponse.success(mapperFacade.map(appPluginEntity, AppPluginDto.class));
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
