package com.deta.achi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deta.achi.dao.mapper.AchiContributionMapper;
import com.deta.achi.dto.AchiContributionDTO;
import com.deta.achi.pojo.AchiContribution;
import com.deta.achi.pojo.AchiTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author DJWang
 * @description 针对表【achi_contribution】的数据库操作Service
 * @createDate 2022-08-09 11:19:30
 */
@Service
public class AchiContributionService extends ServiceImpl<AchiContributionMapper, AchiContribution> {

    private static final Logger logger = LoggerFactory.getLogger(AchiContributionService.class);

    @Autowired
    private AchiContributionMapper achiContributionMapper;

    /**
     * 更新个人贡献绩效
     *
     * @param achiContribution
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer contrUpdate(AchiContribution achiContribution) {
        //设置更新时间
        achiContribution.setUpdateTime(System.currentTimeMillis());
        //返回更新条数
        return achiContributionMapper.update(achiContribution, Wrappers.<AchiContribution>lambdaUpdate()
                .eq(AchiContribution::getId, achiContribution.getId()));
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addCon(AchiContribution achiContribution, String userName, String domainName, String departmentName, String userId) {
        LambdaQueryWrapper<AchiContribution> queryWrapper = new LambdaQueryWrapper<AchiContribution>()
                //查询用户账号
                .eq(AchiContribution::getWorkId, userId)
                //查询是否删除
                .eq(AchiContribution::getStatus, 1)
                //查询所属分类
                .eq(Objects.nonNull(achiContribution.getTypeName()), AchiContribution::getTypeName, achiContribution.getTypeName())
                //查询年份
                .like(Objects.nonNull(achiContribution.getConYear()), AchiContribution::getConYear, achiContribution.getConYear() + "年");
        List<AchiContribution> contributions = achiContributionMapper.selectList(queryWrapper);
        //如果没有相同个人绩效贡献新增
        if (contributions.size() == 0) {
            //当前账号
            achiContribution.setWorkId(userId);
            //用户名
            achiContribution.setWorkName(userName);
            //年份
            achiContribution.setConYear(achiContribution.getConYear() + "年");
            //单位
            achiContribution.setCompany(domainName);
            //部门
            achiContribution.setDepartment(departmentName);
            //更新时间
            achiContribution.setUpdateTime(System.currentTimeMillis());
            //设置状态
            achiContribution.setStatus(1);
            //创建时间
            achiContribution.setCreateTime(System.currentTimeMillis());
            achiContributionMapper.insert(achiContribution);
            return 1;
        }
        return 0;
    }

    public Page<AchiContribution> queryList(AchiContributionDTO achiContribution) {
        Integer pageNum = achiContribution.getPageNum();
        Integer pageSize = achiContribution.getPageSize();
        LambdaQueryWrapper<AchiContribution> queryWrapper = new LambdaQueryWrapper<>();
        //开始时间
        queryWrapper.ge(Objects.nonNull(achiContribution.getStartTime()), AchiContribution::getCreateTime, achiContribution.getStartTime())
                //结束时间
                .le(Objects.nonNull(achiContribution.getStopTime()), AchiContribution::getCreateTime, achiContribution.getStopTime())
                //所属分类
                .eq(AchiContribution::getTypeName, achiContribution.getTypeName())
                //是否删除
                .eq(AchiContribution::getStatus, 1)
                //用户账号
                .eq(AchiContribution::getWorkId, achiContribution.getWorkId());
        //判断正序
        if (Objects.nonNull(achiContribution.getSort()) && "asc".equals(achiContribution.getSort())) {
            queryWrapper.orderByAsc(AchiContribution::getCreateTime);
        }
        //判断倒序
        if (Objects.nonNull(achiContribution.getSort()) && "desc".equals(achiContribution.getSort())) {
            queryWrapper.orderByDesc(AchiContribution::getCreateTime);
        }
        Page<AchiContribution> page = new Page<>(pageNum, pageSize);
        return achiContributionMapper.selectPage(page, queryWrapper);
    }

    public AchiContribution queryDetail(AchiTask achiTask) {
        //年份
        SimpleDateFormat sft = new SimpleDateFormat("yyyy");
        //根据开始时间转换
        String startTime = sft.format(new Date(achiTask.getAchiStart()));
        //所属分类
        String typeName = achiTask.getTypeName();
        //当前账号
        String workId = achiTask.getWorkId();
        //所属分类
        typeName = typeName.substring(typeName.indexOf("\\/") + 1, typeName.length());
        LambdaQueryWrapper<AchiContribution> queryWrapper = new LambdaQueryWrapper<>();
        if (typeName != null) {
            queryWrapper.eq(AchiContribution::getTypeName, typeName);
        }
        if (startTime != null) {
            queryWrapper.like(AchiContribution::getConYear, startTime);
        }
        return achiContributionMapper.selectList(
                queryWrapper.eq(AchiContribution::getWorkId, workId).eq(AchiContribution::getStatus, 1)).get(0);

    }

    public Integer updateCon(AchiContribution achiContribution) {
        return achiContributionMapper.update(null, new LambdaUpdateWrapper<AchiContribution>()
                .set(AchiContribution::getStatus, 0)
                .eq(AchiContribution::getId, achiContribution.getId()));
    }

    public Integer batchUpdateCon(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        return achiContributionMapper.update(null, new LambdaUpdateWrapper<AchiContribution>()
                .set(AchiContribution::getStatus, 0)
                .in(AchiContribution::getId, ids));
    }
}