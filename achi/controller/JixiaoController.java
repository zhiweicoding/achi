package com.deta.achi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.coredata.utils.pool.ExecutorManager;
import com.coredata.utils.pool.ExecutorPool;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.JixiaoInfo;
import com.deta.achi.dto.JixiaoInfoDTO;
import com.deta.achi.service.JixiaoInfoService;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import com.deta.common.annotation.CnoocLogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DJWang
 * @date 2022/08/10 13:45
 **/
@RestController
@RequestMapping("/jixiao/info")
@Api(tags = "绩效考核招标业务相关接口")
public class JixiaoController {

    private ExecutorPool executorPool = ExecutorManager.getInstance().getPool("achi");

    private static final Logger logger = LoggerFactory.getLogger(JixiaoController.class);

    @Autowired
    private JixiaoInfoService jixiaoInfoService;

    @Autowired
    private AchiTaskServiceImpl achiTaskService;

    /**
     * @param achiTask
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "招标二级菜单查询")
    @ApiOperation(value = "二级菜单查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseMap query(@RequestBody AchiTask achiTask) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        List<JixiaoInfo> result = jixiaoInfoService.queryTwo(achiTask);
        responseMap.putResult(result);
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "招标根据标段编号查询")
    @ApiOperation(value = "根据标段编号查询")
    @RequestMapping(value = "/query/detail", method = RequestMethod.POST)
    public ResponseMap query(@RequestBody JixiaoInfo jixiaoInfo) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        JixiaoInfoDTO result = jixiaoInfoService.queryJixiao(jixiaoInfo);
        responseMap.setResult(result);
        return responseMap;
    }

    @CnoocLogOperation(operType = "编辑", operData = "招标根据标段编号修改")
    @ApiOperation(value = "根据标段编号修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMap update(@RequestBody JixiaoInfo jixiaoInfo) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //查询考核任务
        Integer result = jixiaoInfoService.updateDtail(jixiaoInfo);
        //重新计算分数
        executorPool.submit(() -> scoreAchi(jixiaoInfo));
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 个人基础明细列表查询
     *
     * @param achiTask
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "招标个人基础明细列表查询")
    @ApiOperation(value = "个人基础明细列表查询")
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ResponseMap queryList(@RequestBody AchiTaskDTO achiTask) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();

        Map<String, Object> jixiaoInfoList = jixiaoInfoService.queryList(achiTask);
        responseMap.putResult(jixiaoInfoList);
        return responseMap;
    }

    private void scoreAchi(JixiaoInfo jixiaoInfo) {
        QueryWrapper<AchiTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("work_id", jixiaoInfo.getJbrCode()).le("achi_start", jixiaoInfo.getProjectSlDate().getTime()).ge("achi_stop", jixiaoInfo.getProjectSlDate().getTime())
                .eq("achi_status", 1).like("type_name", "招标业务");
        List<AchiTask> as = achiTaskService.list(queryWrapper);
        if (as.size() > 0) {
            for (AchiTask a : as) {
                a.setAutoScore(achiTaskService.calcZhaobiaoyewuScore(jixiaoInfo.getJbrCode(), new Date(a.getAchiStart()), new Date(a.getAchiStop())));
                achiTaskService.updateTask(a);
            }
        }
    }
}


