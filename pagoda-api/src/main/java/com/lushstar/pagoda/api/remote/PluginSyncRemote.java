package com.lushstar.pagoda.api.remote;

import com.lushstar.pagoda.api.bo.PluginChangeMetadata;
import com.lushstar.pagoda.api.response.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

/**
 * <p>description : PluginRemote
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/17 19:06
 */
public interface PluginSyncRemote {

    @RequestMapping("part/{appName}")
    DeferredResult<ResponseEntity<PluginChangeMetadata>> partSync(@PathVariable(value = "appName") String appName);

    @RequestMapping("full/{appName}")
    ServiceResponse<List<PluginChangeMetadata>> fullSync(@PathVariable(value = "appName") String appName);
}
