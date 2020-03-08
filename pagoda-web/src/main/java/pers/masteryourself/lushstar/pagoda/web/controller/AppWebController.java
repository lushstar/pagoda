package pers.masteryourself.lushstar.pagoda.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.masteryourself.lushstar.pagoda.web.remote.AppPluginRemote;
import pers.masteryourself.lushstar.pagoda.web.remote.AppRemote;
import pers.masteryourself.lushstar.pagoda.web.remote.PluginRemote;
import pers.masteryourself.lushstar.pagoda.web.vo.AppPluginVo;
import pers.masteryourself.lushstar.pagoda.web.vo.AppVo;
import pers.masteryourself.lushstar.pagoda.web.vo.PluginVo;

import java.util.List;

/**
 * <p>description : AppWebController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@Controller
@RequestMapping(value = "web/app")
public class AppWebController {

    @Autowired
    private AppRemote appRemote;

    @Autowired
    private PluginRemote pluginRemote;

    @Autowired
    private AppPluginRemote appPluginRemote;

    @GetMapping(value = "list")
    public String list(Model model) {
        model.addAttribute("appVoList", appRemote.list());
        return "app/list";
    }

    @GetMapping(value = "toAdd")
    public String toAdd() {
        return "app/add";
    }

    @PostMapping(value = "add")
    public String add(AppVo appVo) {
        appVo.setDel(false);
        appRemote.add(appVo);
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("appVo", appRemote.find(id));
        return "app/edit";
    }

    @PostMapping(value = "edit")
    public String edit(AppVo appVo) {
        appRemote.update(appVo);
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id, Model model) {
        AppVo appVo = new AppVo();
        appVo.setId(id);
        appVo.setDel(true);
        model.addAttribute("appVo", appRemote.update(appVo));
        return "redirect:/web/app/list";
    }

    @GetMapping(value = "plugin/{appId}")
    public String plugin(@PathVariable Long appId, Model model) {
        List<PluginVo> pluginVoList = pluginRemote.list();
        List<AppPluginVo> appPluginVoList = appPluginRemote.findByAppId(appId);
        model.addAttribute("pluginVoList", pluginVoList);
        model.addAttribute("appId", appId);
        pluginVoList.forEach(pluginVo -> {
            appPluginVoList.forEach(appPluginVo -> {
                if (appPluginVo.getPluginId().equals(pluginVo.getId())) {
                    // 表示已经安装过
                    pluginVo.setInstall(true);
                    pluginVo.setActive(appPluginVo.getActive());
                }
            });
        });
        return "app/plugin/list";
    }

}
