package com.deta.achi.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coredata.utils.response.ResponseMap;
import com.deta.achi.dao.mapper.AchiAssessTargetItemMapper;
import com.deta.achi.dao.mapper.AchiAssessTargetMapper;
import com.deta.achi.exception.BizException;
import com.deta.achi.pojo.AchiAssessTarget;
import com.deta.achi.pojo.AchiAssessTargetItem;
import com.deta.achi.pojo.ResultEnum;
import com.deta.achi.pojo.vo.AchiAssessTargetItemVO;
import com.deta.achi.service.AchiAssessTargetItemService;
import com.deta.gzwreport.entity.vo.QueryPageVO;
import com.deta.framework.common.exception.InfoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标业务类
 * @date 2023-01-06 17:00
 */
@Service("achiAssessTargetItemServiceImpl")
public class AchiAssessTargetItemServiceImpl implements AchiAssessTargetItemService {

    private static final Logger logger = LoggerFactory.getLogger(AchiAssessTargetItemServiceImpl.class);


    @Autowired
    private AchiAssessTargetItemMapper achiAssessTargetItemMapper;

    @Autowired
    private AchiAssessTargetServiceImpl achiAssessTargetService;


    @Override
    public IPage<AchiAssessTargetItem> queryAchiAssessTargetItemsByAchiAssessTargetId(QueryPageVO queryPageVO) {
        // 分页查询
        IPage<AchiAssessTargetItem> page = new Page<>(queryPageVO.getPageNum(), queryPageVO.getPageSize());

        QueryWrapper<AchiAssessTargetItem> achiAssessTargetItemQueryWrapper = new QueryWrapper<>();
        // 校验参数是否全查
        String id = queryPageVO.getId();
        if (!"0".equals(id.trim())){
            // 查询他和他儿子下列表
            String sonIdsById = getSonIdsById(id);
            logger.info("他小弟：{}", sonIdsById);
            String[] split = sonIdsById.split(",");
            achiAssessTargetItemQueryWrapper.in("assess_target_id", split);
        }
        // 名称模糊
        String name = queryPageVO.getName();
        if (name != null && !"".equals(name.trim())){
            achiAssessTargetItemQueryWrapper.like("assess_target_item_name", name);
        }
        return achiAssessTargetItemMapper.selectPage(page, achiAssessTargetItemQueryWrapper);
    }

    @Override
    public AchiAssessTargetItem queryAchiAssessTargetItemById(String id) {
        return achiAssessTargetItemMapper.selectById(id);
    }

    @Override
    public int updateAchiAssessTargetItemById(AchiAssessTargetItem achiAssessTargetItem) {
        return achiAssessTargetItemMapper.updateById(achiAssessTargetItem);
    }

