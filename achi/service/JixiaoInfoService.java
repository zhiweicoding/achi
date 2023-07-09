package com.deta.achi.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coredata.utils.date.DateUtil;
import com.deta.achi.dao.mapper.JixiaoInfoMapper;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.dto.JixiaoInfoDTO;
import com.deta.achi.exception.ResultEnum;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.JixiaoInfo;
import com.deta.achi.utils.PageTool;
import com.deta.achi.utils.SampleTool;
import com.deta.common.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DJWang
 * @description 针对表【jixiao_info】的数据库操作Service
 * @createDate 2022-08-10 11:44:37
 */
@Service
public class JixiaoInfoService extends ServiceImpl<JixiaoInfoMapper, JixiaoInfo> {

    @Autowired
    private JixiaoInfoMapper jixiaoInfoMapper;

    /**
     * 根据时间和经办人查询信息
     */
    public List<JixiaoInfo> queryTwo(AchiTask achiTask) {
        List<JixiaoInfo> js = new ArrayList<>();
        //格式化时间
        String startTime = DateUtil.df.format(achiTask.getAchiStart());
        String stopTime = DateUtil.df.format(achiTask.getAchiStop());
        //一级菜单查询
        List<JixiaoInfo> jixiaoInfos = jixiaoInfoMapper.selectList(Wrappers.<JixiaoInfo>lambdaQuery()
                .eq(JixiaoInfo::getJbrCode, achiTask.getWorkId())
                .ge(JixiaoInfo::getProjectSlDate, startTime)
                .le(JixiaoInfo::getProjectSlDate, stopTime));
        //二级菜单查询
        if (!jixiaoInfos.isEmpty()) {
            List<JixiaoInfo> deepCopyList = JsonUtil.parseObject(JsonUtil.toJSONString(jixiaoInfos), new TypeReference<>() {
            });
            Map<String, List<JixiaoInfo>> collect = deepCopyList.stream().collect(Collectors.groupingBy(JixiaoInfo::getBiaoduanname));
            for (JixiaoInfo info : jixiaoInfos) {
                String name = info.getBiaoduanname();
                List<JixiaoInfo> sons = collect.get(name);
                info.setJixiaoInfoList(sons);
            }
            js.addAll(jixiaoInfos);
        }

        return js;
    }

    /**
     * 根据标段id查询详情
     */
    public JixiaoInfoDTO queryJixiao(JixiaoInfo jixiaoInfo) {
        //判断参数是否有误
        Assert.isTrue(StringUtils.hasText(jixiaoInfo.getBiaoduanuid()), ResultEnum.PARAMS_IS_EMPTY.message());
        return jixiaoInfoMapper.queryDetail(jixiaoInfo.getBiaoduanuid());
    }


    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public Integer updateDtail(JixiaoInfo jixiaoInfo) {
        //判断参数是否有误
        Assert.isTrue(StringUtils.hasText(jixiaoInfo.getBiaoduanuid()), ResultEnum.PARAMS_IS_EMPTY.message());
        return jixiaoInfoMapper.update(jixiaoInfo, new UpdateWrapper<JixiaoInfo>()
                .eq("biaoduanuid", jixiaoInfo.getBiaoduanuid()));
    }

    /**
     * 项目名或id  时间
     *
     * @param achiTask
     * @return
     */
    public Map<String, Object> queryList(AchiTaskDTO achiTask) {
        Map<String, Object> resultMap = new HashMap<>();
        String workId = achiTask.getWorkId();
        String nameOrId = achiTask.getNameOrId();
        String startTime = null;
        String stopTime = null;
        if (Objects.nonNull(achiTask.getAchiStart())) {
            startTime = DateUtil.df.format(new Date(achiTask.getAchiStart()));
        }
        if (Objects.nonNull(achiTask.getAchiStop())) {
            stopTime = DateUtil.df.format(new Date(achiTask.getAchiStop()));
        }
        List<JixiaoInfoDTO> records = jixiaoInfoMapper.queryList(workId, startTime, stopTime);
        if (Objects.nonNull(nameOrId)) {
            records = records.stream().filter(bean -> {
                String projectname = bean.getProjectname();
                String projectno = bean.getProjectno();
                return (projectname != null && projectname.contains(nameOrId) || (projectno != null && projectno.contains(nameOrId)));
            }).collect(Collectors.toList());
        }
        JixiaoInfoDTO page = new JixiaoInfoDTO();
        page.setTAllList(records);
        page.setHasBefore(false);
        page.setEveryPageCount(achiTask.getPageSize());
        page.setHasNext(true);
        page.setWhichPageNum(achiTask.getPageNum());
        SampleTool<JixiaoInfoDTO> tool = new PageTool<>();
        JixiaoInfoDTO work = tool.work(page);
        resultMap.put("records", work.getTList());
        resultMap.put("pageNum", work.getWhichPageNum());
        resultMap.put("total", work.getAllPageCount());
        return resultMap;
    }

    /**
     * 投标供应商查询
     *
     * @param jixiaoInfo
     * @return
     */
    public String queryTbgys(JixiaoInfo jixiaoInfo) {
        return jixiaoInfoMapper.selectTbgys(jixiaoInfo.getBiaoduanuid());
    }
}
