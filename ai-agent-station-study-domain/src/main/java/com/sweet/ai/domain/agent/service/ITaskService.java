package com.sweet.ai.domain.agent.service;

import com.sweet.ai.domain.agent.model.valobj.AiAgentTaskScheduleVO;

import java.util.List;

public interface ITaskService {

    // 查询所有有效的定时任务
    List<AiAgentTaskScheduleVO> queryAllValidTaskSchedule();

    // 查询所有无效的定时任务
    List<Long> queryAllInvalidTaskScheduleIds();

}
