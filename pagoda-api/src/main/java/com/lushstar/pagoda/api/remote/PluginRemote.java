package com.lushstar.pagoda.api.remote;

import com.lushstar.pagoda.api.bo.PluginDto;
import com.lushstar.pagoda.api.response.ServiceResponse;
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
    ServiceResponse<List<PluginDto>> list();

    @PostMapping(value = "add")
    ServiceResponse<PluginDto> add(@RequestBody PluginDto pluginBo);

    @GetMapping(value = "find/{id}")
    ServiceResponse<PluginDto> find(@PathVariable(value = "id") Long id);

    @PostMapping(value = "update")
    ServiceResponse<PluginDto> update(@RequestBody PluginDto pluginBo);

}
