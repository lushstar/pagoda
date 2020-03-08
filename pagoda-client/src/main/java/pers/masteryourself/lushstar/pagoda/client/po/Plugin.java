package pers.masteryourself.lushstar.pagoda.client.po;

import lombok.Data;

import java.util.Date;

/**
 * <p>description : Plugin
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 22:10
 */
@Data
public class Plugin {

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
     * 是否激活
     */
    private Boolean active;

    /**
     * 插件类名
     */
    private String className;

    /**
     * 存放在客户端的磁盘位置
     */
    private String localAddress;

    /**
     * 事件源
     */
    private SourceType sourceType;

}
