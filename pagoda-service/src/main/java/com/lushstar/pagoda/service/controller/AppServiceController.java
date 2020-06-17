package com.lushstar.pagoda.service.controller;

import com.lushstar.pagoda.api.bo.AppDto;
import com.lushstar.pagoda.api.remote.AppRemote;
import com.lushstar.pagoda.api.response.ServiceResponse;
import com.lushstar.pagoda.dal.model.AppEntity;
import com.lushstar.pagoda.service.service.AppService;
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
    public ServiceResponse<List<AppDto>> list() {
        List<AppEntity> appEntityList = appService.list();
        return ServiceResponse.success(mapperFacade.mapAsList(appEntityList, AppDto.class));
    }

    @Override
    @PostMapping(value = "add")
    public ServiceResponse<AppDto> add(@RequestBody AppDto appDto) {
        AppEntity appEntity = appService.save(mapperFacade.map(appDto, AppEntity.class));
        return ServiceResponse.success(mapperFacade.map(appEntity, AppDto.class));
    }

    @Override
    @GetMapping(value = "find/{id}")
    public ServiceResponse<AppDto> find(@PathVariable Long id) {
        AppEntity appEntity = appService.findById(id);
        return ServiceResponse.success(mapperFacade.map(appEntity, AppDto.class));
    }

    @Override
    @PostMapping(value = "update")
    public ServiceResponse<AppDto> update(@RequestBody AppDto appDto) {
        AppEntity appEntity = appService.findById(appDto.getId());
        if (!StringUtils.isEmpty(appDto.getDescription())) {
            appEntity.setDescription(appDto.getDescription());
        }
        if (appDto.getUpdateTime() == null) {
            appEntity.setUpdateTime(new Date());
        } else {
            appEntity.setUpdateTime(appDto.getUpdateTime());
        }
        if (appDto.getDel() != null) {
            appEntity.setDel(appDto.getDel());
        }
        return ServiceResponse.success(mapperFacade.map(appService.save(appEntity), AppDto.class));
    }

}
