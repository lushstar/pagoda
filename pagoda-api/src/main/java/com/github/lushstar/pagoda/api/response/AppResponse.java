package com.github.lushstar.pagoda.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>description : AppResponse
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@Data
public class AppResponse {

    private Long id;

    /**
     * {@link JsonFormat} 用于序列化成 json 数据展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * {@link JsonFormat} 用于序列化成 json 数据展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 0 表示未删除
     */
    private Boolean del;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;

}
