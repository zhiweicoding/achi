package com.deta.achi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dto.AchiTemplateDTO;
import com.deta.achi.pojo.AchiFeature;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.AchiTemplate;
import com.deta.achi.service.AchiFeatureService;
import com.deta.achi.service.AchiTaskService;
import com.deta.achi.service.AchiTemplateService;
import com.deta.common.annotation.CnoocLogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.deta.app.constant.Constants.*;

/**
 * @author DJWang
 * @date 2022/08/08 23:31
 **/
@RestController
@RequestMapping("/achifeature")
@Api(tags = "绩效考核任务创建配置")
public class AchiFeatureController {

    private static final Logger logger = LoggerFactory.getLogger(AchiFeatureController.class);
    @Autowired
    private AchiFeatureService achiFeatureService;
    @Autowired
    private AchiTemplateService achiTemplateService;
    @Autowired
    private AchiTaskService achiTaskService;

    /**
     * 配置项查询
     *
     * @param achiFeature
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "配置项查询")
    @ApiOperation(value = "配置项查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseMap query(@RequestBody AchiFeature achiFeature) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        List<AchiFeature> list = achiFeatureService.select(achiFeature);
        responseMap.putResult(list);
        return responseMap;
    }

    /**
     * 配置项新增
     *
     * @param achiFeature
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "添加", operData = "配置项新增")
    @ApiOperation(value = "配置项新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMap add(@RequestBody AchiFeature achiFeature, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        String userName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_USERNAME)), StandardCharsets.UTF_8);
        Integer result = achiFeatureService.add(achiFeature, userName);
        return responseMap.putResult(result);
    }


    /**
     * 配置项修改
     *
     * @param achiFeature
     * @return
     */
    @CnoocLogOperation(operType = "编辑", operData = "配置项修改")
    @ApiOperation(value = "配置项修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMap update(@RequestBody AchiFeature achiFeature) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        Integer result = achiFeatureService.update(achiFeature);
        return responseMap.putResult(result);
    }

    /**
     * 配置项删除
     *
     * @param achiFeature
     * @return
     */
    @CnoocLogOperation(operType = "删除", operData = "配置项删除")
    @ApiOperation(value = "配置项删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMap deleteAchiFeature(@RequestBody AchiFeature achiFeature) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        Integer result = achiFeatureService.deleteAchiFeature(achiFeature);
        responseMap.putResult(result);
        return responseMap;
    }

