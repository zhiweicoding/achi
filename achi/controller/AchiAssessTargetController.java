package com.deta.achi.controller;

import com.coredata.utils.response.ResponseMap;
import com.deta.achi.pojo.vo.AchiAssessTargetVO;
import com.deta.achi.service.impl.AchiAssessTargetServiceImpl;
import com.deta.common.annotation.CnoocLogOperation;
import com.deta.gzwreport.entity.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标接口
 * @date 2023-01-05 19:40
 */
@RestController
public class AchiAssessTargetController {

    @Autowired
    @Qualifier("achiAssessTargetService")
    private AchiAssessTargetServiceImpl achiAssessTargetService;


    @GetMapping("/achiAssessTarget/queryTypeList/{id}")
    public ResponseMap queryTypeList(@PathVariable("id") String id){
        return achiAssessTargetService.queryTypeListRes(id);
    }

    @GetMapping("/achiAssessTarget/getParentIdAndNamesByParentId/{id}")
    public String getParentIdAndNamesByParentId(@PathVariable("id") String id){
        return achiAssessTargetService.getParentIdAndNames(id);
    }

    @GetMapping("/achiAssessTarget/queryById/{id}")
    public ResponseMap queryById(@PathVariable("id") String id){
        return achiAssessTargetService.queryByIdRes(id);
    }


    @GetMapping("/achiAssessTarget/queryList")
    public ResponseMap queryList(){
        return achiAssessTargetService.queryListRes();
    }


    @GetMapping("/achiAssessTarget/queryAll")
    public ResponseMap queryAll(){
        return achiAssessTargetService.queryAllRes();
    }

    @PostMapping("/achiAssessTarget/insertAssessTarget")
    @CnoocLogOperation(operType = "新增", operData = "绩效考核指标一级分类添加")
    public ResponseMap insertAssessTarget(@RequestBody AchiAssessTargetVO achiAssessTargetVO){
        return achiAssessTargetService.insertAssessTargetRes(achiAssessTargetVO);
    }

    @PutMapping("/achiAssessTarget/updateAssessTarget")
    public ResponseMap updateAssessTarget(@RequestBody AchiAssessTargetVO achiAssessTargetVO){
        return achiAssessTargetService.updateAssessTargetRes(achiAssessTargetVO);
    }


    @PostMapping("/achiAssessTarget/removeAssessTarget")
    @CnoocLogOperation(operType = "删除", operData = "绩效考核指标一级分类删除")
    public ResponseMap removeAssessTarget(@RequestBody QueryPageVO queryPageVO){
        return achiAssessTargetService.removeAssessTargetRes(queryPageVO);
    }
}
