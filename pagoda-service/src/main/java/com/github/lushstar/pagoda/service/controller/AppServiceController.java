package com.github.lushstar.pagoda.service.controller;

import com.github.lushstar.pagoda.api.request.AppRequest;
import com.github.lushstar.pagoda.api.response.AppResponse;
import com.github.lushstar.pagoda.api.remote.AppRemote;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.dal.model.AppEntity;
import com.github.lushstar.pagoda.service.service.AppService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>description : AppServiceController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/app")
public class AppServiceController implements AppRemote {

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
    public ServiceResponse<AppResponse> add(@RequestBody AppRequest appRequest) {
        AppEntity appEntity = appService.save(mapperFacade.map(appRequest, AppEntity.class));
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
    public ServiceResponse<AppResponse> update(@RequestBody AppRequest appRequest) {
        AppEntity appEntity = appService.findById(appRequest.getId());
        if (!StringUtils.isEmpty(appRequest.getDescription())) {
            appEntity.setDescription(appRequest.getDescription());
        }
        if (appRequest.getUpdateTime() == null) {
            appEntity.setUpdateTime(new Date());
        } else {
            appEntity.setUpdateTime(appRequest.getUpdateTime());
        }
        if (appRequest.getDel() != null) {
            appEntity.setDel(appRequest.getDel());
        }
        return ServiceResponse.success(mapperFacade.map(appService.save(appEntity), AppResponse.class));
    }

}
