package pers.masteryourself.lushstar.pagoda.service.controller;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.masteryourself.lushstar.pagoda.config.model.AppEntity;
import pers.masteryourself.lushstar.pagoda.service.bo.AppBo;
import pers.masteryourself.lushstar.pagoda.service.response.ServiceResponse;
import pers.masteryourself.lushstar.pagoda.service.service.AppService;

import java.util.Date;
import java.util.List;

/**
 * <p>description : AppServiceController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/app")
public class AppServiceController {

    @Autowired
    private AppService appService;

    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping(value = "list")
    public ServiceResponse<List<AppBo>> list() {
        List<AppEntity> appEntityList = appService.list();
        return ServiceResponse.success(mapperFacade.mapAsList(appEntityList, AppBo.class));
    }

    @PostMapping(value = "add")
    public ServiceResponse<AppBo> add(@RequestBody AppBo appBo) {
        AppEntity appEntity = appService.save(mapperFacade.map(appBo, AppEntity.class));
        return ServiceResponse.success(mapperFacade.map(appEntity, AppBo.class));
    }

    @GetMapping(value = "find/{id}")
    public ServiceResponse<AppBo> findById(@PathVariable Long id) {
        AppEntity appEntity = appService.findById(id);
        return ServiceResponse.success(mapperFacade.map(appEntity, AppBo.class));
    }

    @PostMapping(value = "update")
    public ServiceResponse<AppBo> update(@RequestBody AppBo appBo) {
        AppEntity appEntity = appService.findById(appBo.getId());
        if (!StringUtils.isEmpty(appBo.getDescription())) {
            appEntity.setDescription(appBo.getDescription());
        }
        if (appBo.getUpdateTime() == null) {
            appEntity.setUpdateTime(new Date());
        } else {
            appEntity.setUpdateTime(appBo.getUpdateTime());
        }
        if (appBo.getDel() != null) {
            appEntity.setDel(appBo.getDel());
        }
        return ServiceResponse.success(mapperFacade.map(appService.save(appEntity), AppBo.class));
    }

}
