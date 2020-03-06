package pers.masteryourself.lushstar.pagoda.service.service;

import pers.masteryourself.lushstar.pagoda.config.model.PluginEntity;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;

import java.util.List;

/**
 * <p>description : PluginService
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
public interface PluginService {

    List<PluginBo> list();

    PluginBo findById(Long id);

    PluginBo save(PluginEntity pluginEntity);
}
