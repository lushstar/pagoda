package com.lushstar.pagoda.web.controller;

import com.lushstar.pagoda.api.bo.AppDto;
import com.lushstar.pagoda.web.feign.AppRemoteFeign;
import com.lushstar.pagoda.web.vo.AppVo;
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
public class AppWebController {

    @Autowired
    private AppRemoteFeign appRemoteFeign;

    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping(value = "list")
    public String list(Model model) {
        model.addAttribute("appVoList", appRemoteFeign.list());
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
        appRemoteFeign.add(mapperFacade.map(appVo, AppDto.class));
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("appVo", appRemoteFeign.find(id));
        return "app/edit";
    }

    @PostMapping(value = "edit")
    public String edit(AppVo appVo) {
        appVo.setUpdateTime(new Date());
        appRemoteFeign.update(mapperFacade.map(appVo, AppDto.class));
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id) {
        AppVo appVo = new AppVo();
        appVo.setId(id);
        appVo.setDel(true);
        appRemoteFeign.update(mapperFacade.map(appVo, AppDto.class));
        return "redirect:/web/app/list";
    }

}
