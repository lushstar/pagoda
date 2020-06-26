package com.github.lushstar.pagoda.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : PluginRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/24 14:22
 */
@Data
public class PluginRequest {

    private Long id;

    /**
     * 0 表示未删除
     */
    private Boolean del;

    @NotBlank(message = "插件名称不能为空")
    private String name;

    private String description;

    private String address;

    /**
     * 插件类名
     */
    @NotBlank(message = "插件类名不能为空")
    private String className;

}