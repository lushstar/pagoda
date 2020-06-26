package com.github.lushstar.pagoda.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>description : PluginBo
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@Data
public class PluginResponse {

    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 0 表示未删除
     */
    private Boolean del;

    /**
     * 插件名称
     */
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
    private String className;

    /**
     * 临时变量, 需要业务自己判断
     * true 表示已经激活
     */
    private boolean active = false;

    /**
     * 临时变量, 需要业务自己判断
     * true 表示已安装
     */
    private boolean install;

}
