package pers.masteryourself.lushstar.pagoda.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pers.masteryourself.lushstar.pagoda.web.vo.PluginVo;
import pers.masteryourself.lushstar.pagoda.web.vo.WebResponse;

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

    @GetMapping(value = "find/{id}")
    public WebResponse<PluginVo> findById(@PathVariable Long id) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/find/" + id,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody();
    }

}
