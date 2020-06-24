package com.github.lushstar.pagoda.web.controller;

import com.github.lushstar.pagoda.api.request.PluginRequest;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.web.feign.PluginRemoteFeign;
import com.github.lushstar.pagoda.web.vo.PluginVo;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * <p>description : PluginWebController
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@Controller
@RequestMapping(value = "web/plugin")
@Slf4j
public class PluginWebController {

    @Autowired
    private PluginRemoteFeign pluginRemoteFeign;

    @Autowired
    private MapperFacade mapperFacade;

    @Value("${pagoda.plugin.site}")
    private String site;

    @GetMapping(value = "list")
    public String list(Model model) {
        model.addAttribute("pluginVoList", pluginRemoteFeign.list().getData());
        return "plugin/list";
    }

    @GetMapping(value = "toAdd")
    public String toAdd() {
        return "plugin/add";
    }

    @PostMapping(value = "add")
    public String add(PluginVo pluginVo, @RequestParam("jarFile") MultipartFile jarFile) throws Exception {
        pluginVo.setDel(false);
        pluginVo.setActive(false);
        String destFile = site + File.separator + jarFile.getOriginalFilename();
        jarFile.transferTo(new File(destFile));
        pluginVo.setAddress(destFile);
        Date now = new Date();
        pluginVo.setCreateTime(now);
        pluginVo.setUpdateTime(now);
        pluginVo.setDel(false);
        pluginRemoteFeign.add(mapperFacade.map(pluginVo, PluginRequest.class));
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("pluginVo", pluginRemoteFeign.find(id).getData());
        return "plugin/edit";
    }

    @PostMapping(value = "edit")
    public String edit(PluginVo pluginVo, @RequestParam("jarFile") MultipartFile jarFile) throws Exception {
        ServiceResponse<PluginResponse> serviceResponse = pluginRemoteFeign.find(pluginVo.getId());
        String oldAddress = serviceResponse.getData().getAddress();
        log.info("{} 原插件文件是否删除成功：{}", oldAddress, new File(oldAddress).delete());
        String destFile = site + File.separator + jarFile.getOriginalFilename();
        jarFile.transferTo(new File(destFile));
        pluginVo.setAddress(destFile);
        pluginVo.setUpdateTime(new Date());
        pluginRemoteFeign.update(mapperFacade.map(pluginVo, PluginRequest.class));
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id) {
        PluginResponse pluginDto = pluginRemoteFeign.find(id).getData();
        PluginVo pluginVo = mapperFacade.map(pluginDto, PluginVo.class);
        log.info("{} 插件文件是否删除成功：{}", pluginVo.getAddress(), new File(pluginVo.getAddress()).delete());
        pluginVo.setUpdateTime(new Date());
        pluginVo.setDel(true);
        pluginVo.setUpdateTime(new Date());
        pluginRemoteFeign.update(mapperFacade.map(pluginVo, PluginRequest.class));
        return "redirect:/web/plugin/list";
    }

}
