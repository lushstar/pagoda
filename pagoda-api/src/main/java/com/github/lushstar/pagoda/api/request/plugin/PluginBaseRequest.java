package com.github.lushstar.pagoda.api.request.plugin;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : PluginBaseRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:40
 */
@Data
public class PluginBaseRequest {

    /**
     * 插件名称
     */
    @NotBlank(message = "插件名称不能为空")
    private String name;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 插件地址
     */
    private String address;

    /**
     * 插件类名
     */
    @NotBlank(message = "插件类名不能为空")
    private String className;

}
