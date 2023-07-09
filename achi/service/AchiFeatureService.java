package com.deta.achi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deta.achi.dao.mapper.AchiFeatureMapper;
import com.deta.achi.dao.mapper.AchiTemplateMapper;
import com.deta.achi.pojo.AchiFeature;
import com.deta.achi.pojo.AchiTemplate;
import com.deta.achi.utils.AchiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.deta.achi.utils.AchiEnum.LEVEL_ONE;
import static com.deta.achi.utils.AchiEnum.ONE;

/**
 * @author DJWang
 * @description 针对表【achi_feature】的数据库操作Service
 * @createDate 2022-08-08 23:29:19
 */
@Service
public class AchiFeatureService extends ServiceImpl<AchiFeatureMapper, AchiFeature> {

    @Autowired
    private AchiFeatureMapper achiFeatureMapper;

    @Autowired
    private AchiTemplateMapper achiTemplateMapper;

    /**
     * 二级菜单查询
     *
     * @param achiFeature
     * @return
     */
    public List<AchiFeature> select(AchiFeature achiFeature) {
        //条件查询某一级菜单
        if (achiFeature.getLevel() != null) {
            return achiFeatureMapper.selectList(new LambdaQueryWrapper<AchiFeature>().eq(AchiFeature::getLevel, achiFeature.getLevel())
                    .ne(AchiFeature::getStatus, AchiEnum.DELETE_TRUE.code()));
        }
        //条件查询二级菜单
        List<AchiFeature> achiFeatureList = achiFeatureMapper.selectList(new LambdaQueryWrapper<AchiFeature>()
                .eq(AchiFeature::getLevel, LEVEL_ONE.code())
                .eq(AchiFeature::getStatus, AchiEnum.DELETE_FALSE.code()));
        if (!achiFeatureList.isEmpty()) {
            for (AchiFeature feature : achiFeatureList) {
                List<AchiFeature> features = achiFeatureMapper.selectList(new LambdaQueryWrapper<AchiFeature>()
                        .eq(AchiFeature::getTypeId, feature.getTypeId())
                        .eq(AchiFeature::getStatus, AchiEnum.DELETE_FALSE.code())
                        .ne(AchiFeature::getId, feature.getId()));
                feature.setAchiFeatures(features);
            }
            return achiFeatureList;
        }
        return new ArrayList<>();
    }

    /**
     * 新增配置项
     *
     * @param achiFeature
     * @param userName
     * @return
     */
    public Integer add(AchiFeature achiFeature, String userName) {
        if (!AchiFeature.TYPE_NAME.equals(achiFeature.getName())) {
            List<AchiFeature> featureList = findByName(achiFeature.getName(), achiFeature.getLevel());
            //判断分类名是否存在
            Assert.isTrue(featureList.isEmpty(), "该分类已存在");
        }
        //分类不存在新增分类
        if (StringUtils.isEmpty(achiFeature.getTypeId())) {
            achiFeature.setTypeId(UUID.randomUUID().toString());
        }
        achiFeature.setOpsUser(userName);
        achiFeature.setOpsTime(System.currentTimeMillis());
        achiFeature.setStatus(AchiEnum.DELETE_FALSE.code());
        return achiFeatureMapper.insert(achiFeature);
    }

    /**
     * 审批分类修改
     */
    public Integer update(AchiFeature achiFeature) {
        //判断分类是否是初始值
        if (!AchiFeature.TYPE_NAME.equals(achiFeature.getName())) {
            //如果不是初始值去库中查询是否存在
            List<AchiFeature> achiFeatures = findByName(achiFeature.getName(), achiFeature.getLevel());
            //判断返回的数据是否为空，不为空弹出提示
            Assert.isTrue(achiFeatures.isEmpty(), "该分类已存在");
        }
        //如果与初始值相等不做重复校验
        achiFeature.setOpsTime(System.currentTimeMillis());
        //执行更新操作
        return achiFeatureMapper.updateById(achiFeature);
    }


