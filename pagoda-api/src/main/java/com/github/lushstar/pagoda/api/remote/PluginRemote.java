package com.github.lushstar.pagoda.api.remote;

import com.github.lushstar.pagoda.api.request.PluginRequest;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.api.response.PluginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>description : PluginRemote
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:07
 */
public interface PluginRemote {

    @GetMapping(value = "list")
    ServiceResponse<List<PluginResponse>> list();

    @PostMapping(value = "add")
    ServiceResponse<PluginResponse> add(@RequestBody PluginRequest pluginRequest);

    @GetMapping(value = "find/{id}")
    ServiceResponse<PluginResponse> find(@PathVariable(value = "id") Long id);

    @PostMapping(value = "update")
    ServiceResponse<PluginResponse> update(@RequestBody PluginRequest pluginRequest);

}
