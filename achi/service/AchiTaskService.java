package com.deta.achi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.pojo.AchiTask;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
* @author DJWang
* @description 针对表【achi_task】的数据库操作Service
* @createDate 2022-08-08 22:46:53
*/
public interface AchiTaskService extends IService<AchiTask>{
    /**
     * 个人考核信息查询
     * @param achiTask
     * @param userId
     * @return
     */
    Page<AchiTask> select(AchiTaskDTO achiTask, String userId);

    /**
     * 通用查询方法
     * @param achiTask
     * @return
     */
    Page<AchiTask> selectTask(AchiTaskDTO achiTask);

    /**
     * 更新考核任务
     * @param achiTask
     * @return
     */
    Integer updateTask(AchiTask achiTask);

    /**
     * 查询考核任务详情
     * @param achiTask
     * @return
     */
    AchiTask selectDtail(AchiTask achiTask);

    /**
     * 根据考核任务详情查询
     * @param achId
     * @return
     */
    List<AchiTask> selectByAchiId(String achId);

    /**
     * 导入历史考核
     * @param file
     * @param domainName
     * @return
     */
    boolean uploadFile(MultipartFile file, String domainName);
}
