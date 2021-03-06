package com.lushstar.pagoda.web.remote.impl;

import com.lushstar.pagoda.web.remote.PluginRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lushstar.pagoda.web.vo.PluginVo;
import com.lushstar.pagoda.web.vo.WebResponse;

import java.util.Date;
import java.util.List;

/**
 * <p>description : PluginWebServiceImpl
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:07
 */
@Slf4j
@Service
public class PluginRemoteImpl implements PluginRemote {

    @Value("${pagoda.service.url}")
    private String routeUrl;

    private String prefix = "/service/plugin";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<PluginVo> list() {
        ParameterizedTypeReference<WebResponse<List<PluginVo>>> typeRef = new ParameterizedTypeReference<WebResponse<List<PluginVo>>>() {
        };
        ResponseEntity<WebResponse<List<PluginVo>>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/list",
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public PluginVo add(PluginVo pluginVo) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        RestTemplate restTemplate = new RestTemplate();
        Date now = new Date();
        pluginVo.setCreateTime(now);
        pluginVo.setUpdateTime(now);
        pluginVo.setDel(false);
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/add",
                HttpMethod.POST, new HttpEntity<>(pluginVo), typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public PluginVo find(Long id) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/find/" + id,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public PluginVo update(PluginVo pluginVo) {
        ParameterizedTypeReference<WebResponse<PluginVo>> typeRef = new ParameterizedTypeReference<WebResponse<PluginVo>>() {
        };
        pluginVo.setUpdateTime(new Date());
        ResponseEntity<WebResponse<PluginVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/update",
                HttpMethod.POST, new HttpEntity<>(pluginVo), typeRef);
        return responseEntity.getBody().getData();
    }

}
