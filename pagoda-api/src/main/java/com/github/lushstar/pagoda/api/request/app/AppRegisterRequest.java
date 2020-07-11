package com.github.lushstar.pagoda.api.request.app;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : AppRegisterRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:46
 */
@Data
public class AppRegisterRequest {

    @NotBlank(message = "注册名称不能为空")
    private String name;

    @NotBlank(message = "instanceId 不能为空")
    private String instanceId;

}
