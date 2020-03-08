package pers.masteryourself.lushstar.pagoda.config.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>description : AppEntity
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:28
 */
@Entity
@Table(name = "app")
@Where(clause = "del = 0")
@Data
public class AppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "datetime COMMENT '修改时间'")
    private Date updateTime;

    @Column(columnDefinition = "bit(1) COMMENT '是否删除，0、false 表示未删除'")
    private boolean del;

    @Column(unique = true, columnDefinition = "varchar(100) COMMENT '应用名称'")
    private String name;

    @Column(columnDefinition = "varchar(5000) COMMENT '应用描述'")
    private String description;

}
