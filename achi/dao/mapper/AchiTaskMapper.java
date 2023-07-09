package com.deta.achi.dao.mapper;

import com.deta.achi.pojo.AchiTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deta.achi.pojo.AchiTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author DJWang
* @description 针对表【achi_task】的数据库操作Mapper
* @createDate 2022-08-16 17:57:00
* @Entity com.deta.achi.pojo.AchiTask
*/
@Mapper
public interface AchiTaskMapper extends BaseMapper<AchiTask> {
    /**
     * 查询考核任务
     * @param achiId
     * @return
     */
    @Select("select * from `achi_task` where achi_id=#{achiId}")
    List<AchiTask> selectByAchiId(String achiId);

}




