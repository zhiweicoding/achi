package com.deta.achi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deta.achi.pojo.AchiWb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
* @author zhiwei
* @description 针对表【achi_wb】的数据库操作Mapper
* @createDate 2022-08-15 14:21:49
* @Entity com.deta.domain.AchiWb
*/
@Mapper
public interface AchiWbMapper extends BaseMapper<AchiWb> {
}




