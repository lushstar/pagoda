package com.github.lushstar.pagoda.web.feign;

import com.github.lushstar.pagoda.api.remote.AppPluginRemote;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>description : AppPluginRemoteFeign
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/17 21:26
 */
@FeignClient(name = "app-plugin", url = "${pagoda.service.url}/service/app/plugin")
public interface AppPluginRemoteFeign extends AppPluginRemote {
}
