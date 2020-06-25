package com.github.lushstar.pagoda.api.remote;

import com.github.lushstar.pagoda.api.request.AppRequest;
import com.github.lushstar.pagoda.api.response.AppResponse;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>description : AppRemote
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:06
 */
public interface AppRemote {

    @GetMapping(value = "list")
    ServiceResponse<List<AppResponse>> list();

    @PostMapping(value = "add")
    ServiceResponse<AppResponse> add(@RequestBody AppRequest appRequest);

    @GetMapping(value = "find/{id}")
    ServiceResponse<AppResponse> find(@PathVariable(value = "id") Long id);

    @PostMapping(value = "update")
    ServiceResponse<AppResponse> update(@RequestBody AppRequest appRequest);
}
