package com.deta.achi.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coredata.utils.file.FileExtCheck;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.pojo.AchiScore;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.excel.ExcelUtils;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import com.deta.common.annotation.CnoocLogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.deta.app.constant.Constants.*;


/**
 * @author DJWang
 * @date 2022/08/09 20:59
 **/
@RestController
@RequestMapping("/achiTask")
@Api(tags = "绩效考核task任务相关接口")
public class AchiTaskController {
    private static final Logger logger = LoggerFactory.getLogger(AchiTaskController.class);
    @Autowired
    private AchiTaskServiceImpl achiTaskService;

    /**
     * 考核任务查询
     *
     * @param achiTask
     * @param headers
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "考核任务查询")
    @ApiOperation(value = "考核任务查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseMap query(@RequestBody AchiTaskDTO achiTask, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //查询账号
        String userId = headers.getFirst(HEADER_USERID);
        //查询数据
        Page<AchiTask> select = achiTaskService.select(achiTask, userId);
        responseMap.putResult(select);
        return responseMap;
    }

    /**
     * 考核汇总查询
     *
     * @param achiTask
     * @return
     */
    @CnoocLogOperation(operType = "查询", operData = "考核汇总查询")
    @ApiOperation(value = "考核汇总查询")
    @RequestMapping(value = "/task/query", method = RequestMethod.POST)
    public ResponseMap queryTask(@RequestBody AchiTaskDTO achiTask, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        //设置所属单位
        achiTask.setCompany(domainName);
        //设置所属部门
        achiTask.setDepartment(departmentName);
        achiTask.setManagerStatus(2);
        Page<AchiTask> pageData = achiTaskService.selectTask(achiTask);
        responseMap.putResult(pageData);
        return responseMap;
    }

