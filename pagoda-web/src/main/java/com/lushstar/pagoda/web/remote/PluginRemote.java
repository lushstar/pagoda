package com.lushstar.pagoda.web.remote;

import com.lushstar.pagoda.web.vo.PluginVo;

import java.util.List;

/**
 * <p>description : PluginRemote
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:07
 */
public interface PluginRemote {

    List<PluginVo> list();

    PluginVo add(PluginVo pluginVo);

    PluginVo find(Long id);

    PluginVo update(PluginVo pluginVo);

}
