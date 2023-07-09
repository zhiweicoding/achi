package com.deta.achi.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dao.mapper.AchiAssessTargetItemMapper;
import com.deta.achi.dao.mapper.AchiAssessTargetMapper;
import com.deta.achi.exception.BizException;
import com.deta.achi.pojo.AchiAssessTarget;
import com.deta.achi.pojo.AchiAssessTargetItem;
import com.deta.achi.pojo.ResultEnum;
import com.deta.achi.pojo.vo.AchiAssessTargetVO;
import com.deta.achi.service.AchiAssessTargetService;
import com.deta.gzwreport.entity.vo.QueryPageVO;
import com.deta.framework.common.exception.InfoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标分类业务类
 * @date 2023-01-05 19:35
 */
@Service("achiAssessTargetService")
public class AchiAssessTargetServiceImpl implements AchiAssessTargetService {

    private static final Logger LOG = LoggerFactory.getLogger(AchiAssessTargetServiceImpl.class);

    @Autowired
    private AchiAssessTargetMapper achiAssessTargetMapper;
    @Autowired
    private AchiAssessTargetItemMapper achiAssessTargetItemMapper;

    @Override
    public List<AchiAssessTarget> queryTypeList(String id) {
        // 查询全部父列表
        List<AchiAssessTarget> achiAssessTargets = queryByParentId("0");
        List<AchiAssessTarget> achiAssessTargetSon = recQueryByParentId(id);

        achiAssessTargets.stream()
                .filter(target -> target.getId().equals(id.trim())).forEach(target -> target.setAccessTargetList(achiAssessTargetSon));
        return achiAssessTargets;
    }

    @Override
    public AchiAssessTarget queryById(String id) {
        // 根据 id 查询分类
        AchiAssessTarget achiAssessTarget = achiAssessTargetMapper.selectById(id);

        if (achiAssessTarget == null || "".equals(achiAssessTarget.getId()) ){
            return null;    // 查询失败
        }
        // 查询子列表
        List<AchiAssessTarget> achiAssessTargetSons = recQueryByParentId(achiAssessTarget.getId());
        achiAssessTarget.setAccessTargetList(achiAssessTargetSons);
        return achiAssessTarget;
    }

    @Override
    public List<AchiAssessTarget> queryList() {
        return queryByParentId("0"); // 查询全部父级
    }

    @Override
    public List<AchiAssessTarget> queryAll() {
        // 先查询所有 父级
        return recQueryByParentId("0");
    }

    @Override
    public int insertAssessTarget(AchiAssessTarget achiAssessTarget) {
        // 添加
        return achiAssessTargetMapper.insert(achiAssessTarget);
    }

    @Override
    public int updateAssessTarget(AchiAssessTarget achiAssessTarget) {
        return achiAssessTargetMapper.updateById(achiAssessTarget);
    }

    @Override
    public int removeAssessTarget(String id) {
        return achiAssessTargetMapper.deleteById(id);
    }

    @Override
    public String getParentIdAndNames(String id) {
        // 查询父级 id 和 name
        AchiAssessTargetItem achiAssessTargetItem = achiAssessTargetItemMapper.selectById(id);
        if (achiAssessTargetItem == null)
            return "/,/"; // 没有
        return getParentIdAndNamesByParentId(achiAssessTargetItem.getAssessTargetId(), "/,/");
    }

    private String getParentIdAndNamesByParentId(String id, String res){
        if ("0".equals(id.trim())){
            // 退出
            String[] split = res.split(",");

            split[0] = split[0].substring(0, split[0].length()-1);
            split[1] = split[1].substring(0, split[1].length()-1);

            res = "/" + split[0] + ",/" + split[1];

            return res;
        }
        AchiAssessTarget achiAssessTarget = achiAssessTargetMapper.selectById(id);// 查询此类
        String[] split = res.split(",");

        split[0] = achiAssessTarget.getId()+"/" + split[0];
        split[1] = achiAssessTarget.getAccessTargetName()+"/" + split[1];
        res = split[0] + "," + split[1];
        String parentId = achiAssessTarget.getParentId();       // 获取此类的父 id

        return getParentIdAndNamesByParentId(parentId, res);
    }

    // 根据 父 id 查询
    private List<AchiAssessTarget> queryByParentId(String id){
        QueryWrapper<AchiAssessTarget> achiAssessTargetQueryWrapper = new QueryWrapper<>();
        achiAssessTargetQueryWrapper.eq("parent_id", id);
        return achiAssessTargetMapper.selectList(achiAssessTargetQueryWrapper);
    }

    // 循环查询子集
    private List<AchiAssessTarget> recQueryByParentId(String id){
        // 先查询最外边子集
        List<AchiAssessTarget> achiAssessTargets = queryByParentId(id);
        for (AchiAssessTarget next : achiAssessTargets) {
            // 查询子集
            next.setAccessTargetList(recQueryByParentId(next.getId()));
        }
        return achiAssessTargets;
    }

