package com.deta.achi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deta.achi.dao.mapper.AchiTaskMapper;
import com.deta.achi.dao.mapper.AchiTemplateMapper;
import com.deta.achi.dto.AchiTemplateDTO;
import com.deta.achi.exception.SdkException;
import com.deta.achi.pojo.AchiTask;
import com.deta.achi.pojo.AchiTemplate;
import com.deta.achi.pojo.ResultEnum;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import com.deta.achi.utils.AchiEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.deta.achi.utils.AchiEnum.DELETE_FALSE;
import static com.deta.achi.utils.AchiEnum.DELETE_TRUE;
import static com.deta.filter.RefererFilter.logger;

/**
 * @author DJWang
 * @description 针对表【achi_template】的数据库操作Service
 * @createDate 2022-08-08 22:42:06
 */
@Service
public class AchiTemplateService extends ServiceImpl<AchiTemplateMapper, AchiTemplate> {
    @Autowired
    private AchiTemplateMapper achiTemplateMapper;
    @Autowired
    private AchiTaskMapper achiTaskMapper;
    @Autowired
    private AchiTaskServiceImpl achiTaskService;

    public boolean add(AchiTemplateDTO achiTemplateDTO, String userName) {
        //创建新的模版
        AchiTemplate achiTemplate = new AchiTemplate();
        String type = achiTemplateDTO.getType();
        Map<String, String> map = achiTemplateDTO.getMap();
        //给template赋值
        BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
        //创建人
        achiTemplate.setCreateUser(userName);
        //创建时间
        achiTemplate.setCreateTime(System.currentTimeMillis());
        //更新时间
        achiTemplate.setUpdateTime(System.currentTimeMillis());
        //更新人
        achiTemplate.setUpdateUser(userName);
        //设置模版状态
        achiTemplate.setStatus(DELETE_FALSE.code());
        //设置考核任务状态
        achiTemplate.setAchiStatus(AchiEnum.ONE.code());
        //执行插入
        achiTemplateMapper.insert(achiTemplate);
        //根据用户创建考核任务
        List<AchiTask> list = this.add(map, achiTemplate, type);
        //执行批量插入
        return achiTaskService.saveBatch(list);
    }


    public AchiTemplate findById(AchiTemplate achiTemplate) {
        //判断传参数否为空
        Assert.isTrue(StringUtils.hasText(achiTemplate.getAchiId()), ResultEnum.PARAMS_IS_EMPTY.message());
        //不为空查询id
        return achiTemplateMapper.selectById(achiTemplate.getAchiId());
    }

