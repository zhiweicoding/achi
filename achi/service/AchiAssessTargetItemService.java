package com.deta.achi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deta.achi.pojo.AchiAssessTargetItem;
import com.deta.gzwreport.entity.vo.QueryPageVO;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标业务接口
 * @date 2023-01-06 16:59
 */
public interface AchiAssessTargetItemService {

    // 根据 类别id 查询 列表
    IPage<AchiAssessTargetItem> queryAchiAssessTargetItemsByAchiAssessTargetId(QueryPageVO queryPageVO);

    // 根据 id 查询 单条记录回显
    AchiAssessTargetItem queryAchiAssessTargetItemById(String id);

    // 修改
    int updateAchiAssessTargetItemById(AchiAssessTargetItem achiAssessTargetItem);

    // 删除
}
