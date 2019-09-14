package com.tutu.db;

import com.tutu2.service.BaseService;
import com.tutu2.vo.ActivityVO;

import java.util.List;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/23 20:10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface ActivityService extends BaseService<ActivityDO>{

    List<ActivityVO> list(Long userId);

    int addShareRecord(ShareRecord shareRecord);

    ActivityDO selectOneById(Long activityId);

}
