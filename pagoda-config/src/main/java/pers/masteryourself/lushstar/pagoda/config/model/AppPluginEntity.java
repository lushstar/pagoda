package pers.masteryourself.lushstar.pagoda.config.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>description : AppPlugin
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:45
 */
@Entity
@Table(name = "app_plugin")
@Where(clause = "del = 0")
@Data
public class AppPluginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "datetime COMMENT '修改时间'")
    private Date updateTime;

    @Column(columnDefinition = "bit(1) COMMENT '是否删除，0、false 表示未删除'")
    private boolean del;

    @Column(name = "app_id", columnDefinition = "bigint(20) COMMENT '应用 id'")
    private Long appId;

    @Column(name = "plugin_id", columnDefinition = "bigint(20) COMMENT '插件 id'")
    private Long pluginId;

    @Column(columnDefinition = "bit(1) COMMENT '是否激活，0、false 表示未激活, null 表示刚刚 install'")
    private boolean active;

}
