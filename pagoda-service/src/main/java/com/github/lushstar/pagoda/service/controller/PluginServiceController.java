package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.PluginRemote;
import com.github.lushstar.pagoda.api.request.PluginRequest;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.dal.model.PluginEntity;
import com.github.lushstar.pagoda.service.service.PluginService;
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
    public ServiceResponse<List<PluginResponse>> list() {
        List<PluginEntity> pluginEntityList = pluginService.list();
        return ServiceResponse.success(mapperFacade.mapAsList(pluginEntityList, PluginResponse.class));
    }

    @Override
    @PostMapping(value = "add")
    public ServiceResponse<PluginResponse> add(@RequestBody PluginRequest pluginRequest) {
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(pluginRequest, PluginEntity.class));
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginResponse.class));
    }

    @Override
    @GetMapping(value = "find/{id}")
    public ServiceResponse<PluginResponse> find(@PathVariable Long id) {
        PluginEntity pluginEntity = pluginService.findById(id);
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginResponse.class));
    }

    @Override
    @PostMapping(value = "update")
    public ServiceResponse<PluginResponse> update(@RequestBody PluginRequest pluginRequest) {
        PluginEntity pluginEntity = pluginService.findById(pluginRequest.getId());
        if (!StringUtils.isEmpty(pluginRequest.getName())) {
            pluginEntity.setName(pluginRequest.getName());
        }
        if (!StringUtils.isEmpty(pluginRequest.getDescription())) {
            pluginEntity.setDescription(pluginRequest.getDescription());
        }
        if (!StringUtils.isEmpty(pluginRequest.getClassName())) {
            pluginEntity.setClassName(pluginRequest.getClassName());
        }
        if (pluginRequest.getUpdateTime() == null) {
            pluginEntity.setUpdateTime(new Date());
        } else {
            pluginEntity.setUpdateTime(pluginRequest.getUpdateTime());
        }
        if (pluginRequest.getDel() != null) {
            pluginEntity.setDel(pluginRequest.getDel());
        }
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginResponse.class));
    }

}
