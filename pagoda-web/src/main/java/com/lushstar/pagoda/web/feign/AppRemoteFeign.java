package com.lushstar.pagoda.web.feign;

import com.lushstar.pagoda.api.remote.AppRemote;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>description : AppRemoteFeign
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/17 21:21
 */
@FeignClient(name = "app", url = "${pagoda.service.url}/service/app")
public interface AppRemoteFeign extends AppRemote {
}
