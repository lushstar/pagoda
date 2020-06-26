package com.github.lushstar.pagoda.web.controller;

import com.github.lushstar.pagoda.api.request.AppRequest;
import com.github.lushstar.pagoda.web.feign.AppRemoteFeign;
import com.github.lushstar.pagoda.web.vo.AppVo;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * <p>description : AppWebController
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
    public String add(AppVo appVo) {
        appVo.setDel(false);
        Date now = new Date();
        appVo.setCreateTime(now);
        appVo.setUpdateTime(now);
        appVo.setDel(false);
        appRemoteFeign.add(mapperFacade.map(appVo, AppRequest.class));
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("appVo", appRemoteFeign.find(id).getData());
        return "app/edit";
    }

    @PostMapping(value = "edit")
    public String edit(AppVo appVo) {
        appVo.setUpdateTime(new Date());
        appRemoteFeign.update(mapperFacade.map(appVo, AppRequest.class));
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id) {
        AppVo appVo = new AppVo();
        appVo.setId(id);
        appVo.setDel(true);
        appRemoteFeign.update(mapperFacade.map(appVo, AppRequest.class));
        return "redirect:/web/app/list";
    }

}
