package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.PluginRemote;
import com.github.lushstar.pagoda.api.request.plugin.PluginAddRequest;
import com.github.lushstar.pagoda.api.request.plugin.PluginBaseRequest;
import com.github.lushstar.pagoda.api.request.plugin.PluginDelRequest;
import com.github.lushstar.pagoda.api.request.plugin.PluginUpdateRequest;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.ex.PagodaExceptionEnum;
import com.github.lushstar.pagoda.dal.model.PluginEntity;
import com.github.lushstar.pagoda.service.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

/**
 * <p>description : ServicePluginController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/plugin")
@Slf4j
public class ServicePluginController implements PluginRemote {

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
    public ServiceResponse<PluginResponse> add(@RequestBody @Validated PluginAddRequest request) {
        // 属性校验
        this.check(request);
        // 保存
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(request, PluginEntity.class));
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
    public ServiceResponse<PluginResponse> update(@RequestBody @Validated PluginUpdateRequest request) {
        // 先查询
        Long id = request.getId();
        PluginEntity pluginEntityQuery = pluginService.findById(id);
        PagodaExceptionEnum.ID_DATA_NULL.notNull(pluginEntityQuery, id);
        mapperFacade.map(request, pluginEntityQuery);
        // 属性校验
        this.check(request);
        // 更新
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(pluginEntityQuery, PluginEntity.class));
        // 判断是否需要删除插件
        if (pluginEntity.isDel()) {
            log.info("{} 插件文件是否删除成功：{}", pluginEntity.getAddress(), new File(pluginEntity.getAddress()).delete());
        }
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginResponse.class));
    }

    @Override
    @PostMapping(value = "del")
    public ServiceResponse<PluginResponse> del(PluginDelRequest request) {
        // 先查询
        Long id = request.getId();
        PluginEntity pluginServiceQuery = pluginService.findById(id);
        PagodaExceptionEnum.ID_DATA_NULL.notNull(pluginServiceQuery, id);
        mapperFacade.map(request, pluginServiceQuery);
        // 更新
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(pluginServiceQuery, PluginEntity.class));
        return ServiceResponse.success(mapperFacade.map(pluginService.save(pluginEntity), PluginResponse.class));
    }

    private void check(PluginBaseRequest request) {
        // 判断名称是否重复
        PluginEntity pluginEntityByName = pluginService.findByName(request.getName());
        if (request instanceof PluginAddRequest) {
            PagodaExceptionEnum.PARAM_REPEAT.isNull(pluginEntityByName, "插件名称");
        } else if (request instanceof PluginUpdateRequest) {
            PluginUpdateRequest pluginUpdateRequest = (PluginUpdateRequest) request;
            if (pluginEntityByName != null) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(pluginEntityByName.getId().equals(pluginUpdateRequest.getId()), "插件名称");
            }
        }
        // 判断类名是否重复
        PluginEntity pluginEntityByClassName = pluginService.findByClassName(request.getClassName());
        if (request instanceof PluginAddRequest) {
            PagodaExceptionEnum.PARAM_REPEAT.isNull(pluginEntityByClassName, "类名");
        } else if (request instanceof PluginUpdateRequest) {
            PluginUpdateRequest pluginUpdateRequest = (PluginUpdateRequest) request;
            if (pluginEntityByClassName != null) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(pluginEntityByClassName.getId().equals(pluginUpdateRequest.getId()), "类名");
            }
        }
    }

}
