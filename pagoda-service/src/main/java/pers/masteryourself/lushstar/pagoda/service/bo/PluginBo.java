package pers.masteryourself.lushstar.pagoda.service.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>description : PluginBo
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@Data
public class PluginBo {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 0 表示未删除
     */
    private boolean del;

    private String name;

    private String description;

    private String address;

    /**
     * 是否激活
     */
    private boolean active;

    /**
     * 插件类名
     */
    private String className;

    private SourceType sourceType;

}
