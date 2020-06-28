package com.github.lushstar.pagoda.web.controller;

import com.github.lushstar.pagoda.api.request.PluginRequest;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.ex.PagodaExceptionEnum;
import com.github.lushstar.pagoda.web.feign.PluginRemoteFeign;
import com.github.lushstar.pagoda.web.request.WebPluginRequest;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>description : WebPluginController
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
public class WebPluginController {

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
    public String add(@Validated WebPluginRequest webPluginRequest, @RequestParam("jarFile") MultipartFile jarFile) throws Exception {
        // 校验文件
        PagodaExceptionEnum.PARAM_EMPTY.hasText(jarFile.getOriginalFilename(), "jar 包文件路径");
        // 保存
        PluginRequest pluginRequest = this.buildPluginRequest(webPluginRequest, jarFile);
        pluginRemoteFeign.add(pluginRequest).log();
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        model.addAttribute("pluginVo", pluginRemoteFeign.find(id).getData());
        return "plugin/edit";
    }

    @PostMapping(value = "edit")
    public String edit(@Validated WebPluginRequest webPluginRequest, @RequestParam("jarFile") MultipartFile jarFile) throws Exception {
        // 先查询
        PluginRequest pluginRequest = mapperFacade.map(pluginRemoteFeign.find(webPluginRequest.getId()).getData(), PluginRequest.class);
        PagodaExceptionEnum.ID_DATA_NULL.notNull(pluginRequest, webPluginRequest.getId());
        // 判断是否上传了新的插件
        if (StringUtils.hasText(jarFile.getOriginalFilename())) {
            // 删除原来的 jar 包插件
            ServiceResponse<PluginResponse> serviceResponse = pluginRemoteFeign.find(webPluginRequest.getId());
            String oldAddress = serviceResponse.getData().getAddress();
            log.info("{} 原插件文件是否删除成功：{}", oldAddress, new File(oldAddress).delete());
            // 保存新的 jar 包插件
            String destFile = site + File.separator + jarFile.getOriginalFilename();
            jarFile.transferTo(new File(destFile));
            pluginRequest.setAddress(destFile);
        }
        // 更新
        pluginRequest.setClassName(webPluginRequest.getClassName());
        pluginRequest.setDescription(webPluginRequest.getDescription());
        pluginRemoteFeign.update(pluginRequest);
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable Long id) {
        // 先查询
        PluginRequest pluginRequest = mapperFacade.map(pluginRemoteFeign.find(id).getData(), PluginRequest.class);
        PagodaExceptionEnum.ID_DATA_NULL.notNull(pluginRequest, id);
        // 删除
        pluginRequest.setDel(true);
        pluginRemoteFeign.update(pluginRequest);
        return "redirect:/web/plugin/list";
    }

    private PluginRequest buildPluginRequest(WebPluginRequest webPluginRequest, MultipartFile jarFile) throws IOException {
        PluginRequest pluginRequest = mapperFacade.map(webPluginRequest, PluginRequest.class);
        pluginRequest.setDel(false);
        String destFile = site + File.separator + jarFile.getOriginalFilename();
        jarFile.transferTo(new File(destFile));
        pluginRequest.setAddress(destFile);
        pluginRequest.setDel(false);
        return pluginRequest;
    }

}
