package com.github.lushstar.pagoda.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>description : AppPluginResponse
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:08
 */
@Data
public class AppPluginResponse {

    private Long id;

    /**
     * {@link JsonFormat} 用于序列化成 json 数据展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * {@link JsonFormat} 用于序列化成 json 数据展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private boolean del;

    private Long appId;

    private Long pluginId;

    private boolean active;

}
