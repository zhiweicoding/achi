package com.deta.achi.controller;

import com.coredata.utils.response.ResponseMap;
import com.deta.achi.pojo.vo.AchiAssessTargetItemVO;
import com.deta.achi.service.impl.AchiAssessTargetItemServiceImpl;
import com.deta.gzwreport.entity.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标管理接口
 * @date 2023-01-06 16:59
 */
@RestController
public class AchiAssessTargetItemController {

    @Autowired
    @Qualifier("achiAssessTargetItemServiceImpl")
    private AchiAssessTargetItemServiceImpl achiAssessTargetItemService;

    // 列表
    @PostMapping("/achiAssessTargetItem/queryAchiAssessTargetItemsByAchiAssessTargetId")
    public ResponseMap queryAchiAssessTargetItemsByAchiAssessTargetId(@RequestBody QueryPageVO queryPageVO){
        return achiAssessTargetItemService.queryAchiAssessTargetItemsByAchiAssessTargetIdRes(queryPageVO);
    }

    // 查询单条数据详情
    @GetMapping("/achiAssessTargetItem/queryAchiAssessTargetItemById/{id}")
    public ResponseMap queryAchiAssessTargetItemById(@PathVariable("id") String id){
        return achiAssessTargetItemService.queryAchiAssessTargetItemByIdRes(id);
    }

    // 修改
    @PutMapping("/achiAssessTargetItem/updateAchiAssessTargetItemById")
    public ResponseMap updateAchiAssessTargetItemById(@RequestBody AchiAssessTargetItemVO achiAssessTargetItemVO){
        return achiAssessTargetItemService.updateAchiAssessTargetItemByIdRes(achiAssessTargetItemVO);
    }

}