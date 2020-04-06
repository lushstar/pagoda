package com.lushstar.pagoda.dal.repository;

import com.lushstar.pagoda.dal.model.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>description : AppRepository
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:28
 */
public interface AppRepository extends JpaRepository<AppEntity, Long> {

    AppEntity findByName(String appName);

}
