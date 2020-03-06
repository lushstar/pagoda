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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0 表示未删除
     */
    @Column
    private boolean del;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String address;

    /**
     * 状态
     */
    @Column
    private boolean status;

    /**
     * 插件类名
     */
    @Column(name = "class_name")
    private String className;

}
