package com.github.lushstar.pagoda.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : PluginRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/26 12:22
 */
@Data
public class WebPluginRequest {

    private Long id;

    private Boolean del;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String address;

    @NotBlank
    private String className;

}
