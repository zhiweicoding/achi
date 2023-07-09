package com.deta.achi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coredata.utils.pool.ExecutorManager;
import com.coredata.utils.pool.ExecutorPool;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dto.AchiContributionDTO;
import com.deta.achi.pojo.AchiContribution;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.service.AchiContributionService;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import com.deta.common.annotation.CnoocLogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.deta.app.constant.Constants.*;

/**
 * @author DJWang
 * @date 2022/08/17 17:52
 **/
@RestController
@RequestMapping("/achiCon")
@Api(tags = "绩效考核个人绩效贡献接口")
public class AchiContributionController {
    private static final Logger logger = LoggerFactory.getLogger(AchiContribution.class);
    @Autowired
    private AchiContributionService achiContributionService;

    @Autowired
    private AchiTaskServiceImpl achiTaskService;

    private ExecutorPool executorPool = ExecutorManager.getInstance().getPool("achi");

    /**
     * 个人绩效贡献修改
     *
     * @param achiContribution
     * @return
     */
    @CnoocLogOperation(operType = "编辑", operData = "个人绩效贡献修改")
    @ApiOperation(value = "个人绩效贡献修改")
    @RequestMapping(value = "/contr/update", method = RequestMethod.POST)
    public ResponseMap achiUpdate(@RequestBody AchiContribution achiContribution) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //根据id查询个人绩效贡献
        AchiContribution dbContribution = achiContributionService.getById(achiContribution.getId());
        //判断个人绩效贡献是否为空
        Assert.notNull(dbContribution, "个人绩效贡献不存在");
        //如果个人绩效贡献不为空修改
        Integer result = achiContributionService.contrUpdate(achiContribution);
        // 重新计算积分
        executorPool.submit(() -> reCalcScore(dbContribution));
        responseMap.putResult(result);
        return responseMap;
    }


    @CnoocLogOperation(operType = "查询", operData = "个人绩效贡献根据任务查询详情")
    @ApiOperation(value = "个人绩效贡献根据任务查询详情")
    @RequestMapping(value = "/task/query", method = RequestMethod.POST)
    public ResponseMap queryDetailCon(@RequestBody AchiTask achiTask) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //根据考核任务查询
        AchiContribution result = achiContributionService.queryDetail(achiTask);
        //返回结果
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 个人绩效贡献查询
     *
     * @param achiTaskId 考核任务id
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "个人绩效贡献详情查询")
    @ApiOperation(value = "个人绩效贡献详情查询")
    @RequestMapping(value = "/contr/query/{achiTaskId}", method = RequestMethod.GET)
    public ResponseMap achiQueryId(@PathVariable String achiTaskId) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            AchiTask achiTask = achiTaskService.getById(achiTaskId);
            if (achiTask != null) {
                List<AchiContribution> achiContributionList = achiContributionService.list(Wrappers.<AchiContribution>lambdaQuery()
                        .eq(AchiContribution::getWorkId, achiTask.getWorkId())
                        .like(AchiContribution::getConYear, format.format(new Date(achiTask.getAchiStart())))
                        .eq(AchiContribution::getStatus, 1));
                if (!CollectionUtils.isEmpty(achiContributionList)) {
                    AchiContribution result = achiContributionList.get(0);
                    responseMap.putResult(result);
                } else {
                    responseMap = ResponseMap.BadRequestInstance("未查询到个人贡献信息，考核任务id：" + achiTaskId);
                }
            } else {
                responseMap = ResponseMap.BadRequestInstance("考核任务不存在或已被删除，考核任务id：" + achiTaskId);
            }
        } catch (Exception e) {
            logger.error("search query error: ", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage(e.getMessage());
        }
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "个人绩效贡献列表查询")
    @ApiOperation(value = "个人绩效贡献列表查询")
    @RequestMapping(value = "/contr/query/list", method = RequestMethod.POST)
    public ResponseMap queryList(@RequestBody AchiContributionDTO achiContributionDTO, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        String userId = headers.getFirst(HEADER_USERID);
        if (Objects.nonNull(userId)) {
            achiContributionDTO.setWorkId(userId);
        }
        Page<AchiContribution> pageData = achiContributionService.queryList(achiContributionDTO);
        responseMap.putResult(pageData);
        return responseMap;
    }

    /**
     * 个人绩效贡献明细查询
     *
     * @param id 个人贡献id
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "个人绩效贡献详情查询")
    @ApiOperation(value = "个人绩效贡献详情查询")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseMap achiGetById(@PathVariable String id) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            AchiContribution achiContribution = achiContributionService.getById(id);
            responseMap.putResult(achiContribution);
        } catch (Exception e) {
            logger.error("search query error: ", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage(e.getMessage());
        }
        return responseMap;
    }

    /**
     * 个人绩效贡献新增
     *
     * @param achiContribution
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "添加", operData = "个人绩效贡献新增")
    @ApiOperation(value = "个人绩效贡献新增")
    @RequestMapping(value = "/add/Con", method = RequestMethod.POST)
    public ResponseMap addCon(@RequestBody AchiContribution achiContribution, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //用户名
        String userName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_USERNAME)), StandardCharsets.UTF_8);
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        //用户账号
        String userId = headers.getFirst(HEADER_USERID);
        //添加个人绩效贡献
        Integer result = achiContributionService.addCon(achiContribution, userName, domainName, departmentName, userId);
        if (result == 0) {
            responseMap.setHttpCode(400);
        }
        // 重新计算积分
        executorPool.submit(() -> reCalcScore(achiContribution));
        responseMap.putResult(result);
        return responseMap;
    }


    @CnoocLogOperation(operType = "删除", operData = "个人绩效贡献删除")
    @ApiOperation(value = "个人绩效贡献删除")
    @RequestMapping(value = "/con/delete", method = RequestMethod.POST)
    public ResponseMap conDelete(@RequestBody AchiContribution achiContribution) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //根据id查询个人绩效贡献
        AchiContribution dbContribution = achiContributionService.getById(achiContribution.getId());
        //判断个人绩效贡献是否存在
        Assert.notNull(dbContribution, "个人绩效贡献不存在！");
        //如果存在更新个人绩效贡献状态
        Integer result = achiContributionService.updateCon(achiContribution);
        // 重新计算积分
        if (dbContribution != null) {
            executorPool.submit(() -> reCalcScore(dbContribution));
        }
        responseMap.putResult(result);
        return responseMap;
    }

    @CnoocLogOperation(operType = "删除", operData = "批量个人绩效贡献删除")
    @ApiOperation(value = "批量个人绩效贡献删除")
    @RequestMapping(value = "/con/batchdelete", method = RequestMethod.POST)
    public ResponseMap conBatchDelete(@RequestBody List<String> ids) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        logger.info("achiContribution---------ids---{}", ids);
        //根据ids查询
        List<AchiContribution> list = achiContributionService.listByIds(ids);
        //批量更新状态
        Integer result = achiContributionService.batchUpdateCon(ids);
        // 重新计算积分
        if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
            for (final AchiContribution contribution : list) {
                executorPool.submit(() -> reCalcScore(contribution));
            }
        }
        responseMap.putResult(result);
        return responseMap;
    }

    private void reCalcScore(AchiContribution achiContribution) {
        try {
            // 重新计算积分
            QueryWrapper<AchiTask> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("work_id", achiContribution.getWorkId()).
                    le("achi_start", achiContribution.getCreateTime())
                    .ge("achi_stop", achiContribution.getCreateTime())
                    .eq("status", 1)
                    .eq("achi_status", 1)
                    .like("type_name", achiContribution.getTypeName());
            List<AchiTask> as = achiTaskService.list(queryWrapper);
            String autoScore = "";
            for (AchiTask achiTask : as) {
                if ("招标业务".equalsIgnoreCase(achiContribution.getTypeName())) {
                    autoScore = achiTaskService.calcZhaobiaoyewuScore(achiContribution.getWorkId(),
                            new Date(achiTask.getAchiStart()), new Date(achiTask.getAchiStop()));
                } else if ("品类采购".equalsIgnoreCase(achiContribution.getTypeName())) {
                    autoScore = achiTaskService.calcPinleicaigouSocre(achiContribution.getWorkId(),
                            new Date(achiTask.getAchiStart()), new Date(achiTask.getAchiStop()));
                } else if ("外部市场".equalsIgnoreCase(achiContribution.getTypeName())) {
                    autoScore = achiTaskService.calcWaibushichangSocre(achiContribution.getWorkId(),
                            new Date(achiTask.getAchiStart()), new Date(achiTask.getAchiStop()));
                }
                logger.info("绩效贡献变化重新计算积分, tbrCode={}, createTime={}, autoScore={},",
                        achiContribution.getWorkId(), achiContribution.getCreateTime(), autoScore);
                if (StringUtils.hasText(autoScore)) {
                    achiTask.setAutoScore(autoScore);
                    achiTaskService.updateTask(achiTask);
                }
            }

        } catch (Exception e) {
            logger.error("修改个人贡献明细重新计算积分异常", e);
        }
    }

}


