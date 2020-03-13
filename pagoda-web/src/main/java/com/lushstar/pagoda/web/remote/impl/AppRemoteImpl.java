package com.lushstar.pagoda.web.remote.impl;

import com.lushstar.pagoda.web.remote.AppRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lushstar.pagoda.web.vo.AppVo;
import com.lushstar.pagoda.web.vo.WebResponse;

import java.util.Date;
import java.util.List;

/**
 * <p>description : AppWebServiceImpl
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:07
 */
@Slf4j
@Service
public class AppRemoteImpl implements AppRemote {

    @Value("${pagoda.service.url}")
    private String routeUrl;

    private String prefix = "/service/app";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<AppVo> list() {
        ParameterizedTypeReference<WebResponse<List<AppVo>>> typeRef = new ParameterizedTypeReference<WebResponse<List<AppVo>>>() {
        };
        ResponseEntity<WebResponse<List<AppVo>>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/list",
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppVo add(AppVo appVo) {
        ParameterizedTypeReference<WebResponse<AppVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppVo>>() {
        };
        RestTemplate restTemplate = new RestTemplate();
        Date now = new Date();
        appVo.setCreateTime(now);
        appVo.setUpdateTime(now);
        appVo.setDel(false);
        ResponseEntity<WebResponse<AppVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/add",
                HttpMethod.POST, new HttpEntity<>(appVo), typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppVo find(Long id) {
        ParameterizedTypeReference<WebResponse<AppVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppVo>>() {
        };
        ResponseEntity<WebResponse<AppVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/find/" + id,
                HttpMethod.GET, null, typeRef);
        return responseEntity.getBody().getData();
    }

    @Override
    public AppVo update(AppVo appVo) {
        ParameterizedTypeReference<WebResponse<AppVo>> typeRef = new ParameterizedTypeReference<WebResponse<AppVo>>() {
        };
        appVo.setUpdateTime(new Date());
        ResponseEntity<WebResponse<AppVo>> responseEntity = restTemplate.exchange(routeUrl + prefix + "/update",
                HttpMethod.POST, new HttpEntity<>(appVo), typeRef);
        return responseEntity.getBody().getData();
    }

}