    /**
     * 审批分类删除
     */
    public Integer deleteAchiFeature(AchiFeature achiFeature) {
        //判断是否为一级分类
        if (achiFeature.getLevel() != null && achiFeature.getLevel().intValue() == LEVEL_ONE.code()) {
            //查询一级分类的typeId
            AchiFeature feature = achiFeatureMapper.selectById(achiFeature.getId());
            //根据typeId查询是否存在二级菜单
            List<AchiFeature> achiFeatures = achiFeatureMapper.selectList(new LambdaQueryWrapper<AchiFeature>()
                    .eq(AchiFeature::getTypeId, feature.getTypeId())
                    .eq(AchiFeature::getStatus, AchiEnum.DELETE_FALSE.code()));
            //判断是否存在二级菜单，存在弹出提示
            Assert.isTrue(achiFeatures.size() <= ONE.code(), "该分类下有二级分类，请先删除");
        } else {
            //二级菜单根据id查询考核模版
            List<AchiTemplate> achiTemplates = achiTemplateMapper.selectList(new LambdaQueryWrapper<AchiTemplate>()
                    //模版状态是否删除
                    .eq(AchiTemplate::getStatus, AchiEnum.DELETE_FALSE.code())
                    //模版id
                    .eq(AchiTemplate::getTypeId, achiFeature.getId()));
            //判断数据是否为空，不为空弹出提示
            Assert.isTrue(achiTemplates.isEmpty(), "该分类下有数据，请先删除");
        }
        //设置参数
        achiFeature.setOpsTime(System.currentTimeMillis());
        achiFeature.setStatus(AchiEnum.DELETE_TRUE.code());
        //执行删除操作
        return achiFeatureMapper.updateById(achiFeature);
    }

    /**
     * 查询名分类名是否存在
     *
     * @param typeName
     * @param level
     * @return
     */
    public List<AchiFeature> findByName(String typeName, int level) {
        //查询分类名是否有重复，考虑一级分类下可重复
        return achiFeatureMapper.selectList(new LambdaQueryWrapper<AchiFeature>()
                //是否删除
                .eq(AchiFeature::getStatus, AchiEnum.DELETE_FALSE.code())
                //分类名
                .eq(AchiFeature::getName, typeName)
                //判断一二级分类
                .eq(AchiFeature::getLevel, level));
    }

    public Page<AchiTemplate> queryTemplates(AchiTemplate achiTemplate) {
        //查询一级菜单下所有二级菜单
        LambdaQueryWrapper<AchiFeature> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiFeature::getTypeId, achiTemplate.getAchiId())
                .eq(AchiFeature::getStatus, AchiEnum.DELETE_FALSE.code());
        List<AchiFeature> achiFeatures = achiFeatureMapper.selectList(wrapper);
        //遍历保存所有二级菜单的id
        List<String> templateIds = new ArrayList<>();
        for (AchiFeature achiFeature : achiFeatures) {
            templateIds.add(achiFeature.getId());
        }
        //通过所有二级菜单的id查询所有考核任务模版
        LambdaQueryWrapper<AchiTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AchiTemplate::getStatus, AchiEnum.DELETE_FALSE.code())
                //考核任务名
                .like(StringUtils.hasText(achiTemplate.getAchiName()), AchiTemplate::getAchiName, achiTemplate.getAchiName())
                //考核任务状态
                .eq(Objects.nonNull(achiTemplate.getAchiStatus()), AchiTemplate::getAchiStatus, achiTemplate.getAchiStatus())
                //类型id与模版地匹配
                .in(AchiTemplate::getTypeId, templateIds);
        if (achiTemplate.getPageNum() == null) {
            achiTemplate.setPageNum(1);
        }
        if (achiTemplate.getPageSize() == null) {
            achiTemplate.setPageSize(10);
        }
        Page<AchiTemplate> page = new Page<>(achiTemplate.getPageNum(), achiTemplate.getPageSize());
        return achiTemplateMapper.selectPage(page, queryWrapper);
    }
}
