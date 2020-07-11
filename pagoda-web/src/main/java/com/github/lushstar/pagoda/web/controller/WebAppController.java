package com.github.lushstar.pagoda.web.controller;

import com.github.lushstar.pagoda.api.request.AppRequest;
import com.github.lushstar.pagoda.api.response.AppResponse;
import com.github.lushstar.pagoda.common.ex.PagodaExceptionEnum;
import com.github.lushstar.pagoda.web.feign.AppRemoteFeign;
import com.github.lushstar.pagoda.web.request.WebAppRequest;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>description : WebAppController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@Controller
@RequestMapping(value = "web/app")
public class WebAppController {

    @Autowired
    private AppRemoteFeign appRemoteFeign;

    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping(value = "list")
    public String list(Model model) {
        model.addAttribute("appVoList", appRemoteFeign.list().getData());
        return "app/list";
    }

    @GetMapping(value = "toAdd")
    public String toAdd() {
        return "app/add";
    }

    @PostMapping(value = "add")
    public String add(@Validated WebAppRequest webAppRequest) {
        AppRequest appRequest = mapperFacade.map(webAppRequest, AppRequest.class);
        appRequest.setDel(false);
        appRemoteFeign.add(appRequest).log();
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("appVo", appRemoteFeign.find(id).getData());
        return "app/edit";
    }

    @PostMapping(value = "edit")
    public String edit(@Validated WebAppRequest webAppRequest) {
        // 先查询
        Long id = webAppRequest.getId();
        AppResponse appResponse = appRemoteFeign.find(id).getData();
        PagodaExceptionEnum.ID_DATA_NULL.notNull(appResponse, id);
        AppRequest appRequest = mapperFacade.map(appResponse, AppRequest.class);
        // 更新
        appRemoteFeign.update(appRequest);
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id) {
        // 先查询
        AppResponse appResponse = appRemoteFeign.find(id).getData();
        PagodaExceptionEnum.ID_DATA_NULL.notNull(appResponse, id);
        AppRequest appRequest = mapperFacade.map(appResponse, AppRequest.class);
        // 删除
        appRequest.setDel(true);
        appRemoteFeign.update(appRequest);
        return "redirect:/web/app/list";
    }

}
