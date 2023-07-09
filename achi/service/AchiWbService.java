package com.deta.achi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coredata.utils.date.DateUtil;
import com.deta.achi.dao.mapper.AchiWbMapper;
import com.deta.achi.dto.AchiWbDTO;
import com.deta.achi.exception.ResultEnum;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.AchiWb;
import com.deta.achi.utils.AchiEnum;
import com.deta.achi.utils.PageTool;
import com.deta.achi.utils.SampleTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author DJWang
 * @description 针对表【achi_wb】的数据库操作Service
 * @createDate 2022-08-16 22:07:16
 */
@Service
public class AchiWbService extends ServiceImpl<AchiWbMapper, AchiWb> {
    @Autowired
    private AchiWbMapper achiWbMapper;

    @Transactional(rollbackFor = {Exception.class})
    public Integer add(AchiWb achiWb, String userName, String domainName, String departmentName, String userId) {
        if (achiWb.getProgectno() != null && achiWb.getLevel() == AchiEnum.LEVEL_ONE.code()) {
            List<AchiWb> wb = achiWbMapper.selectList(new LambdaQueryWrapper<AchiWb>()
                    .eq(AchiWb::getProgectno, achiWb.getProgectno())
                    .eq(AchiWb::getStatus, AchiEnum.DELETE_FALSE.code()));
            //判断项目是否存在
            Assert.isTrue(wb.isEmpty(), "该项目已存在");
        }
        if (achiWb.getBiaoduanuid() != null && achiWb.getLevel() == AchiEnum.LEVEL_TWO.code() && achiWb.getProgectno() != null) {
            List<AchiWb> biaoduanname = achiWbMapper.selectList(new LambdaQueryWrapper<AchiWb>()
                    .eq(AchiWb::getLevel, AchiEnum.LEVEL_TWO.code())
                    .eq(AchiWb::getBiaoduanuid, achiWb.getBiaoduanuid())
                    .eq(AchiWb::getStatus, AchiEnum.DELETE_FALSE.code()));
            //判断标段是否存在
            Assert.isTrue(biaoduanname.isEmpty(), "该标段已存在");
            List<AchiWb> wb = achiWbMapper.selectList(new LambdaQueryWrapper<AchiWb>()
                    .eq(AchiWb::getProgectno, achiWb.getProgectno())
                    .eq(AchiWb::getStatus, AchiEnum.DELETE_FALSE.code()));
            //判断项目是否存在
            Assert.isTrue(!wb.isEmpty(), "该项目不存在");
        }
        achiWb.setStatus(AchiEnum.DELETE_FALSE.code());
        achiWb.setJbrCode(userId);
        achiWb.setJbrName(userName);
        achiWb.setCompany(domainName);
        achiWb.setDepartment(departmentName);
        achiWb.setCreateTime(new Date());
        return achiWbMapper.insert(achiWb);
    }

    public AchiWb selectDetail(String id) {
        Assert.isTrue(StringUtils.hasText(id), ResultEnum.PARAMS_IS_EMPTY.message());
        return achiWbMapper.selectById(id);
    }


    @Transactional(rollbackFor = {Exception.class})
    public Integer delete(List<String> ids) {
        //删除逻辑判断
        Assert.isTrue(!ids.isEmpty(),  ResultEnum.PARAMS_IS_EMPTY.message());
        //修改数据状态
        return achiWbMapper.update(null, new LambdaUpdateWrapper<AchiWb>()
                .set(AchiWb::getStatus, AchiEnum.DELETE_TRUE.code())
                .eq(ids.size() == AchiEnum.ONE.code(), AchiWb::getId, ids.get(AchiEnum.ZERO.code()))
                .in(ids.size() != AchiEnum.ONE.code(), AchiWb::getId, ids));
    }

    public List<AchiWb> selectMenu(AchiTask achiTask) {
        List<AchiWb> achiWbList = achiWbMapper.selectList(new LambdaQueryWrapper<AchiWb>()
                .ge(AchiWb::getCreateTime, DateUtil.df.format(achiTask.getAchiStart()))
                .le(AchiWb::getCreateTime, DateUtil.df.format(achiTask.getAchiStop()))
                .eq(AchiWb::getStatus, AchiEnum.DELETE_FALSE.code())
                .eq(AchiWb::getLevel, AchiEnum.LEVEL_ONE.code())
                .eq(AchiWb::getJbrCode, achiTask.getWorkId()));
        if (achiWbList.size() != AchiEnum.ZERO.code()) {
            for (AchiWb achiWb : achiWbList) {
                List<AchiWb> collect = achiWbMapper.selectList(new LambdaQueryWrapper<AchiWb>()
                        .ge(AchiWb::getCreateTime, DateUtil.df.format(achiTask.getAchiStart()))
                        .le(AchiWb::getCreateTime, DateUtil.df.format(achiTask.getAchiStop()))
                        .eq(AchiWb::getProgectno, achiWb.getProgectno())
                        .eq(AchiWb::getStatus, AchiEnum.DELETE_FALSE.code())
                        .eq(AchiWb::getJbrCode, achiTask.getWorkId())
                        .ne(AchiWb::getId, achiWb.getId()));
                achiWb.setAchiWbList(collect);
            }
        }
        return achiWbList;
    }

    public Map<String, Object> findList(AchiWbDTO achiWbDTO, String userId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        LambdaQueryWrapper<AchiWb> wrapper = Wrappers.lambdaQuery();
        //开始时间
        if (Objects.nonNull(achiWbDTO.getStartTime())) {
            wrapper.ge(AchiWb::getCreateTime, DateUtil.df.format(new Date(achiWbDTO.getStartTime())));
        }
        //结束时间
        if (Objects.nonNull(achiWbDTO.getStopTime())) {
            wrapper.le(AchiWb::getCreateTime, DateUtil.df.format(new Date(achiWbDTO.getStopTime())));
        }
        //正序
        if (Objects.nonNull(achiWbDTO.getSort()) && AchiWb.ASC.equals(achiWbDTO.getSort())) {
            wrapper.orderByAsc(AchiWb::getCreateTime);
        }
        //倒序
        if (Objects.nonNull(achiWbDTO.getSort()) && AchiWb.DESC.equals(achiWbDTO.getSort())) {
            wrapper.orderByDesc(AchiWb::getCreateTime);
        }
        wrapper.eq(AchiWb::getLevel, achiWbDTO.getLevel())
                .eq(AchiWb::getStatus, AchiEnum.DELETE_FALSE.code())
                .and(Objects.nonNull(achiWbDTO.getNameOrId()), (queryWrapper) -> {
                    queryWrapper.like(AchiWb::getProgectno, achiWbDTO.getNameOrId()).or().like(AchiWb::getProjectname, achiWbDTO.getNameOrId());
                });
        List<AchiWb> records = achiWbMapper.selectList(wrapper.eq(AchiWb::getJbrCode, userId));
        AchiWb page = new AchiWb();
        page.setTAllList(records);
        page.setHasBefore(false);
        page.setEveryPageCount(achiWbDTO.getPageSize());
        page.setHasNext(true);
        page.setWhichPageNum(achiWbDTO.getPageNum());
        SampleTool<AchiWb> tool = new PageTool<>();
        AchiWb work = tool.work(page);
        resultMap.put("array", work.getTList());
        resultMap.put("pageNum", work.getWhichPageNum());
        resultMap.put("total", work.getAllPageCount());
        return resultMap;
    }

    private boolean isEmpty(String value) {
        return value == null || "".equals(value.trim()) || "null".equals(value.trim());
    }
}
