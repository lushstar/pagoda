package pers.masteryourself.lushstar.pagoda.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import pers.masteryourself.lushstar.pagoda.service.service.EventService;

/**
 * <p>description : ApplicationEventServiceImpl
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:45
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void sendEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

}