    public ResponseMap queryTypeListRes(String id) {
        ResponseMap responseMap = null;
        if (id == null || "".equals(id.trim())) {
            // 参数校验失败
            LOG.error("查询参数校验失败 id：{}", id);
            responseMap = new ResponseMap(ResultEnum.PARAMS_IS_EMPTY.code(), ResultEnum.PARAMS_IS_EMPTY.message());
            return responseMap;
        }
        // 查询操作
        LOG.info("根据id 查询 绩效指标列表 id：{}", id);
        List<AchiAssessTarget> achiAssessTargets = queryTypeList(id);
        if (achiAssessTargets.isEmpty()) {
            responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
            responseMap.setResult(new ArrayList<>());   // 查询为空无需转换
            return responseMap;
        }
        // 转换 VO 返回
        responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
        List<AchiAssessTargetVO> achiAssessTargetVOS = new ArrayList<>();
        try {
            achiAssessTargetVOS = convertOneVOList(achiAssessTargets);
        } catch (InfoException e) {
            LOG.error("VO 转实体失败!!!：{}", JSON.toJSONString(achiAssessTargets));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        responseMap.setResult(achiAssessTargetVOS);   // 查询为空无需转换
        return responseMap;
    }

    public ResponseMap queryByIdRes(String id){
        ResponseMap responseMap = null;
        // 参数校验
        if (id == null || "".equals(id)){
            // 参数校验失败
            LOG.error("参数校验失败 id：{}", id);
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }

        LOG.info("根据id 查询 绩效指标列表 id：{}", id);
        AchiAssessTarget achiAssessTarget = queryById(id);

        if (achiAssessTarget == null){
            //查询不到
            responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
            responseMap.setResult(new ArrayList<>());   // 查询为空无需转换
            return responseMap;
        }

        // 转换 VO 返回
        responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
        AchiAssessTargetVO achiAssessTargetVO = new AchiAssessTargetVO();
        try {
            achiAssessTargetVO = convertOneVO(achiAssessTarget);
        } catch (InfoException e) {
            LOG.error("VO 转实体失败!：{}", JSON.toJSONString(achiAssessTarget));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        responseMap.setResult(achiAssessTargetVO);   // 查询为空无需转换
        return responseMap;
    }


    public ResponseMap queryListRes(){
        ResponseMap responseMap = null;
        LOG.info("查询 绩效指标列表");
        List<AchiAssessTarget> achiAssessTargets = queryList();
        if (achiAssessTargets.isEmpty()) {
            responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
            responseMap.setResult(new ArrayList<>());   // 查询为空无需转换
            return responseMap;
        }
        // 转换 VO 返回
        responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
        List<AchiAssessTargetVO> achiAssessTargetVOS = new ArrayList<>();
        try {
            achiAssessTargetVOS = convertOneVOList(achiAssessTargets);
        } catch (InfoException e) {
            LOG.error("VO 转实体失败！：{}", JSON.toJSONString(achiAssessTargets));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        responseMap.setResult(achiAssessTargetVOS);
        return responseMap;
    }

    public ResponseMap queryAllRes(){
        ResponseMap responseMap = null;
        LOG.info("查询 绩效指标列表");
        List<AchiAssessTarget> achiAssessTargets = queryAll();
        if (achiAssessTargets.isEmpty()) {
            responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
            responseMap.setResult(new ArrayList<>());   // 查询为空无需转换
            return responseMap;
        }
        // 转换 VO 返回
        responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
        List<AchiAssessTargetVO> achiAssessTargetVOS = new ArrayList<>();
        try {
            achiAssessTargetVOS = convertOneVOList(achiAssessTargets);
        } catch (InfoException e) {
            LOG.error("VO 转实体失败！！：{}", JSON.toJSONString(achiAssessTargets));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        responseMap.setResult(achiAssessTargetVOS);
        return responseMap;
    }

    // 添加
    public ResponseMap insertAssessTargetRes(AchiAssessTargetVO achiAssessTargetVO){
        ResponseMap responseMap = null;
        if (achiAssessTargetVO == null){
            // 参数校验失败
            LOG.error("参数校验失败 achiAssessTargetVO：{}", achiAssessTargetVO);
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }

        // 转换 VO 为 POJO
        AchiAssessTarget achiAssessTarget = null;
        try {
            achiAssessTarget = converVOtoPOJO(achiAssessTargetVO);
        } catch (InfoException e) {
            LOG.error("VO 转实体失败！！！：{}", JSON.toJSONString(achiAssessTargetVO));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        // 转换完毕添加
        int i = insertAssessTarget(achiAssessTarget);
        if (i > 0){
            // 添加成功
            responseMap = new ResponseMap(ResultEnum.INSERT_SUCCESS.code(), ResultEnum.INSERT_SUCCESS.message());
            responseMap.setResult(i);
            return responseMap;
        }
        // 添加失败
        responseMap = new ResponseMap(ResultEnum.INSERT_FAIL.code(), ResultEnum.INSERT_FAIL.message());
        return responseMap;
    }

    // 修改
    public ResponseMap updateAssessTargetRes(AchiAssessTargetVO achiAssessTargetVO){
        ResponseMap responseMap = null;
        if (achiAssessTargetVO == null || achiAssessTargetVO.getId() == null || "".equals(achiAssessTargetVO.getId().trim()) ){
            // 参数校验失败
            LOG.error("参数校验失败 achiAssessTargetVO：{}", achiAssessTargetVO);
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }

        // 转换 VO 为 POJO
        AchiAssessTarget achiAssessTarget = null;
        try {
            achiAssessTarget = converVOtoPOJO(achiAssessTargetVO);
        } catch (InfoException e) {
            LOG.error("VO 转实体失败!!：{}", JSON.toJSONString(achiAssessTargetVO));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        // 转换完毕修改
        int i = updateAssessTarget(achiAssessTarget);
        if (i > 0){
            // 修改成功
            responseMap = new ResponseMap(ResultEnum.UPDATE_SUCCESS.code(), ResultEnum.UPDATE_SUCCESS.message());
            responseMap.setResult(i);
            return responseMap;
        }
        // 修改失败
        responseMap = new ResponseMap(ResultEnum.UPDATE_FAIL.code(), ResultEnum.UPDATE_FAIL.message());
        return responseMap;
    }

    // 删除
    public ResponseMap removeAssessTargetRes(QueryPageVO queryPageVO){
        ResponseMap responseMap = null;
        // 参数校验
        if (queryPageVO == null || queryPageVO.getId() == null ||  "".equals(queryPageVO.getId().trim())){
            // 参数校验失败
            LOG.error("参数校验失败 id：{}", queryPageVO);
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }

        String id = queryPageVO.getId();
        // 先查询是否有小弟，有小弟则不允许删除
        QueryWrapper<AchiAssessTargetItem> achiAssessTargetItemQueryWrapper = new QueryWrapper<>();
        achiAssessTargetItemQueryWrapper.eq("assess_target_id", id);
        int bro = achiAssessTargetItemMapper.selectCount(achiAssessTargetItemQueryWrapper);
        if (bro > 0){
            responseMap = new ResponseMap(ResultEnum.REMOVE_FAIL_EXISTS_BRO.code(), ResultEnum.REMOVE_FAIL_EXISTS_BRO.message());
            return responseMap;
        }

        // 先查询是否有子集，有则不允许删除
        AchiAssessTarget achiAssessTarget = queryById(id);


        if (achiAssessTarget == null){
            responseMap = new ResponseMap(ResultEnum.REMOVE_FAIL_NOT_EXISTS.code(), ResultEnum.REMOVE_FAIL_NOT_EXISTS.message());
            return responseMap;
        }

        if (!achiAssessTarget.getAccessTargetList().isEmpty()){
            // 删除失败
            LOG.error("删除失败此 id：{} 有子集", id);
            responseMap = new ResponseMap(ResultEnum.REMOVE_FAIL_EXISTS_SON.code(), ResultEnum.REMOVE_FAIL_EXISTS_SON.message());
            return responseMap;
        }
        // 删除操作
        LOG.info("根据 id 删除 分类：{}", id);
        int i = removeAssessTarget(id);
        if (i > 0){
            // 删除成功
            responseMap = new ResponseMap(ResultEnum.REMOVE_SUCCESS.code(), ResultEnum.REMOVE_SUCCESS.message());
            responseMap.setResult(i);
            return responseMap;
        }
        // 删除失败
        responseMap = new ResponseMap(ResultEnum.REMOVE_FAIL.code(), ResultEnum.REMOVE_FAIL.message());
        return responseMap;
    }

    // pojo 转换为 单个 VO
    private AchiAssessTargetVO convertOneVO(AchiAssessTarget achiAssessTarget) throws InfoException{
        // 先转换自己
        AchiAssessTargetVO achiAssessTargetVO = new AchiAssessTargetVO();
        achiAssessTargetVO.setId(achiAssessTarget.getId());
        achiAssessTargetVO.setAccessTargetName(achiAssessTarget.getAccessTargetName());
        achiAssessTargetVO.setParentId(achiAssessTarget.getParentId());
        // 转换 子集
        if (!achiAssessTarget.getAccessTargetList().isEmpty()){
            achiAssessTargetVO.setAccessTargetList(convertOneVOList(achiAssessTarget.getAccessTargetList()));
        }
        return achiAssessTargetVO;
    }

    // pojo 转换为 批量 VO
    private List<AchiAssessTargetVO> convertOneVOList(List<AchiAssessTarget> achiAssessTargets) throws  InfoException{
        List<AchiAssessTargetVO> list = new ArrayList<>();
        for (AchiAssessTarget achiAssessTarget : achiAssessTargets) {
            list.add(convertOneVO(achiAssessTarget));
        }
        return list;
    }


    private AchiAssessTarget converVOtoPOJO(AchiAssessTargetVO achiAssessTargetVO) throws InfoException{
        AchiAssessTarget achiAssessTarget = new AchiAssessTarget();
        achiAssessTarget.setId(achiAssessTargetVO.getId());
        achiAssessTarget.setAccessTargetName(achiAssessTargetVO.getAccessTargetName());
        achiAssessTarget.setParentId(achiAssessTargetVO.getParentId());
        achiAssessTarget.setIsDeleted("0"); // 莫得删除
        return achiAssessTarget;
    }

}
