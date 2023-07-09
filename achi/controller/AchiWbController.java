package com.deta.achi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.coredata.utils.elasticsearch.ElasticsearchService;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dto.AchiWbDTO;
import com.deta.achi.exception.ResultEnum;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.AchiWb;
import com.deta.achi.service.AchiWbService;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import com.deta.achi.utils.AchiEnum;
import com.deta.common.annotation.CnoocLogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.deta.app.constant.Constants.*;

/**
 * @author DJWang
 * @date 2022/08/16 22:10
 **/
@RestController
@RequestMapping("/achiWb")
@Api(tags = "绩效考核外部市场相关接口")
public class AchiWbController {

    private static final Logger logger = LoggerFactory.getLogger(AchiWb.class);
    @Autowired
    private AchiWbService achiWbService;

    @Autowired
    private AchiTaskServiceImpl achiTaskService;


    /**
     * 外部市场新增
     *
     * @param achiWb
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "添加", operData = "外部市场新增")
    @ApiOperation(value = "外部市场新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMap query(@RequestBody AchiWb achiWb, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        String userName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_USERNAME)), StandardCharsets.UTF_8);
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        String userId = headers.getFirst("userId");
        Integer result = achiWbService.add(achiWb, userName, domainName, departmentName, userId);
        scoreHelp(achiWb);
        if (result.intValue() == AchiEnum.ZERO.code()) {
            responseMap.putResult(ResultEnum.SUCCESS_MESSAGE.message());
        } else {
            responseMap.putResult(ResultEnum.ERROR_MESSAGE.message());
        }
        return responseMap.putResult(result);
    }

    /**
     * 外部市场删除
     *
     * @param ids
     * @return
     */
    @CnoocLogOperation(operType = "删除", operData = "外部市场删除")
    @ApiOperation(value = "外部市场删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMap delete(@RequestBody final List<String> ids) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        achiWbService.delete(ids);
        List<AchiWb> achiWbs = achiWbService.listByIds(ids);
        //重新计算分数
        if (achiWbs.size() > 0) {
            for (AchiWb achiWb : achiWbs) {
                scoreHelp(achiWb);
            }
        }
        return responseMap;
    }

    /**
     * 外部市场查询详情
     *
     * @param id
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "外部市场查询详情")
    @ApiOperation(value = "外部市场查询详情")
    @RequestMapping(value = "/query/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseMap queryDetail(@PathVariable final String id) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //查询详情
        AchiWb result = achiWbService.selectDetail(id);
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 外部市场修改
     *
     * @param achiWb
     * @return
     */
    @CnoocLogOperation(operType = "编辑", operData = "外部市场修改")
    @Transactional(rollbackFor = {Exception.class})
    @ApiOperation(value = "外部市场修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMap update(@RequestBody AchiWb achiWb) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        boolean result = achiWbService.updateById(achiWb);
        scoreHelp(achiWb);
        return responseMap.putResult(result);
    }

    @CnoocLogOperation(operType = "查询", operData = "外部市场明细二级菜单")
    @ApiOperation(value = "外部市场明细二级菜单")
    @RequestMapping(value = "/find/menu", method = RequestMethod.POST)
    public ResponseMap findMenu(@RequestBody AchiTask achiTask) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        List<AchiWb> achiWbList = achiWbService.selectMenu(achiTask);
        responseMap.putResult(achiWbList);
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "外部市场明细列表条件查询")
    @ApiOperation(value = "外部市场明细列表条件查询")
    @RequestMapping(value = "/find/list", method = RequestMethod.POST)
    public ResponseMap findList(@RequestBody AchiWbDTO achiWbDTO, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        String userId = headers.getFirst(HEADER_USERID);
        Map<String, Object> achiWbList = achiWbService.findList(achiWbDTO, userId);
        responseMap.putResult(achiWbList);
        return responseMap;
    }

    private void scoreHelp(AchiWb achiWb) {
        LambdaQueryWrapper<AchiTask> wrapper = Wrappers.<AchiTask>lambdaQuery();
        wrapper.eq(AchiTask::getWorkId, achiWb.getJbrCode())
                .le(AchiTask::getAchiStart, achiWb.getCreateTime())
                .ge(AchiTask::getAchiStop, achiWb.getCreateTime())
                .like(AchiTask::getTypeName, "外部市场")
                .eq(AchiTask::getAchiStatus, 1);
        List<AchiTask> as = achiTaskService.list(wrapper);
        if (as.size() > 0) {
            for (AchiTask a : as) {
                a.setAutoScore(achiTaskService.calcWaibushichangSocre(achiWb.getJbrCode(), new Date(a.getAchiStart()), new Date(a.getAchiStop())));
                achiTaskService.updateTask(a);
            }
        }
    }


}


