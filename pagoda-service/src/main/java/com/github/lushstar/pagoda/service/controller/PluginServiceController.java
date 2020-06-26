package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.PluginRemote;
import com.github.lushstar.pagoda.api.request.PluginRequest;
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
@Slf4j
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
    public ServiceResponse<PluginResponse> add(@RequestBody @Validated PluginRequest pluginRequest) {
        // 属性校验
        this.check(pluginRequest, false);
        // 保存
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
    public ServiceResponse<PluginResponse> update(@RequestBody @Validated PluginRequest pluginRequest) {
        // 属性校验
        this.check(pluginRequest, true);
        // 更新
        PluginEntity pluginEntity = pluginService.save(mapperFacade.map(pluginRequest, PluginEntity.class));
        // 判断是否需要删除插件
        if(pluginEntity.isDel()){
            log.info("{} 插件文件是否删除成功：{}", pluginEntity.getAddress(), new File(pluginEntity.getAddress()).delete());
        }
        return ServiceResponse.success(mapperFacade.map(pluginEntity, PluginResponse.class));
    }

    private void check(PluginRequest pluginRequest, boolean update) {
        // 判断名称是否重复
        PluginEntity pluginEntityByName = pluginService.findByName(pluginRequest.getName());
        if (update) {
            if (pluginEntityByName != null) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(pluginEntityByName.getId().equals(pluginRequest.getId()), "插件名称");
            }
        } else {
            PagodaExceptionEnum.PARAM_REPEAT.isNull(pluginEntityByName, "插件名称");
        }
        // 判断类名是否重复
        PluginEntity pluginEntityByClassName = pluginService.findByClassName(pluginRequest.getClassName());
        if (update) {
            if (pluginEntityByClassName != null) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(pluginEntityByClassName.getId().equals(pluginRequest.getId()), "类名");
            }
        } else {
            PagodaExceptionEnum.PARAM_REPEAT.isNull(pluginEntityByClassName, "类名");
        }
    }

}
