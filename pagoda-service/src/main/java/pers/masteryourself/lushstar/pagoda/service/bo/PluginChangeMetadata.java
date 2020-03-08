package pers.masteryourself.lushstar.pagoda.service.bo;

import lombok.Data;

/**
 * <p>description : PluginChangeMetadata
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 16:40
 */
@Data
public class PluginChangeMetadata {

    private Long id;

    private String appName;

    private String address;

    private String className;

    private SourceType sourceType;

    /**
     * 0、false 表示禁用
     */
    private boolean active;

}
