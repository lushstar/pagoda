package pers.masteryourself.lushstar.pagoda.config.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>description : PluginEntity
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:28
 */
@Entity
@Table(name = "plugin")
@Where(clause = "del = 0")
@Data
public class PluginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "datetime COMMENT '修改时间'")
    private Date updateTime;

    /**
     * 0 表示未删除
     */
    @Column(columnDefinition = "bit(1) COMMENT '是否删除，0、false 表示未删除'")
    private boolean del;

    /**
     * 插件名称
     */
    @Column(unique = true, columnDefinition = "varchar(100) COMMENT '插件名称'")
    private String name;

    /**
     * 插件描述
     */
    @Column(columnDefinition = "varchar(5000) COMMENT '插件描述'")
    private String description;

    /**
     * 插件地址
     */
    @Column(columnDefinition = "varchar(5000) COMMENT '插件地址'")
    private String address;

    /**
     * 是否激活
     */
    @Column(columnDefinition = "bit(1) COMMENT '是否激活，0、false 表示未激活'")
    private boolean active;

    /**
     * 插件类名
     */
    @Column(name = "class_name", unique = true, columnDefinition = "varchar(100) COMMENT '插件类名'")
    private String className;

}
