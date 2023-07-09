package com.deta.achi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deta.achi.dto.JixiaoInfoDTO;
import com.deta.achi.pojo.JixiaoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author DJWang
* @description 针对表【jixiao_info】的数据库操作Mapper
* @createDate 2022-08-19 22:57:09
* @Entity com.deta.achi.pojo.JixiaoInfo
*/
@Mapper
public interface JixiaoInfoMapper extends BaseMapper<JixiaoInfo> {
    @Select("select `gys_name` from `jixiao_info` where `biaoduanuid`=#{biaoduanuid}")
    String selectTbgys(@Param("biaoduanuid") String biaoduanuid);

    JixiaoInfoDTO queryDetail(@Param("biaoduanuid") String biaoduanuid);

    List<JixiaoInfoDTO> queryList(@Param("workId") String workId, @Param("startTime") String startTime, @Param("stopTime") String stopTime);

    /**
     * 查询账号下的明细信息
     * @param jbrCode
     * @return
     */
    List<JixiaoInfo> queryCale(@Param("jbrCode") String jbrCode);
}




