package com.deta.achi.dao.mapper;

import com.deta.achi.pojo.AchiContribution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author DJWang
* @description 针对表【achi_contribution】的数据库操作Mapper
* @createDate 2022-08-17 16:17:42
* @Entity com.deta.achi.pojo.AchiContribution
*/
@Mapper
public interface AchiContributionMapper extends BaseMapper<AchiContribution> {
    @Select("select * from `achi_contribution` where task_id=#{taskId}")
    AchiContribution selectByTaskId(@Param("taskId") String taskId);

}




