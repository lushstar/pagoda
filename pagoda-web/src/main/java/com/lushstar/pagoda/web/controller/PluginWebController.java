package com.lushstar.pagoda.web.controller;

import com.lushstar.pagoda.web.remote.PluginRemote;
import com.lushstar.pagoda.web.vo.PluginVo;
import lombok.extern.slf4j.Slf4j;
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
    private PluginRemote pluginRemote;

    @Value("${pagoda.plugin.site}")
    private String site;

    @GetMapping(value = "list")
    public String list(Model model) {
        model.addAttribute("pluginVoList", pluginRemote.list());
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
        pluginRemote.add(pluginVo);
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("pluginVo", pluginRemote.find(id));
        return "plugin/edit";
    }

    @PostMapping(value = "edit")
    public String edit(PluginVo pluginVo, @RequestParam("jarFile") MultipartFile jarFile) throws Exception {
        String oldAddress = pluginRemote.find(pluginVo.getId()).getAddress();
        log.info("{} 原插件文件是否删除成功：{}", oldAddress, new File(oldAddress).delete());
        String destFile = site + File.separator + jarFile.getOriginalFilename();
        jarFile.transferTo(new File(destFile));
        pluginVo.setAddress(destFile);
        pluginRemote.update(pluginVo);
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id) {
        PluginVo pluginVo = pluginRemote.find(id);
        log.info("{} 插件文件是否删除成功：{}", pluginVo.getAddress(), new File(pluginVo.getAddress()).delete());
        pluginVo.setUpdateTime(new Date());
        pluginVo.setDel(true);
        pluginRemote.update(pluginVo);
        return "redirect:/web/plugin/list";
    }

}
