package com.sweet.ai.domain.agent.service.task;

import com.sweet.ai.domain.agent.adapter.repository.IAgentRepository;
import com.sweet.ai.domain.agent.model.valobj.AiAgentTaskScheduleVO;
import com.sweet.ai.domain.agent.service.ITaskService;

import javax.annotation.Resource;
import java.util.List;

public class AiAgentTaskService implements ITaskService {

    @Resource
    private IAgentRepository repository;

    @Override
    public List<AiAgentTaskScheduleVO> queryAllValidTaskSchedule() {
        return repository.queryAllValidTaskSchedule();
    }

    @Override
    public List<Long> queryAllInvalidTaskScheduleIds() {
        return repository.queryAllInvalidTaskScheduleIds();
    }
}