    /**
     *
     */
    @CnoocLogOperation(operType = "编辑", operData = "个人考核信息修改")
    @ApiOperation(value = "个人考核信息修改")
    @RequestMapping(value = "/task/update", method = RequestMethod.POST)
    public ResponseMap update(@RequestBody AchiTask achiTask) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            achiTaskService.updateTask(achiTask);
            // 提交和结束状态需要同步修改明细和个人贡献的状态
        } catch (Exception e) {
            logger.error("search update error: ", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage(e.getMessage());
        }
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "处室主任考核汇总查询")
    @ApiOperation(value = "处室主任考核汇总查询")
    @RequestMapping(value = "/director/query", method = RequestMethod.POST)
    public ResponseMap queryDirector(@RequestBody AchiTaskDTO achiTask, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            //单位
            String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
            //部门
            String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
            //设置所属单位
            achiTask.setCompany(domainName);
            //设置所属部门
            achiTask.setDepartment(departmentName);
            achiTask.setDirectorStatus(2);
            Page<AchiTask> pageData = achiTaskService.selectTask(achiTask);
            responseMap.putResult(pageData);
        } catch (Exception e) {
            logger.error("search director error: ", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage(e.getMessage());
        }
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "考核任务详情查询")
    @ApiOperation(value = "考核任务详情查询")
    @RequestMapping(value = "/detail/query", method = RequestMethod.POST)
    public ResponseMap queryDetail(@RequestBody AchiTask achiTask) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            AchiTask dtail = achiTaskService.selectDtail(achiTask);
            responseMap.putResult(dtail);
        } catch (Exception e) {
            logger.error("search director error: ", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage(e.getMessage());
        }
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "hr任务汇总查询")
    @ApiOperation(value = "hr任务汇总查询")
    @RequestMapping(value = "/hr/query", method = RequestMethod.POST)
    public ResponseMap queryHr(@RequestBody AchiTaskDTO achiTask, @RequestHeader(required = false) HttpHeaders headers) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //部门
        String departmentName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_DOMAINNAME)), StandardCharsets.UTF_8);
        //设置所属单位
        achiTask.setCompany(domainName);
        //设置所属部门
        achiTask.setDepartment(departmentName);
        achiTask.setAchiStatus(3);
        Page<AchiTask> pageData = achiTaskService.selectTask(achiTask);
        responseMap.putResult(pageData);

        return responseMap;
    }

    @CnoocLogOperation(operType = "导入", operData = "导入数据")
    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/task/file", method = RequestMethod.POST)
    public ResponseMap file(@RequestParam("file") MultipartFile file, @RequestHeader(required = false) HttpHeaders headers) throws IOException {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        //单位
        String domainName = URLDecoder.decode(Objects.requireNonNull(headers.getFirst(HEADER_FULLDOMAINNAME)), StandardCharsets.UTF_8);
        //读取文件
        InputStream inputStream = file.getInputStream();
        //判断是否格式正确
        boolean valid = FileExtCheck.isValid(file, "xlsx", "xls");
        if (!valid) {
            responseMap = ResponseMap.BadRequestInstance();
            responseMap.setMessage("请上传Excel文件");
        } else {
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<Map<String, Object>> list = reader.readAll();
            if (list.isEmpty()) {
                responseMap.setMessage("数据为空");
                responseMap.setHttpCode(400);
            } else {
                boolean uploadFile = achiTaskService.uploadFile(file, domainName);
                responseMap.putResult(uploadFile);
            }
        }
        return responseMap;
    }

    @CnoocLogOperation(operType = "下载", operData = "下载绩效导入模版")
    @ApiOperation("下载绩效导入模版")
    @RequestMapping(value = "/downloadAchiFile/{type}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadTemplateFile(@PathVariable String type) {
        try {
            String excel = null;
            if (type.contains("招标业务")) {
                excel = "achi_bidding.xlsx";
            } else if (type.contains("外部市场")) {
                excel = "achi_external_market.xlsx";
            } else if (type.contains("品类采购")) {
                excel = "achi_procurement.xlsx";
            }
            URL url = ResourceUtils.getURL("classpath:export/" + excel);
            byte[] byteArray = IOUtils.toByteArray(url);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", excel);
            return new ResponseEntity<>(byteArray, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("下载导入模板文件异常", e);
        }
        return null;
    }

    @CnoocLogOperation(operType = "导出", operData = "导出Excel")
    @ApiOperation(value = "导出Excel")
    @RequestMapping(value = "/exportFile", method = RequestMethod.POST)
    public ResponseMap testScore(@RequestBody AchiTaskDTO achiTask, HttpServletResponse response) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        achiTask.setAchiStatus(3);
        Page<AchiTask> pageData = achiTaskService.selectTask(achiTask);
        List<AchiTask> taskList = new ArrayList<>();
        if (pageData != null) {
            taskList = pageData.getRecords();
        }
        List<Object> list = new ArrayList<>();
        List<String> scoreNameList = new ArrayList<>();
        if (achiTask.getTypeName().contains("招标业务")) {
            scoreNameList = Arrays.asList("考核积分", "难度系数", "效率系数", "效益系数", "公平系数", "风险系数", "双选系数", "额外积分");
            list = Arrays.asList("考核任务名称", "被考核人姓名", "账号", "所属部门", "考核积分", "难度系数", "效率系数", "效益系数", "公平系数", "风险系数", "双选系数", "额外积分", "考核开始时间", "考核结束时间");
        } else if (achiTask.getTypeName().contains("外部市场")) {
            scoreNameList = Arrays.asList("考核积分", "难度系数", "效率系数", "效益系数", "公平系数", "风险系数", "额外贡献系数");
            list = Arrays.asList("考核任务名称", "被考核人姓名", "账号", "所属部门", "考核积分", "难度系数", "效率系数", "效益系数", "公平系数", "风险系数", "额外贡献系数", "考核开始时间", "考核结束时间");
        } else if (achiTask.getTypeName().contains("品类采购")) {
            scoreNameList = Arrays.asList("考核积分", "工作量系数", "工作质量系数", "公平系数", "风险系数", "额外贡献系数", "招标代理工作", "协作");
            list = Arrays.asList("考核任务名称", "被考核人姓名", "账号", "所属部门", "考核积分", "工作量系数", "工作质量系数", "公平系数", "风险系数", "额外贡献系数", "招标代理工作", "协作", "考核开始时间", "考核结束时间");
        }
        List<List<Object>> listList = new ArrayList<>();
        listList.add(list);
        for (AchiTask task : taskList) {
            List<Object> arrayList = new ArrayList<>();
            arrayList.add(task.getAchiName());
            arrayList.add(task.getWorkName());
            arrayList.add(task.getWorkId());
            arrayList.add(task.getDepartment());
            if (StringUtils.hasText(task.getDetailScore())) {
                JSONObject jsonObject = JSONObject.parseObject(task.getDetailScore());
                for (String scoreName : scoreNameList) {
                    if (jsonObject.containsKey(scoreName)) {
                        Object score = jsonObject.get(scoreName);
                        if (score != null) {
                            arrayList.add(score);
                        } else {
                            arrayList.add(0);
                        }
                    } else {
                        arrayList.add(0);
                    }
                }
            } else {
                JSONArray jsonArray = JSONObject.parseArray(task.getAutoScore());
                if (achiTask.getTypeName().contains("招标业务")) {
                    BigDecimal nandu = jisuan(0, "基础考核信息", jsonArray, 0);
                    BigDecimal xiaolv = jisuan(1, "基础考核信息", jsonArray, 0);
                    BigDecimal xiaoyi = jisuan(2, "基础考核信息", jsonArray, 0);
                    BigDecimal fengxian = jisuan(3, "基础考核信息", jsonArray, 0);
                    BigDecimal gongping = jisuan(0, "个人绩效贡献", jsonArray, 1);
                    BigDecimal shuangxuan = jisuan(1, "个人绩效贡献", jsonArray, 1);
                    BigDecimal ewai = jisuan(2, "个人绩效贡献", jsonArray, 1);
                    BigDecimal decimal = nandu.add(xiaolv).add(xiaoyi).add(fengxian).add(gongping).add(shuangxuan).add(ewai);
                    arrayList.add(decimal);
                    arrayList.add(nandu);
                    arrayList.add(xiaolv);
                    arrayList.add(xiaoyi);
                    arrayList.add(gongping);
                    arrayList.add(fengxian);
                    arrayList.add(shuangxuan);
                    arrayList.add(ewai);
                } else if (achiTask.getTypeName().contains("外部市场")) {
                    BigDecimal nandu = pinleiwaibuxishu(jsonArray, "基础考核信息", 0, 0);
                    BigDecimal xiaoyi = pinleiwaibuxishu(jsonArray, "基础考核信息", 0, 1);
                    BigDecimal fengxian = pinleiwaibuxishu(jsonArray, "基础考核信息", 0, 2);
                    BigDecimal xiaolv = pinleiwaibuxishu(jsonArray, "基础考核信息", 0, 3);
                    BigDecimal gongping = pinleiwaibuxishu(jsonArray, "个人绩效贡献", 1, 0);
                    BigDecimal ewai = pinleiwaibuxishu(jsonArray, "个人绩效贡献", 1, 1);
                    BigDecimal fenshu = nandu.add(xiaolv).add(xiaoyi).add(fengxian).add(gongping).add(ewai);
                    arrayList.add(fenshu);
                    arrayList.add(nandu);
                    arrayList.add(xiaolv);
                    arrayList.add(xiaoyi);
                    arrayList.add(gongping);
                    arrayList.add(fengxian);
                    arrayList.add(ewai);
                } else if (achiTask.getTypeName().contains("品类采购")) {
                    BigDecimal gongzuoliang = pinleiwaibuxishu(jsonArray, "基础考核信息", 0, 0);
                    BigDecimal gongzuozhiliang = pinleiwaibuxishu(jsonArray, "基础考核信息", 0, 1);
                    BigDecimal fengxian = pinleixishugeren(jsonArray, "基础考核信息", 0, 2);
                    BigDecimal gongping = pinleixishugeren(jsonArray, "个人绩效贡献", 1, 0);
                    BigDecimal ewai = pinleixishugeren(jsonArray, "个人绩效贡献", 1, 1);
                    BigDecimal daili = pinleixishugeren(jsonArray, "个人绩效贡献", 1, 2);
                    BigDecimal xiezuo = pinleixishugeren(jsonArray, "个人绩效贡献", 1, 3);
                    BigDecimal fenshu = gongping.add(gongzuoliang).add(gongzuozhiliang).add(fengxian).add(ewai).add(daili).add(xiezuo);
                    arrayList.add(fenshu);
                    arrayList.add(gongzuoliang);
                    arrayList.add(gongzuozhiliang);
                    arrayList.add(gongping);
                    arrayList.add(fengxian);
                    arrayList.add(ewai);
                    arrayList.add(daili);
                    arrayList.add(xiezuo);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (Objects.nonNull(task.getAchiStart())) {
                arrayList.add(format.format(task.getAchiStart()));
            } else {
                arrayList.add(null);
            }
            if (Objects.nonNull(task.getAchiStop())) {
                arrayList.add(format.format(task.getAchiStop()));
            } else {
                arrayList.add(null);
            }
            listList.add(arrayList);
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        ExcelUtils.export(response, "用户积分表", listList);
        return responseMap;
    }

    /**
     * 更新评分：室主任，部门经理
     *
     * @param achiScore
     * @return
     */
    @CnoocLogOperation(operType = "添加", operData = "自动评分新增")
    @ApiOperation(value = "自动评分新增")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMap updateScore(@RequestBody AchiScore achiScore) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        AchiTask task = new AchiTask();
        BeanUtils.copyProperties(task, achiScore);
        achiTaskService.updateTask(task);
        responseMap.setResult(achiScore);
        return responseMap;
    }

    @CnoocLogOperation(operType = "查询", operData = "获取详情")
    @ApiOperation(value = "获取详情")
    @GetMapping("/getDetail")
    public ResponseMap getDetail(@RequestParam String id) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        AchiTask achiTask = achiTaskService.getById(id);
        responseMap.putResult(achiTask);
        return responseMap;
    }

    @ApiOperation(value = "绩效考核计算测试接口")
    @RequestMapping(value = "/testScore", method = RequestMethod.POST)
    public ResponseMap testScore(@RequestParam String type, @RequestParam String workId,
                                 @RequestParam Long startTime, @RequestParam Long endTime) {
        ResponseMap responseMap = ResponseMap.SuccessInstance();
        try {
            String score = "";
            Date start = new Date(startTime);
            Date end = new Date(endTime);
            // 1-招标业务、2-外部市场、3-品类采购
            if ("1".equals(type)) {
                score = achiTaskService.calcZhaobiaoyewuScore(workId, start, end);
            } else if ("2".equals(type)) {
                score = achiTaskService.calcWaibushichangSocre(workId, start, end);
            } else if ("3".equals(type)) {
                score = achiTaskService.calcPinleicaigouSocre(workId, start, end);
            } else {
                score = "不支持得类型参数[" + type + "]";
            }
            responseMap.setResult(score);
        } catch (Exception e) {
            logger.error("绩效考核计算测试接口异常： ", e);
            responseMap = ResponseMap.ErrorInstance();
            responseMap.setMessage(e.getMessage());
        }
        return responseMap;
    }

    public static BigDecimal jisuan(int xishu, String type, JSONArray jsonArray, int typenum) {
        BigDecimal num = new BigDecimal(0);
        if (jsonArray.get(0) != null) {
            Object jichu = jsonArray.getJSONObject(typenum).get(type);
            JSONObject jsonObject = JSONObject.parseArray(jichu.toString()).getJSONObject(xishu);
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                Iterator<Object> iterator = JSONObject.parseArray(stringObjectEntry.getValue().toString()).iterator();
                System.err.println(stringObjectEntry.getValue());
                while (iterator.hasNext()) {
                    for (Map.Entry<String, Object> objectEntry : JSONObject.parseObject(iterator.next().toString()).entrySet()) {
                        BigDecimal bigDecimal = new BigDecimal(Double.valueOf(objectEntry.getValue().toString()));
                        num = num.add(bigDecimal);
                    }
                }
            }
            return num;
        }
        return num;
    }


    public static BigDecimal pinleiwaibuxishu(JSONArray jsonArray, String typeName, int type, int num) {
        BigDecimal bigDecimal = new BigDecimal(0);
        if (jsonArray.get(0) != null) {
            Object jichu = jsonArray.getJSONObject(type).get(typeName);
            JSONObject jsonObject = JSONObject.parseArray(jichu.toString()).getJSONObject(num);
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                for (Object o : JSONArray.parseArray(stringObjectEntry.getValue().toString())) {
                    JSONObject object = JSONArray.parseObject(o.toString());
                    for (Map.Entry<String, Object> objectEntry : object.entrySet()) {
                        JSONArray objects = JSONArray.parseArray(objectEntry.getValue().toString());
                        Iterator<Object> iterator = objects.iterator();
                        while (iterator.hasNext()) {
                            JSONObject parseObject = JSONObject.parseObject(iterator.next().toString());
                            for (Map.Entry<String, Object> entry : parseObject.entrySet()) {
                                BigDecimal decimal = new BigDecimal(Double.valueOf(entry.getValue().toString()));
                                bigDecimal = bigDecimal.add(decimal);
                            }
                        }
                    }
                }
            }
        }
        return bigDecimal;
    }

    public static BigDecimal pinleixishugeren(JSONArray jsonArray, String typeName, int type, int num) {
        BigDecimal bigDecimal = new BigDecimal(0);
        if (jsonArray.get(0) != null) {
            Object jichu = jsonArray.getJSONObject(type).get(typeName);
            System.err.println(jichu);
            JSONObject jsonObject = JSONObject.parseArray(jichu.toString()).getJSONObject(num);
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                for (Object o : JSONArray.parseArray(stringObjectEntry.getValue().toString())) {
                    JSONObject object = JSONArray.parseObject(o.toString());
                    System.err.println(o);
                    for (Map.Entry<String, Object> objectEntry : object.entrySet()) {
                        BigDecimal decimal = new BigDecimal(Double.valueOf(objectEntry.getValue().toString()));
                        bigDecimal = bigDecimal.add(decimal);
                    }
                }
            }
        }
        return bigDecimal;
    }
}