    public ResponseMap queryAchiAssessTargetItemsByAchiAssessTargetIdRes(QueryPageVO queryPageVO) {
        ResponseMap responseMap = null;
        // 参数校验
        if (queryPageVO == null || queryPageVO.getId() == null || "".equals(queryPageVO.getId().trim())) {
            // 参数校验不通过
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }
        // 查询操作
        IPage<AchiAssessTargetItem> pages = queryAchiAssessTargetItemsByAchiAssessTargetId(queryPageVO);

        if (pages.getRecords().isEmpty()) {
            responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
            responseMap.setResult(new ArrayList<>());
            return responseMap;
        }

        // 转换封装返回
        List<AchiAssessTargetItemVO> achiAssessTargetItemVOS = null;
        try {
            achiAssessTargetItemVOS = converAchiAssessTargetItemsToVO(pages.getRecords());
        } catch (InfoException e) {
            logger.error("VO 转实体失败：{}", JSON.toJSONString(pages.getRecords()));
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        Map<String, Object> result = new HashMap<>();

        result.put("list", achiAssessTargetItemVOS);
        result.put("total", pages.getTotal());      // 总条数
        result.put("pageNum", pages.getCurrent());  // 当前页
        result.put("pageSize", pages.getSize());    // 每页条数
        result.put("pages", pages.getPages());      // 总页数
        responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
        responseMap.setResult(result);

        return responseMap;
    }

    // 查询单条记录
    public ResponseMap queryAchiAssessTargetItemByIdRes(String id) {
        ResponseMap responseMap = null;
        // 参数校验
        if (id == null || "".equals(id.trim())) {
            // 参数校验不通过
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }
        // 查询操作
        AchiAssessTargetItem achiAssessTargetItem = queryAchiAssessTargetItemById(id);

        if (achiAssessTargetItem == null) {
            // 莫得
            responseMap = new ResponseMap(ResultEnum.QUERY_FAIL.code(), ResultEnum.QUERY_FAIL.message());
            return responseMap;
        }
        // 转换
        AchiAssessTargetItemVO achiAssessTargetItemVO;
        try {
            achiAssessTargetItemVO = converAchiAssessTargetItemToVO(achiAssessTargetItem);
        } catch (InfoException e) {
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        responseMap = new ResponseMap(ResultEnum.QUERY_SUCCESS.code(), ResultEnum.QUERY_SUCCESS.message());
        responseMap.setResult(achiAssessTargetItemVO);
        return responseMap;
    }


    public ResponseMap updateAchiAssessTargetItemByIdRes(AchiAssessTargetItemVO achiAssessTargetItemVO) {
        ResponseMap responseMap;
        // 参数校验
        if (achiAssessTargetItemVO == null || "".equals(achiAssessTargetItemVO.getId().trim())) {
            // 参数校验不通过
            responseMap = new ResponseMap(ResultEnum.PARAM_CHECK_FAIL.code(), ResultEnum.PARAM_CHECK_FAIL.message());
            return responseMap;
        }

        // 转换
        AchiAssessTargetItem achiAssessTargetItem;
        try {
            achiAssessTargetItem = converVOtoPOJO(achiAssessTargetItemVO);
        } catch (InfoException e) {
            throw new BizException(ResultEnum.CONVER_VO_ERROE, e);
        }
        // 收动填充修改时间
        achiAssessTargetItem.setGmtModified(new Timestamp(System.currentTimeMillis()));
        // 修改操作
        int i = updateAchiAssessTargetItemById(achiAssessTargetItem);
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

    private List<AchiAssessTargetItemVO> converAchiAssessTargetItemsToVO (List<AchiAssessTargetItem> list) throws InfoException {

        List<AchiAssessTargetItemVO> result = new ArrayList<>();

        for (AchiAssessTargetItem achiAssessTargetItem : list) {
            result.add(converAchiAssessTargetItemToVO(achiAssessTargetItem));
        }

        return result;

    }


    private AchiAssessTargetItemVO converAchiAssessTargetItemToVO (AchiAssessTargetItem achiAssessTargetItem) throws InfoException {
        AchiAssessTargetItemVO achiAssessTargetVO = new AchiAssessTargetItemVO();
        achiAssessTargetVO.setId(achiAssessTargetItem.getId());
        achiAssessTargetVO.setAssessTargetItemName(achiAssessTargetItem.getAssessTargetItemName());
        achiAssessTargetVO.setNotes(achiAssessTargetItem.getNotes());
        achiAssessTargetVO.setScore(achiAssessTargetItem.getScore());
        achiAssessTargetVO.setScoreType(achiAssessTargetItem.getScoreType());
        achiAssessTargetVO.setScoreTypeId(achiAssessTargetItem.getScoreTypeId());
        achiAssessTargetVO.setAddScoreType(achiAssessTargetItem.getAddScoreType());
        achiAssessTargetVO.setAddScoreTypeId(achiAssessTargetItem.getAddScoreTypeId());
        achiAssessTargetVO.setScorePlace(achiAssessTargetItem.getScorePlace());
        achiAssessTargetVO.setDateFile(achiAssessTargetItem.getDateFile());
        achiAssessTargetVO.setDateFileId(achiAssessTargetItem.getDateFileId());
        achiAssessTargetVO.setDateFileRelField(achiAssessTargetItem.getDateFileRelField());
        achiAssessTargetVO.setDateFileRelFieldId(achiAssessTargetItem.getDateFileRelFieldId());
        achiAssessTargetVO.setAssessTargetId(achiAssessTargetItem.getAssessTargetId());
        achiAssessTargetVO.setGmtCreate(achiAssessTargetItem.getGmtCreate());
        achiAssessTargetVO.setGmtModified(achiAssessTargetItem.getGmtModified());

        String parentIdAndNames = achiAssessTargetService.getParentIdAndNames(achiAssessTargetItem.getId());
        String[] split = parentIdAndNames.split(",");
        achiAssessTargetVO.setFullAssessTargetId(split[0]);
        achiAssessTargetVO.setFullAssessTargetName(split[1]);

        return achiAssessTargetVO;
    }

    private AchiAssessTargetItem converVOtoPOJO (AchiAssessTargetItemVO achiAssessTargetItemVO) throws InfoException {
        AchiAssessTargetItem achiAssessTargetItem = new AchiAssessTargetItem();
        achiAssessTargetItem.setId(achiAssessTargetItemVO.getId());
        achiAssessTargetItem.setAssessTargetItemName(achiAssessTargetItemVO.getAssessTargetItemName());
        achiAssessTargetItem.setNotes(achiAssessTargetItemVO.getNotes());
        achiAssessTargetItem.setScore(achiAssessTargetItemVO.getScore());
        achiAssessTargetItem.setScoreType(achiAssessTargetItemVO.getScoreType());
        achiAssessTargetItem.setScoreTypeId(achiAssessTargetItemVO.getScoreTypeId());
        achiAssessTargetItem.setAddScoreType(achiAssessTargetItemVO.getAddScoreType());
        achiAssessTargetItem.setAddScoreTypeId(achiAssessTargetItemVO.getAddScoreTypeId());
        achiAssessTargetItem.setScorePlace(achiAssessTargetItemVO.getScorePlace());
        achiAssessTargetItem.setDateFile(achiAssessTargetItemVO.getDateFile());
        achiAssessTargetItem.setDateFileId(achiAssessTargetItemVO.getDateFileId());
        achiAssessTargetItem.setDateFileRelField(achiAssessTargetItemVO.getDateFileRelField());
        achiAssessTargetItem.setDateFileRelFieldId(achiAssessTargetItemVO.getDateFileRelFieldId());
        achiAssessTargetItem.setAssessTargetId(achiAssessTargetItemVO.getAssessTargetId());
        achiAssessTargetItem.setIsDeleted(achiAssessTargetItemVO.getAssessTargetItemName());

        return achiAssessTargetItem;
    }

    private String getSonIdsById(String id){
        // 查询所有的子类 ids
        StringBuilder ids = new StringBuilder();
        AchiAssessTarget achiAssessTarget = achiAssessTargetService.queryById(id);
        ids.append(achiAssessTarget.getId());   // 全部奥
        for (AchiAssessTarget assessTarget : achiAssessTarget.getAccessTargetList()) {
            String sonIdsById = getSonIdsById(assessTarget.getId());
            ids.append(",").append(sonIdsById);    // 获取所有id
        }
        return new String(ids);
    }


}
