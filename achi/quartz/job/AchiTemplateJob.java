package com.deta.achi.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.deta.achi.dao.mapper.AchiTaskMapper;
import com.deta.achi.dao.mapper.AchiTemplateMapper;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.AchiTemplate;
import com.deta.achi.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时改变状态
 *
 * @author DJWang
 *
 */
@Slf4j
public class AchiTemplateJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        updateAchiTemplate();
    }


    private AchiTemplateMapper achiTemplateMapper;

    private AchiTaskMapper achiTaskMapper;

    private void updateAchiTemplate() {
        if ( achiTaskMapper == null && achiTemplateMapper == null) {
            achiTaskMapper = SpringContextUtil.getBean(AchiTaskMapper.class);
            achiTemplateMapper = SpringContextUtil.getBean((AchiTemplateMapper.class));
        }
        if ( achiTemplateMapper == null || achiTaskMapper == null) {
            return;
        } else {
            //获取当前时间
            long timeMillis = System.currentTimeMillis();
            QueryWrapper<AchiTemplate> queryWrapper = new QueryWrapper<>();
            queryWrapper.le("achi_stop", timeMillis);
            List<AchiTemplate> achiTemplateList = achiTemplateMapper.selectList(queryWrapper);
            List<String> list = new ArrayList<>();
            for (AchiTemplate template : achiTemplateList) {
                String achiId = template.getAchiId();
                list.add(achiId);
            }
            if (list.isEmpty()){
                log.error("定时任务执行失败，参数 id : {}", list);
                return;
            }
            if (achiTemplateList.size() != 0) {
                achiTemplateMapper.update(null, new UpdateWrapper<AchiTemplate>()
                        .set("status", 0)
                        .in("achi_id", list));
            }
            achiTaskMapper.update(null, new UpdateWrapper<AchiTask>()
                    .set("achi_status", 3)
                    .set("manager_status", 4).set("director_status", 4).in("achi_id", list));
        }
    }
}
