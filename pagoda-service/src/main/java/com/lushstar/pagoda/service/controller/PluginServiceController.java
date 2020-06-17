package com.lushstar.pagoda.service.controller;

import com.lushstar.pagoda.api.bo.PluginDto;
import com.lushstar.pagoda.api.remote.PluginRemote;
import com.lushstar.pagoda.api.response.ServiceResponse;
import com.lushstar.pagoda.dal.model.PluginEntity;
import com.lushstar.pagoda.service.service.PluginService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>description : PluginServiceController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/plugin")
public class PluginServiceController implements PluginRemote {

    @Autowired
    private PluginService pluginService;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    @GetMapping(value = "list")
    public ServiceResponse<List<PluginDto>> list() {
        List<PluginEntity> pluginEntityList = pluginService.list();
        return ServiceResponse.success(mapperFacade.mapAsList(pluginEntityList, PluginDto.class));
    }

    @Override
    @PostMapping(value = "add")
    public ServiceResponse<PluginDto> add(@RequestBody PluginDto pluginBo) {
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(pluginBo, PluginEntity.class));
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginDto.class));
    }

    @Override
    @GetMapping(value = "find/{id}")
    public ServiceResponse<PluginDto> find(@PathVariable Long id) {
        PluginEntity pluginEntity = pluginService.findById(id);
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginDto.class));
    }

    @Override
    @PostMapping(value = "update")
    public ServiceResponse<PluginDto> update(@RequestBody PluginDto pluginDto) {
        PluginEntity pluginEntity = pluginService.findById(pluginDto.getId());
        if (!StringUtils.isEmpty(pluginDto.getName())) {
            pluginEntity.setName(pluginDto.getName());
        }
        if (!StringUtils.isEmpty(pluginDto.getDescription())) {
            pluginEntity.setDescription(pluginDto.getDescription());
        }
        if (!StringUtils.isEmpty(pluginDto.getClassName())) {
            pluginEntity.setClassName(pluginDto.getClassName());
        }
        if (pluginDto.getUpdateTime() == null) {
            pluginEntity.setUpdateTime(new Date());
        } else {
            pluginEntity.setUpdateTime(pluginDto.getUpdateTime());
        }
        if (pluginDto.getDel() != null) {
            pluginEntity.setDel(pluginDto.getDel());
        }
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginDto.class));
    }

}
