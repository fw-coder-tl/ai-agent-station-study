package com.sweet.ai.domain.agent.service.task;

import com.sweet.ai.domain.agent.adapter.repository.IAgentRepository;
import com.sweet.ai.domain.agent.model.valobj.AiAgentTaskScheduleVO;
import com.sweet.ai.domain.agent.service.ITaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
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
