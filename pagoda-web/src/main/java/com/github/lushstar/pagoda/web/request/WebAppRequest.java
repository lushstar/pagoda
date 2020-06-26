package com.github.lushstar.pagoda.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : WebAppRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/26 15:13
 */
@Data
public class WebAppRequest {

    private Long id;

    /**
     * 0 表示未删除
     */
    private Boolean del;

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