    /**
     * 删除考核任务模版同时删除对应考核任务
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = SdkException.class)
    public Integer delete(List<String> ids) {
        //判断入参
        Assert.isTrue(!CollectionUtils.isEmpty(ids), ResultEnum.PARAMS_IS_EMPTY.message());
        int templateCount = 0;
        //修改考核模版状态
        templateCount = achiTemplateMapper.update(null, new UpdateWrapper<AchiTemplate>()
                .set("status", AchiEnum.DELETE_TRUE.code()).in("achi_id", ids));
        //修改考核任务状态
        int taskCount = achiTaskMapper.update(null, new UpdateWrapper<AchiTask>()
                .set("status", AchiEnum.THREE.code()).in("achi_id", ids));
        logger.info("update template count={}, update task count={}", templateCount, taskCount);
        return templateCount;
    }

    /**
     * 多条件查询考核任务模版
     *
     * @param achiTemplate
     * @return
     */
    public Page<AchiTemplate> queryLike(AchiTemplate achiTemplate) {
        LambdaQueryWrapper<AchiTemplate> wrapper = new LambdaQueryWrapper<>();
        //状态
        wrapper.eq(AchiTemplate::getStatus, DELETE_FALSE.code())
                //考核任务名
                .like(StringUtils.hasText(achiTemplate.getAchiName()), AchiTemplate::getAchiName, achiTemplate.getAchiName())
                //考核分类
                .like(StringUtils.hasText(achiTemplate.getType()), AchiTemplate::getType, achiTemplate.getType())
                //考核分类Id
                .eq(StringUtils.hasText(achiTemplate.getTypeId()), AchiTemplate::getTypeId, achiTemplate.getTypeId())
                //考核任务状态
                .eq(Objects.nonNull(achiTemplate.getAchiStatus()), AchiTemplate::getAchiStatus, achiTemplate.getAchiStatus())
                //时间排序
                .orderByDesc(AchiTemplate::getCreateTime);
        if (achiTemplate.getPageNum() == null) {
            achiTemplate.setPageNum(1);
        }
        if (achiTemplate.getPageSize() == null) {
            achiTemplate.setPageSize(10);
        }
        Page<AchiTemplate> page = new Page<>(achiTemplate.getPageNum(), achiTemplate.getPageSize());
        return achiTemplateMapper.selectPage(page, wrapper);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Integer achiUpdate(AchiTemplateDTO achiTemplateDTO) {
        Map<String, String> dtoMap = achiTemplateDTO.getMap();
        AchiTask achiTasks = new AchiTask();
        //删除
        if (achiTemplateDTO.getStatus() != null && achiTemplateDTO.getStatus().equals(0)) {
            return deleteTemplate(achiTemplateDTO);
        }
        //停用
        if (achiTemplateDTO.getAchiStatus() != null && achiTemplateDTO.getAchiStatus().equals(1)) {
            return Deactivate(achiTemplateDTO);
        }
        //启用
        if (achiTemplateDTO.getAchiStatus() != null && achiTemplateDTO.getAchiStatus().equals(2)) {
            return TemplateSatrt(achiTemplateDTO);
        }

        //人员变动
        List<AchiTask> achiTaskList = achiTaskMapper.selectList(new QueryWrapper<AchiTask>()
                .eq("achi_id", achiTemplateDTO.getAchiId())
                .eq("status", 0));
        if (dtoMap == null) {
            if (achiTaskList.size() == 0) {
                AchiTemplate achiTemplate = new AchiTemplate();
                BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
                return achiTemplateMapper.updateById(achiTemplate);
            }
            List<String> list = new ArrayList<>();
            for (AchiTask achiTask : achiTaskList) {
                list.add(achiTask.getWorkId());
            }
            achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                    .set("status", 3)
                    .eq("achi_id", achiTemplateDTO.getAchiId())
                    .in("work_id", list));
            AchiTemplate achiTemplate = new AchiTemplate();
            BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
            return achiTemplateMapper.updateById(achiTemplate);
        }
        if (dtoMap.size() == achiTaskList.size() && dtoMap.size() != 0 && achiTaskList.size() != 0) {
            List<String> xiangtong = new ArrayList<>(); //相同
            List<String> shanchu = new ArrayList<>(); //数据库有map没有
            for (AchiTask achiTask : achiTaskList) {
                if (dtoMap.get(achiTask.getWorkId()) != null) {
                    xiangtong.add(achiTask.getWorkId());
                    dtoMap.remove(achiTask.getWorkId());
                } else {
                    shanchu.add(achiTask.getWorkId());
                }
            }
            //数据库有map没有删除
            if (shanchu.size() != 0) {
                achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                        .set("status", 3)
                        .eq("achi_id", achiTemplateDTO.getAchiId())
                        .in("work_id", shanchu));
            }
            //相同修改
            if (xiangtong.size() != 0) {
                achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                        .set("status", 0)
                        .eq("achi_id", achiTemplateDTO.getAchiId())
                        .in("work_id", xiangtong));
            }
            if (dtoMap.size() != 0) {
                //不同新增
                achiTaskService.saveBatch(this.addDTO(dtoMap, achiTemplateDTO));
            }
            AchiTemplate achiTemplate = new AchiTemplate();
            BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
            return achiTemplateMapper.updateById(achiTemplate);
        }
        if (dtoMap.size() > achiTaskList.size() && dtoMap.size() != 0 && achiTaskList.size() != 0) {
            Map<String, String> achiMap = new HashMap<>();
            List<String> xiangtong = new ArrayList<>();
            Map<String, String> xinzeng = new HashMap<>();
            for (AchiTask achiTask : achiTaskList) {
                achiMap.put(achiTask.getWorkId(), achiTask.getWorkName());
            }
            for (Map.Entry<String, String> entry : dtoMap.entrySet()) {
                if (achiMap.get(entry.getKey()) != null) {
                    xiangtong.add(entry.getKey());
                    achiMap.remove(entry.getKey());//数据库有map没有
                } else {
                    xinzeng.put(entry.getKey(), entry.getValue());//map有数据库没有
                }
            }
            //相同修改
            if (xiangtong.size() != 0) {
                achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                        .set("status", 0)
                        .eq("achi_id", achiTemplateDTO.getAchiId())
                        .in("work_id", xiangtong));
            }
            if (xinzeng.size() != 0) {
                //新增
                achiTaskService.saveBatch(this.addDTO(dtoMap, achiTemplateDTO));
            }
            //删除
            if (achiMap.size() != 0) {
                achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                        .set("status", 3)
                        .eq("achi_id", achiTemplateDTO.getAchiId())
                        .in("work_id", achiMap));
            }
            AchiTemplate achiTemplate = new AchiTemplate();
            BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
            return achiTemplateMapper.updateById(achiTemplate);
        }
        if (dtoMap.size() < achiTaskList.size() && dtoMap.size() != 0 && achiTaskList.size() != 0) {
            List<String> xiangtong = new ArrayList<>();
            List<String> shanchu = new ArrayList<>();
            for (AchiTask achiTask : achiTaskList) {
                if (dtoMap.get(achiTask.getWorkId()) != null) {
                    xiangtong.add(achiTask.getWorkId());
                    dtoMap.remove(achiTask.getWorkId());
                } else {
                    shanchu.add(achiTask.getWorkId());
                }
            }
            if (shanchu.size() != 0) {
                //数据库有map没有删除
                achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                        .set("status", 3)
                        .eq("achi_id", achiTemplateDTO.getAchiId())
                        .in("work_id", shanchu));
            }
            if (xiangtong.size() != 0) {
                //相同修改
                achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                        .set("status", 0)
                        .eq("achi_id", achiTemplateDTO.getAchiId())
                        .in("work_id", xiangtong));
            }
            if (dtoMap.size() != 0) {
                achiTaskService.saveBatch(this.addDTO(dtoMap, achiTemplateDTO));
            }
            AchiTemplate achiTemplate = new AchiTemplate();
            BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
            return achiTemplateMapper.updateById(achiTemplate);
        } else if (dtoMap.size() != 0 && achiTaskList.size() == 0) {
            achiTaskService.saveBatch(this.addDTO(dtoMap, achiTemplateDTO));
            AchiTemplate achiTemplate = new AchiTemplate();
            BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
            return achiTemplateMapper.updateById(achiTemplate);
        }
        if (dtoMap.size() == 0 && achiTaskList.size() != 0) {
            List<String> list = new ArrayList<>();
            for (AchiTask achiTask : achiTaskList) {
                list.add(achiTask.getWorkId());
            }
            achiTaskMapper.update(achiTasks, new UpdateWrapper<AchiTask>()
                    .set("status", 3)
                    .eq("achi_id", achiTemplateDTO.getAchiId())
                    .in("work_id", list));
        }
        AchiTemplate achiTemplate = new AchiTemplate();
        BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
        return achiTemplateMapper.updateById(achiTemplate);
    }

    /**
     * 启用模版
     *
     * @param achiTemplateDTO
     * @return
     */
    private Integer TemplateSatrt(AchiTemplateDTO achiTemplateDTO) {
        //启用模版
        achiTaskMapper.update(null, new LambdaUpdateWrapper<AchiTask>()
                .set(AchiTask::getStatus, DELETE_FALSE.code())
                .eq(AchiTask::getAchiId, achiTemplateDTO.getAchiId())
                .eq(AchiTask::getStatus, DELETE_TRUE.code()));
        AchiTemplate achiTemplate = new AchiTemplate();
        BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
        //更新模版
        return achiTemplateMapper.updateById(achiTemplate);
    }

    /**
     * 查询模版详情
     *
     * @param achiTemplate
     * @return
     */
    public AchiTemplateDTO templateDetail(AchiTemplate achiTemplate) {
        AchiTemplate result = achiTemplateMapper.selectById(achiTemplate.getAchiId());
        HashMap<String, String> map = new HashMap<>();
        List<AchiTask> achiTaskList = achiTaskMapper.selectList(new QueryWrapper<AchiTask>()
                .eq("achi_id", achiTemplate.getAchiId())
                .eq("status", DELETE_TRUE.code()));
        for (AchiTask achiTask : achiTaskList) {
            map.put(achiTask.getWorkId(), achiTask.getWorkName());
        }
        AchiTemplateDTO templateDTO = new AchiTemplateDTO();
        BeanUtils.copyProperties(result, templateDTO);
        templateDTO.setMap(map);
        return templateDTO;
    }

    /**
     * 删除模版
     *
     * @param achiTemplateDTO
     * @return
     */
    private Integer deleteTemplate(AchiTemplateDTO achiTemplateDTO) {
        achiTaskMapper.update(null, new UpdateWrapper<AchiTask>()
                .set("status", 3)
                .eq("achi_id", achiTemplateDTO.getAchiId())
                .eq("status", 0));
        AchiTemplate achiTemplate = new AchiTemplate();
        BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
        return achiTemplateMapper.updateById(achiTemplate);
    }

    /**
     * 停用模版
     *
     * @param achiTemplateDTO
     * @return
     */
    private Integer Deactivate(AchiTemplateDTO achiTemplateDTO) {
        achiTaskMapper.update(null, new LambdaUpdateWrapper<AchiTask>()
                .set(AchiTask::getAchiStatus, 3)
                .set(AchiTask::getDirectorStatus, 4)
                .set(AchiTask::getManagerStatus, 4)
                .eq(AchiTask::getStatus, 1)
                .eq(AchiTask::getAchiId, achiTemplateDTO.getAchiId()));
        AchiTemplate achiTemplate = new AchiTemplate();
        BeanUtils.copyProperties(achiTemplateDTO, achiTemplate);
        return achiTemplateMapper.updateById(achiTemplate);
    }

    private List<AchiTask> add(Map<String, String> map, AchiTemplate achiTemplate, String type) {
        List<AchiTask> list = new ArrayList<>();
        for (Map.Entry<String, String> s : map.entrySet()) {
            AchiTask achiTask = new AchiTask();
            achiTask.setAchiName(achiTemplate.getAchiName());
            achiTask.setId(UUID.randomUUID().toString());
            achiTask.setAchiStart(achiTemplate.getAchiStart());
            achiTask.setAchiStop(achiTemplate.getAchiStop());
            achiTask.setWorkName(s.getValue());
            achiTask.setWorkId(s.getKey());
            achiTask.setCompany(achiTemplate.getCompany());
            achiTask.setDepartment(achiTemplate.getDepartment());
            achiTask.setAchiStatus(1);
            achiTask.setStatus(0);
            achiTask.setTypeId(achiTemplate.getTypeId());
            achiTask.setTypeName(achiTemplate.getType());
            achiTask.setDirectorStatus(1);
            achiTask.setManagerStatus(1);
            achiTask.setAchiId(achiTemplate.getAchiId());
            if (type != null && type.contains("招标业务")) {
                achiTask.setAutoScore(achiTaskService.calcZhaobiaoyewuScore(s.getKey(), new Date(achiTemplate.getAchiStart()), new Date(achiTemplate.getAchiStop())));
            } else if (type != null && type.contains("外部市场")) {
                achiTask.setAutoScore(achiTaskService.calcWaibushichangSocre(s.getKey(), new Date(achiTemplate.getAchiStart()), new Date(achiTemplate.getAchiStop())));
            } else if (type != null && type.contains("品类采购")) {
                achiTask.setAutoScore(achiTaskService.calcPinleicaigouSocre(s.getKey(), new Date(achiTemplate.getAchiStart()), new Date(achiTemplate.getAchiStop())));
            }
            list.add(achiTask);
        }
        return list;
    }


    private List<AchiTask> addDTO(Map<String, String> dtoMap, AchiTemplateDTO achiTemplateDTO) {
        List<AchiTask> list = new ArrayList<>();
        for (Map.Entry<String, String> s : dtoMap.entrySet()) {
            AchiTask achiTask = new AchiTask();
            String type = achiTemplateDTO.getType();
            achiTask.setAchiName(achiTemplateDTO.getAchiName());
            achiTask.setId(UUID.randomUUID().toString());
            achiTask.setAchiStart(achiTemplateDTO.getAchiStart());
            achiTask.setAchiStop(achiTemplateDTO.getAchiStop());
            achiTask.setWorkName(s.getValue());
            achiTask.setWorkId(s.getKey());
            achiTask.setCompany(achiTemplateDTO.getCompany());
            achiTask.setDepartment(achiTemplateDTO.getDepartment());
            achiTask.setAchiStatus(1);
            achiTask.setStatus(0);
            achiTask.setTypeId(achiTemplateDTO.getTypeId());
            achiTask.setTypeName(achiTemplateDTO.getType());
            achiTask.setDirectorStatus(1);
            achiTask.setManagerStatus(1);
            achiTask.setAchiId(achiTemplateDTO.getAchiId());
            if (type != null && type.contains("招标业务")) {
                achiTask.setAutoScore(achiTaskService.calcZhaobiaoyewuScore(s.getKey(), new Date(achiTemplateDTO.getAchiStart()), new Date(achiTemplateDTO.getAchiStop())));
            } else if (type != null && type.contains("外部市场")) {
                achiTask.setAutoScore(achiTaskService.calcWaibushichangSocre(s.getKey(), new Date(achiTemplateDTO.getAchiStart()), new Date(achiTemplateDTO.getAchiStop())));
            } else if (type != null && type.contains("品类采购")) {
                achiTask.setAutoScore(achiTaskService.calcPinleicaigouSocre(s.getKey(), new Date(achiTemplateDTO.getAchiStart()), new Date(achiTemplateDTO.getAchiStop())));
            }
            list.add(achiTask);
        }
        return list;
    }
}
