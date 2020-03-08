package pers.masteryourself.lushstar.pagoda.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.masteryourself.lushstar.pagoda.web.remote.PluginRemote;
import pers.masteryourself.lushstar.pagoda.web.vo.PluginVo;

/**
 * <p>description : PluginWebController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
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
    public String add(PluginVo pluginVo) {
        pluginVo.setDel(false);
        pluginVo.setActive(false);
        pluginRemote.add(pluginVo);
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("pluginVo", pluginRemote.find(id));
        return "plugin/edit";
    }

    @PostMapping(value = "edit")
    public String edit(PluginVo pluginVo, Model model) {
        model.addAttribute("pluginVo", pluginRemote.update(pluginVo));
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "{action}/{id}")
    public String action(@PathVariable String action,
                         @PathVariable Long id) {
        pluginRemote.update(id, action);
        return "redirect:/web/plugin/list";
    }

}
