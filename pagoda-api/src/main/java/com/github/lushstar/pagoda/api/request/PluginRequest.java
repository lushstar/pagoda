package com.github.lushstar.pagoda.api.request;

import lombok.Data;

import java.util.Date;

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

    private Date createTime;

    private Date updateTime;

    /**
     * 0 表示未删除
     */
    private Boolean del;

    private String name;

    private String description;

    private String address;

    /**
     * 插件类名
     */
    private String className;

}
