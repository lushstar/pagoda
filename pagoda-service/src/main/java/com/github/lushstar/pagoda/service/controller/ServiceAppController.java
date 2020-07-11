package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.remote.AppRemote;
import com.github.lushstar.pagoda.api.request.app.AppAddRequest;
import com.github.lushstar.pagoda.api.request.app.AppBaseRequest;
import com.github.lushstar.pagoda.api.request.app.AppRegisterRequest;
import com.github.lushstar.pagoda.api.request.app.AppUpdateRequest;
import com.github.lushstar.pagoda.api.response.AppResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.ex.PagodaExceptionEnum;
import com.github.lushstar.pagoda.dal.model.AppEntity;
import com.github.lushstar.pagoda.service.register.RegisterCenter;
import com.github.lushstar.pagoda.service.service.AppService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>description : ServiceAppController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/app")
public class ServiceAppController implements AppRemote {

    @Autowired
    private AppService appService;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    @GetMapping(value = "list")
    public ServiceResponse<List<AppResponse>> list() {
        List<AppEntity> appEntityList = appService.list();
        return ServiceResponse.success(mapperFacade.mapAsList(appEntityList, AppResponse.class));
    }

    @Override
    @PostMapping(value = "add")
    public ServiceResponse<AppResponse> add(@RequestBody AppAddRequest request) {
        // 属性校验
        this.check(request);
        // 保存
        AppEntity appEntity = appService.save(mapperFacade.map(request, AppEntity.class));
        return ServiceResponse.success(mapperFacade.map(appEntity, AppResponse.class));
    }

    @Override
    @GetMapping(value = "find/{id}")
    public ServiceResponse<AppResponse> find(@PathVariable Long id) {
        AppEntity appEntity = appService.findById(id);
        return ServiceResponse.success(mapperFacade.map(appEntity, AppResponse.class));
    }

    @Override
    @PostMapping(value = "update")
    public ServiceResponse<AppResponse> update(@Validated @RequestBody AppUpdateRequest request) {
        // 属性校验
        this.check(request);
        // 更新
        AppEntity appEntity = appService.save(mapperFacade.map(request, AppEntity.class));
        return ServiceResponse.success(mapperFacade.map(appService.save(appEntity), AppResponse.class));
    }

    @Override
    public ServiceResponse<Boolean> register(@Validated AppRegisterRequest request) {
        String name = request.getName();
        AppEntity appEntity = appService.findByName(name);
        PagodaExceptionEnum.DATA_NULL.notNull(appEntity);
        RegisterCenter.register(name, request.getInstanceId());
        return ServiceResponse.success(true);
    }

    private void check(AppBaseRequest request) {
        // 查询是否有同名
        AppEntity appEntity = appService.findByName(request.getName());
        if (request instanceof AppAddRequest) {
            PagodaExceptionEnum.PARAM_REPEAT.isNull(appEntity, "应用名称");
        } else if (request instanceof AppUpdateRequest) {
            AppUpdateRequest appUpdateRequest = (AppUpdateRequest) request;
            if (appEntity != null) {
                PagodaExceptionEnum.PARAM_REPEAT.isTrue(appEntity.getId().equals(appUpdateRequest.getId()), "应用名称");
            }
        }
    }

}
