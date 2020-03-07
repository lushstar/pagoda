package pers.masteryourself.lushstar.pagoda.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pers.masteryourself.lushstar.pagoda.web.vo.PluginVo;
import pers.masteryourself.lushstar.pagoda.web.vo.WebResponse;

import java.util.Date;
import java.util.List;

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

    @Value("${pagoda.service.url}")
    private String routeUrl;

    private String prefix = "/service/plugin";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "list")
    public String list(Model model) {
        ParameterizedTypeReference<WebResponse<List<PluginVo>>> typeRef = new ParameterizedTypeReference<WebResponse<List<PluginVo>>>() {
        };
        ResponseEntity<WebResponse<List<PluginVo>>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/list",
                HttpMethod.GET, null, typeRef);
        model.addAttribute("pluginVoList", responseEntity.getBody().getData());
        return "plugin/list";
    }

    @GetMapping(value = "toAdd")
    public String toAdd() {
        return "plugin/add";
    }

    @PostMapping(value = "add")
    public String add(PluginVo pluginVo, Model model) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        RestTemplate restTemplate = new RestTemplate();
        Date now = new Date();
        pluginVo.setCreateTime(now);
        pluginVo.setUpdateTime(now);
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/add",
                HttpMethod.POST, new HttpEntity<>(pluginVo), typeRef);
        model.addAttribute("pluginVoList", responseEntity.getBody().getData());
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/find/" + id,
                HttpMethod.GET, null, typeRef);
        model.addAttribute("pluginVo", responseEntity.getBody().getData());
        return "plugin/edit";
    }

    @PostMapping(value = "edit")
    public String edit(PluginVo pluginVo, Model model) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        pluginVo.setUpdateTime(new Date());
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/update",
                HttpMethod.POST, new HttpEntity<>(pluginVo), typeRef);
        model.addAttribute("pluginVo", responseEntity.getBody().getData());
        return "redirect:/web/plugin/list";
    }

    @GetMapping(value = "{action}/{id}")
    public String action(@PathVariable String action,
                         @PathVariable Long id, Model model) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        ResponseEntity<WebResponse<PluginVo>> findResponseEntity = restTemplate.exchange(routeUrl + prefix + "/find/" + id,
                HttpMethod.GET, null, typeRef);
        PluginVo pluginVo = findResponseEntity.getBody().getData();
        String op;
        switch (action) {
            case "install":
                op = "install";
                break;
            case "active":
                pluginVo.setActive(true);
                op = "active";
                break;
            case "disable":
                pluginVo.setActive(false);
                op = "disable";
                break;
            case "uninstall":
                op = "uninstall";
                break;
            default:
                log.warn("操作有问题 {}", action);
                op = null;
                break;
        }
        if (op == null) {
            log.warn("操作有问题 {}, 请求取消", action);
            return "redirect:/web/plugin/list";
        }
        pluginVo.setUpdateTime(new Date());
        ResponseEntity<WebResponse<PluginVo>> updateResponseEntity = restTemplate.exchange(routeUrl + prefix + "/" + op,
                HttpMethod.POST, new HttpEntity<>(pluginVo), typeRef);
        model.addAttribute("pluginVo", updateResponseEntity.getBody().getData());
        return "redirect:/web/plugin/list";
    }

}
