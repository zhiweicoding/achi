package com.deta.achi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coredata.utils.pool.ExecutorManager;
import com.coredata.utils.pool.ExecutorPool;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.pojo.AchiProcurement;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.service.AchiPlV2Service;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import com.deta.common.annotation.CnoocLogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.deta.app.constant.Constants.*;

/**
 * 绩效考核品类管理相关接口
 *
 * @author by diaozhiwei on 2023/01/10.
 * @email diaozhiwei2k@163.com
 **/
@RestController
@RequestMapping("/achiPl")
@Api(tags = "v2-绩效考核品类管理相关接口")
public class AchiPlV2Controller {
    private static final Logger logger = LoggerFactory.getLogger(AchiPlV2Controller.class);

    @Autowired
    private AchiPlV2Service achiPlService;

    @Autowired
    private AchiTaskServiceImpl achiTaskService;

    private ExecutorPool executorPool = ExecutorManager.getInstance().getPool("achi");

    /**
     * 品类管理查询
     *
     * @param achiPl
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "品类管理查询")
    @ApiOperation(value = "品类管理查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseMap query(@RequestBody AchiProcurement achiPl, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //用户账号
        String userId = headers.getFirst(HEADER_USERID);
        //查询品类数据
        Page<AchiProcurement> result = achiPlService.queryList(achiPl, userId);
        return responseMap.putResult(result);
    }

    /**
     * 品类管理新增
     *
     * @param achiPl
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "添加", operData = "品类管理新增")
    @ApiOperation(value = "品类管理新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMap add(@RequestBody AchiProcurement achiPl, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //用户名
        String userName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_USERNAME)), StandardCharsets.UTF_8);
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        //用户账号
        String userId = headers.getFirst(HEADER_USERID);
        //执行添加操作
        int result = achiPlService.add(achiPl, userName, domainName, departmentName, userId);
        // 重新计算积分
        executorPool.submit(() -> reCalcScore(achiPl));
        return responseMap.putResult(result);
    }

    /**
     * 品类管理修改
     *
     * @param achiPl
     * @return
     */
    @CnoocLogOperation(operType = "编辑", operData = "品类管理修改")
    @ApiOperation(value = "品类管理修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMap update(@RequestBody AchiProcurement achiPl) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //根据id查询品类明细
        AchiProcurement dbAchiPl = achiPlService.getById(achiPl.getId());
        //判断品类明细是否存在
        Assert.isTrue(achiPl != null, "品类管理明细不存在");
        //执行update方法
        boolean result = achiPlService.updateById(achiPl);
        // 重新计算积分
        executorPool.submit(() -> reCalcScore(dbAchiPl));
        return responseMap.putResult(result);
    }

    /**
     * @param ids
     * @return
     */
    @CnoocLogOperation(operType = "删除", operData = "品类管理删除")
    @ApiOperation(value = "品类管理删除")
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public ResponseMap delete(@RequestBody final List<String> ids) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        Integer result = achiPlService.delete(ids);
        List<AchiProcurement> achiPls = achiPlService.listByIds(ids);
        // 重新计算积分
        if (!CollectionUtils.isEmpty(achiPls)) {
            for (final AchiProcurement achiPl : achiPls) {
                executorPool.submit(() -> reCalcScore(achiPl));
            }
        }
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 品类相关左侧树查询
     *
     * @param achiTaskDTO
     * @param
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "品类相关左侧树查询")
    @ApiOperation(value = "品类相关左侧树查询")
    @RequestMapping(value = "/query/left", method = RequestMethod.POST)
    public ResponseMap queryLeft(@RequestBody AchiTaskDTO achiTaskDTO) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        List<AchiProcurement> result = achiPlService.queryLeft(achiTaskDTO);
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 品类相关详情查询
     *
     * @param achiPl
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "品类相关详情查询")
    @ApiOperation(value = "品类相关详情查询")
    @RequestMapping(value = "/query/detail", method = RequestMethod.POST)
    public ResponseMap queryDetail(@RequestBody AchiProcurement achiPl) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        AchiProcurement result = achiPlService.queryDetail(achiPl);
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 重新计算品类积分
     *
     * @param achiPl
     */
    private void reCalcScore(AchiProcurement achiPl) {
        try {
            // 重新计算积分
            LambdaQueryWrapper<AchiTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AchiTask::getWorkId, achiPl.getJbrCode()).
                    le(AchiTask::getAchiStart, achiPl.getCreateTime().getTime())
                    .ge(AchiTask::getAchiStop, achiPl.getCreateTime().getTime())
                    .eq(AchiTask::getStatus, 1)
                    .like(AchiTask::getTypeName, "品类采购");
            List<AchiTask> achiTasks = achiTaskService.getBaseMapper().selectList(queryWrapper);
            for (AchiTask achiTask : achiTasks) {
                String autoScore = achiTaskService.calcPinleicaigouSocre(achiPl.getJbrCode(),
                        new Date(achiTask.getAchiStart()), new Date(achiTask.getAchiStop()));
                logger.info("重新计算品类积分, jbrCode={}, createTime={}, autoScore={},",
                        achiPl.getJbrCode(), achiPl.getCreateTime(), autoScore);
                if (StringUtils.hasText(autoScore)) {
                    achiTask.setAutoScore(autoScore);
                    achiTaskService.updateTask(achiTask);
                }
            }
        } catch (Exception e) {
            logger.error("修改品类采购明细重新计算积分异常", e);
        }
    }

}


