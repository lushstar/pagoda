package com.lushstar.pagoda.api.remote;

import com.lushstar.pagoda.api.dto.AppDto;
import com.lushstar.pagoda.api.response.ServiceResponse;
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
    ServiceResponse<List<AppDto>> list();

    @PostMapping(value = "add")
    ServiceResponse<AppDto> add(@RequestBody AppDto appBo);

    @GetMapping(value = "find/{id}")
    ServiceResponse<AppDto> find(@PathVariable(value = "id") Long id);

    @PostMapping(value = "update")
    ServiceResponse<AppDto> update(@RequestBody AppDto appBo);
}
