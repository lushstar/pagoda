package pers.masteryourself.lushstar.pagoda.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>description : AppVo
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppVo {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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