    /**
     * 任务新增
     *
     * @param achiTemplateDTO
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "添加", operData = "考核任务模版添加")
    @ApiOperation(value = "考核任务模版添加")
    @RequestMapping(value = "/work/add", method = RequestMethod.POST)
    public ResponseMap achiAdd(@RequestBody AchiTemplateDTO achiTemplateDTO, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //用户名
        String userName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_USERNAME)), StandardCharsets.UTF_8);
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        //设置所属单位
        achiTemplateDTO.setCompany(domainName);
        //设置所属部门
        achiTemplateDTO.setDepartment(departmentName);
        boolean result = achiTemplateService.add(achiTemplateDTO, userName);
        return responseMap.putResult(result);
    }


    /**
     * 任务删除
     *
     * @param ids
     * @return
     */
    @CnoocLogOperation(operType = "删除", operData = "考核任务模版删除")
    @ApiOperation(value = "任务删除")
    @RequestMapping(value = "/work/delete", method = RequestMethod.POST)
    public ResponseMap achiDelete(@RequestBody final List<String> ids) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        Integer result = achiTemplateService.delete(ids);
        return responseMap.putResult(result);
    }

    /**
     * @param achiTemplateDTO
     * @return
     */
    @CnoocLogOperation(operType = "编辑", operData = "考核任务模版编辑")
    @ApiOperation(value = "任务修改")
    @RequestMapping(value = "/work/update", method = RequestMethod.POST)
    public ResponseMap achiUpdate(@RequestBody AchiTemplateDTO achiTemplateDTO, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        //设置所属单位
        achiTemplateDTO.setCompany(domainName);
        //设置所属部门
        achiTemplateDTO.setDepartment(departmentName);
        //更新考核任务
        achiTemplateService.achiUpdate(achiTemplateDTO);
        if (achiTemplateDTO.getAchiStatus() != null && achiTemplateDTO.getAchiStatus() == 3) {
            // 考核任务结束后，其下的任务全部状态置为结束
            List<AchiTask> achiTasks = achiTaskService.selectByAchiId(achiTemplateDTO.getAchiId());
            if (!CollectionUtils.isEmpty(achiTasks)) {
                for (AchiTask achiTask : achiTasks) {
                    AchiTask task = new AchiTask();
                    task.setId(achiTask.getId());
                    task.setAchiStatus(3);
                    task.setManagerStatus(5);
                    task.setDirectorStatus(5);
                    achiTaskService.updateById(task);
                }
            }
        }
        return responseMap;
    }

    /**
     * 考核任务详情查询
     */
    @CnoocLogOperation(operType = "查询", operData = "考核任务模版详情")
    @ApiOperation(value = "考核任务模版详情")
    @RequestMapping(value = "/template/detail", method = RequestMethod.POST)
    public ResponseMap templateDetail(@RequestBody AchiTemplate achiTemplate) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        AchiTemplateDTO detail = achiTemplateService.templateDetail(achiTemplate);
        responseMap.putResult(detail);
        return responseMap;
    }


    /**
     * 考核模版查询
     *
     * @param achiTemplate
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "考核任务模版查询")
    @ApiOperation(value = "考核任务模版查询")
    @RequestMapping(value = "/work/query/like", method = RequestMethod.POST)
    public ResponseMap achiQueryLike(@RequestBody AchiTemplate achiTemplate) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //判断是否是一级菜单的任务查询
        if (StringUtils.isEmpty(achiTemplate.getAchiId())) {
            Page<AchiTemplate> templateList = achiTemplateService.queryLike(achiTemplate);
            responseMap.putResult(templateList);
        } else {
            //一级菜单任务查询方法
            Page<AchiTemplate> template = achiFeatureService.queryTemplates(achiTemplate);
            responseMap.putResult(template);
        }
        return responseMap;
    }

    @CnoocLogOperation(operType = "复制", operData = "复制考核任务模板")
    @ApiOperation(value = "复制考核任务模板")
    @RequestMapping(value = "/achitemplate/copy/{achiId}", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseMap achitemplatecopy(@PathVariable String achiId, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            String userName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_USERNAME)), StandardCharsets.UTF_8);
            AchiTemplate achiTemplate = achiTemplateService.getById(achiId);
            if (achiTemplate == null) {
                responseMap = ResponseMap.BadRequestInstance("复制的考核任务不存在，achiId：" + achiId);
            } else {
                AchiTemplate target = new AchiTemplate();
                BeanUtils.copyProperties(achiTemplate, target);
                target.setAchiId(null);
                target.setAchiName(achiTemplate.getAchiName() + "副本");
                target.setCreateUser(userName);
                long ts = System.currentTimeMillis();
                target.setCreateTime(ts);
                target.setUpdateTime(ts);
                target.setUpdateUser(userName);
                target.setStatus(1);
                target.setAchiStatus(1);
                achiTemplateService.save(target);
                List<AchiTask> achiTasks = achiTaskService.selectByAchiId(achiId);
                if (!CollectionUtils.isEmpty(achiTasks)) {
                    for (AchiTask achiTask : achiTasks) {
                        achiTask.setId(null);
                        achiTask.setAchiId(target.getAchiId());
                        achiTask.setAchiName(target.getAchiName());
                    }
                    achiTaskService.saveBatch(achiTasks);
                }
            }
        } catch (Exception e) {
            logger.error("复制考核任务异常", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage("复制考核任务异常：" + e.getMessage());
        }
        return responseMap;
    }
}


