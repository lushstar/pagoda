package pers.masteryourself.lushstar.pagoda.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;
import pers.masteryourself.lushstar.pagoda.service.response.ServiceResponse;
import pers.masteryourself.lushstar.pagoda.service.service.PluginService;

import java.util.List;

/**
 * <p>description : PluginServiceController
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@RestController
@RequestMapping(value = "service/plugin")
public class PluginServiceController {

    @Autowired
    private PluginService pluginService;

    @GetMapping(value = "list")
    public ServiceResponse<List<PluginBo>> list() {
        return ServiceResponse.success(pluginService.list());
    }

    @GetMapping(value = "find/{id}")
    public ServiceResponse<PluginBo> findById(@PathVariable Long id) {
        return ServiceResponse.success(pluginService.findById(id));
    }

}
