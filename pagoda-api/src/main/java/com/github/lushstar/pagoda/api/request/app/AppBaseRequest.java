package com.github.lushstar.pagoda.api.request.app;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : AppBaseRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:30
 */
@Data
public class AppBaseRequest {

    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String name;

    /**
     * 描述信息
     */
    private String description;

}
