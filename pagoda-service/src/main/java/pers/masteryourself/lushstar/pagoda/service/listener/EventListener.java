package pers.masteryourself.lushstar.pagoda.service.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import pers.masteryourself.lushstar.pagoda.service.event.PluginChangeEvent;
import pers.masteryourself.lushstar.pagoda.service.event.PluginChangeModel;

/**
 * <p>description : EventListener
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:49
 */
@Component
public class EventListener {

    @org.springframework.context.event.EventListener(classes = {PluginChangeEvent.class})
    public void mockRuleChangeEvent(ApplicationEvent event) {
        Object source = event.getSource();
        if (source instanceof PluginChangeModel) {
            PluginChangeModel pluginChangeModel = (PluginChangeModel) source;
            /*String appName = pluginChangeModel.getAppName();
            List<Long> configIds = pluginChangeModel.getMockConfigIds();
            if (StringUtils.isEmpty(appName) && CollectionUtils.isEmpty(configIds)) {
                return;
            }
            // if sync type = FULL, use appName
            if (pluginChangeModel.getType() == MockRuleEventModel.RuleType.FULL) {
                Set<String> appNames = new HashSet<>();
                // deal mockConfigIds to appName
                if (CollectionUtils.isNotEmpty(configIds)) {
                    for (Long configId : configIds) {
                        MockConfigDomain mockConfigDomain = mockConfigService.findById(configId);
                        if (mockConfigDomain == null || StringUtils.isEmpty(mockConfigDomain.getAppName())) {
                            continue;
                        }
                        appNames.add(mockConfigDomain.getAppName());
                    }
                }
                // deal appName
                if (StringUtils.isNotEmpty(appName)) {
                    appNames.add(appName);
                }
                // notify or cache configs
                for (String name : appNames) {
                    // update local cache, select from db to sync cache
                    MockRuleContext.MOCK_CONFIGS.put(name, this.configListToMap(mockConfigService.listByAppName(name)));
                    boolean needCache = true;
                    // notify client
                    for (String instanceIdAndIp : MockRuleContext.HOLD_REQUEST_CONFIGS.keySet()) {
                        if (instanceIdAndIp.startsWith(name)) {
                            needCache = false;
                            MockRuleContext.HOLD_REQUEST_CONFIGS.get(instanceIdAndIp).setResult(MockRuleContext.MOCK_CONFIGS.get(name));
                        }
                    }
                    // if no client wait, cache this update
                    if (needCache) {
                        // clear ip record
                        MockRuleContext.CACHE_CONFIGS.put(name, new HashSet<>());
                    }
                }
            }*/
        }
    }

}
