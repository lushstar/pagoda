package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.PluginRemote;
import com.github.lushstar.pagoda.api.request.PluginRequest;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.ex.PagodaExceptionEnum;
import com.github.lushstar.pagoda.dal.model.PluginEntity;
import com.github.lushstar.pagoda.service.service.PluginService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ServiceResponse<PluginResponse> add(@RequestBody @Validated PluginRequest pluginRequest) {
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
        this.check(pluginRequest, true);
        // 创建时间不更改
//        PluginEntity oldPluginEntity = pluginService.findById(pluginRequest.getId());
//        PluginEntity newPluginEntity = mapperFacade.map(pluginRequest, PluginEntity.class);
//        newPluginEntity.setCreateTime(oldPluginEntity.getCreateTime());
        return ServiceResponse.success(mapperFacade.map(pluginService.save(mapperFacade.map(pluginRequest, PluginEntity.class)), PluginResponse.class));
    }

    private void check(PluginRequest pluginRequest, boolean update) {
        // 判断名称是否重复
        List<PluginEntity> pluginEntityListByName = pluginService.findByName(pluginRequest.getName());
        if (update) {
            if (pluginEntityListByName != null && pluginEntityListByName.size() == 1) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(pluginEntityListByName.get(0).getId().equals(pluginRequest.getId()), "插件名称");
            }
        } else {
            PagodaExceptionEnum.PARAM_REPEAT.isEmpty(pluginEntityListByName, "插件名称");
        }
        // 判断类名是否重复
        List<PluginEntity> pluginEntityListByClassName = pluginService.findByClassName(pluginRequest.getClassName());
        if (update) {
            if (pluginEntityListByClassName != null && pluginEntityListByClassName.size() == 1) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(pluginEntityListByClassName.get(0).getId().equals(pluginRequest.getId()), "类名");
            }
        } else {
            PagodaExceptionEnum.PARAM_REPEAT.isEmpty(pluginEntityListByClassName, "类名");
        }
    }

}
