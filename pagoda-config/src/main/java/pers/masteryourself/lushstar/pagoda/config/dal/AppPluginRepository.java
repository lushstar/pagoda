package pers.masteryourself.lushstar.pagoda.config.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.masteryourself.lushstar.pagoda.config.model.AppPluginEntity;

import java.util.List;

/**
 * <p>description : AppPluginRepository
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 15:11
 */
public interface AppPluginRepository extends JpaRepository<AppPluginEntity, Long> {

    List<AppPluginEntity> findByAppId(Long appId);

    AppPluginEntity findByAppIdAndPluginId(Long appId, Long pluginId);

}
