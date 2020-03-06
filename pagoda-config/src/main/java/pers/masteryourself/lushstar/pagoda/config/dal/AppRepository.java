package pers.masteryourself.lushstar.pagoda.config.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.masteryourself.lushstar.pagoda.config.model.AppEntity;

/**
 * <p>description : AppRepository
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:28
 */
public interface AppRepository extends JpaRepository<AppEntity, Long> {
}
