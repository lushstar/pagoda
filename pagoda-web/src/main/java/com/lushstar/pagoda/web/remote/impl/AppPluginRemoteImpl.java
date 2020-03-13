package com.lushstar.pagoda.web.remote.impl;

import com.lushstar.pagoda.web.remote.AppPluginRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lushstar.pagoda.web.vo.AppPluginVo;
import com.lushstar.pagoda.web.vo.WebResponse;

import java.util.List;

/**
 * <p>description : AppPluginRemoteImpl
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:55
 */
@Service
public class AppPluginRemoteImpl implements AppPluginRemote {

    @Value("${pagoda.service.url}")
    private String routeUrl;

    private String prefix = "/service/app/plugin";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<AppPluginVo> findByAppId(Long id) {
        ParameterizedTypeReference<WebResponse<List<AppPluginVo>>> typeRef = new ParameterizedTypeReference<WebResponse<List<AppPluginVo>>>() {
        };
        ResponseEntity<WebResponse<List<AppPluginVo>>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/findByAppId/" + id,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppPluginVo install(Long appId, Long pluginId) {
        ParameterizedTypeReference<WebResponse<AppPluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppPluginVo>>() {
        };
        ResponseEntity<WebResponse<AppPluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/install/" + appId + "/" + pluginId,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppPluginVo active(Long appId, Long pluginId) {
        ParameterizedTypeReference<WebResponse<AppPluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppPluginVo>>() {
        };
        ResponseEntity<WebResponse<AppPluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/active/" + appId + "/" + pluginId,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppPluginVo disable(Long appId, Long pluginId) {
        ParameterizedTypeReference<WebResponse<AppPluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppPluginVo>>() {
        };
        ResponseEntity<WebResponse<AppPluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/disable/" + appId + "/" + pluginId,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppPluginVo uninstall(Long appId, Long pluginId) {
        ParameterizedTypeReference<WebResponse<AppPluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppPluginVo>>() {
        };
        ResponseEntity<WebResponse<AppPluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/uninstall/" + appId + "/" + pluginId,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

}
