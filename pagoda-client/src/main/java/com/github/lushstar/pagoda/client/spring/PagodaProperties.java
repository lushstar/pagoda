package com.github.lushstar.pagoda.client.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>description : PagodaProperties
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 12:47
 */
@Data
@ConfigurationProperties(prefix = "pagoda")
public class PagodaProperties {
    
    private String appName;
    
    private String serviceUrl;
    
    private String instanceId;
    
}
