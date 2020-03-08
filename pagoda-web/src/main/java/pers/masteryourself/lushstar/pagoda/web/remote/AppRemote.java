package pers.masteryourself.lushstar.pagoda.web.remote;

import pers.masteryourself.lushstar.pagoda.web.vo.AppVo;

import java.util.List;

/**
 * <p>description : AppRemote
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 14:06
 */
public interface AppRemote {

    List<AppVo> list();

    AppVo add(AppVo appVo);

    AppVo find(Long id);

    AppVo update(AppVo appVo);
}
