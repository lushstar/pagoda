package com.github.lushstar.pagoda.web.feign;

import com.github.lushstar.pagoda.api.remote.PluginRemote;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>description : PluginRemoteFeign
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/17 21:07
 */
@FeignClient(name = "plugin", url = "${pagoda.service.url}/service/plugin")
public interface PluginRemoteFeign extends PluginRemote {
}
