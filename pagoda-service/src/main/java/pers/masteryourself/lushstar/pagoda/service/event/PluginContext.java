package pers.masteryourself.lushstar.pagoda.service.event;

import pers.masteryourself.lushstar.pagoda.service.response.DeferredResultWrapper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>description : PluginContext
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:57
 */
public class PluginContext {

    /**
     * hold request
     * key:instanceId-ip
     * value:DeferredResultWrapper
     */
    public static final Map<String, DeferredResultWrapper> HOLD_REQUEST_CONFIGS = new ConcurrentHashMap<>();

    /**
     * cache
     * key:instanceId
     * value:xxx.xxx.xxx.101, xxx.xxx.xxx.102, xxx.xxx.xxx.103
     */
    public static final Map<String, Set<String>> CACHE_CONFIGS = new ConcurrentHashMap<>();

    /**
     * key:instanceId
     * value:{
     * provider-parent.testMethod.00001:"return:999,用户已经完成付款",
     * provider-parent.testMethod.00002:"throw:{"className":"java.lang.NullPointerException"}"
     * }
     */
    public static final Map<String, Map<String, String>> MOCK_CONFIGS = new ConcurrentHashMap<>();

    public static Map<String, DeferredResultWrapper> getHoldRequestConfigs() {
        return HOLD_REQUEST_CONFIGS;
    }

    public static Map<String, Set<String>> getCacheConfigs() {
        return CACHE_CONFIGS;
    }

    public static Map<String, Map<String, String>> getMockConfigs() {
        return MOCK_CONFIGS;
    }

}
