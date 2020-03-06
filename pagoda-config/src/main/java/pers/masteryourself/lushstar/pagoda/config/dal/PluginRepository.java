package pers.masteryourself.lushstar.pagoda.config.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;

/**
 * <p>description : PluginRepository
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:28
 */
public interface PluginRepository extends JpaRepository<PluginEntity, Long> {
}
