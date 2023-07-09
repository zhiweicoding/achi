package com.deta.achi.service;


import com.deta.achi.pojo.AchiAssessTarget;

import java.util.List;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标分类service接口
 * @date 2023-01-05 19:35
 */
public interface AchiAssessTargetService {

    /*
    * 查询分类全部，此 id 的所有子列表
    * */
    List<AchiAssessTarget> queryTypeList(String id);

    /*
    * 查询id 的分类 及子列表
    * */
    AchiAssessTarget queryById(String id);

    /*
     * 查询全部父级分类
     * */
    List<AchiAssessTarget> queryList();

    /*
    * 查询全部分类及全部子列表
    * */
    List<AchiAssessTarget> queryAll();

    /*
    * 添加类别
    * */
    int insertAssessTarget(AchiAssessTarget achiAssessTarget);

    /*
    * 修改类别名称，
    * */
    int updateAssessTarget(AchiAssessTarget achiAssessTarget);

    /*
    * 根据 id 删除类别
    * */
    int removeAssessTarget(String id);

    /*
     * 根据 id 查询全部父级 id 和 父级 名称
     * */
    String getParentIdAndNames(String id);


}
