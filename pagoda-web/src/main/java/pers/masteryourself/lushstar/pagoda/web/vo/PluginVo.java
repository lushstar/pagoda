package pers.masteryourself.lushstar.pagoda.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>description : PluginVo
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@Data
public class PluginVo {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Boolean del;

    private String name;

    private String description;

    private String address;

    private String className;

    /**
     * 临时变量, 仅用于页面数据展示
     * true 表示已经激活
     */
    private boolean active = false;

    /**
     * 临时变量, 仅用于页面数据展示
     * true 表示已安装
     */
    private boolean install;
}
