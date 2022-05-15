package cn.sliew.scaleph.service.admin.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sliew.scaleph.dao.entity.log.ScheduleLog;
import cn.sliew.scaleph.dao.mapper.log.ScheduleLogMapper;
import cn.sliew.scaleph.service.admin.ScheduleLogService;
import cn.sliew.scaleph.service.convert.admin.ScheduleLogConvert;
import cn.sliew.scaleph.service.dto.admin.ScheduleLogDTO;
import cn.sliew.scaleph.service.param.admin.ScheduleLogParam;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author gleiyu
 */
@Service
public class ScheduleLogServiceImpl implements ScheduleLogService {

    @Autowired
    private ScheduleLogMapper scheduleLogMapper;

    @Override
    public int insert(ScheduleLogDTO scheduleLog) {
        ScheduleLog log = ScheduleLogConvert.INSTANCE.toDo(scheduleLog);
        return this.scheduleLogMapper.insert(log);
    }

    @Override
    public ScheduleLogDTO selectOne(Long id) {
        ScheduleLog log = this.scheduleLogMapper.selectById(id);
        return ScheduleLogConvert.INSTANCE.toDto(log);
    }

    @Override
    public Page<ScheduleLogDTO> listByPage(ScheduleLogParam param) {
        Page<ScheduleLogDTO> result = new Page<>();
        Page<ScheduleLog> list = this.scheduleLogMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                Wrappers.lambdaQuery(ScheduleLog.class)
                        .eq(StrUtil.isNotEmpty(param.getTaskGroup()), ScheduleLog::getTaskGroup, param.getTaskGroup())
                        .eq(StrUtil.isNotEmpty(param.getTaskName()), ScheduleLog::getTaskName, param.getTaskName())
                        .eq(StrUtil.isNotEmpty(param.getResult()), ScheduleLog::getResult, param.getResult())
                        .gt(ObjectUtil.isNotNull(param.getStartTime()), ScheduleLog::getStartTime, param.getStartTime())
                        .lt(ObjectUtil.isNotNull(param.getEndTime()), ScheduleLog::getEndTime, param.getEndTime())
                        .orderByDesc(ScheduleLog::getStartTime)
        );
        List<ScheduleLogDTO> dtoList = ScheduleLogConvert.INSTANCE.toDto(list.getRecords());
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }
}