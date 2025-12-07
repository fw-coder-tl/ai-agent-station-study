package com.sweet.ai.infrastructure.adapter.repository;

import com.sweet.ai.domain.agent.adapter.repository.IAgentRepository;
import com.sweet.ai.domain.agent.model.valobj.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 仓储服务
 */

@Slf4j
@Repository
public class AgentRepository implements IAgentRepository {
    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public List<AiClientModelVO> AiClientModelVOByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public List<AiClientToolMcpVO> AiClientToolMcpVOByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public List<AiClientSystemPromptVO> AiClientSystemPromptVOByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public Map<String, AiClientSystemPromptVO> queryAiClientSystemPromptMapByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public List<AiClientAdvisorVO> AiClientAdvisorVOByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public List<AiClientVO> AiClientVOByClientIds(List<String> clientIdList) {
        return null;
    }

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByModelIds(List<String> modelIdList) {
        return null;
    }

    @Override
    public List<AiClientModelVO> AiClientModelVOByModelIds(List<String> modelIdList) {
        return null;
    }

    @Override
    public Map<String, AiAgentClientFlowConfigVO> queryAiAgentClientFlowConfig(String aiAgentId) {
        return null;
    }

    @Override
    public AiAgentVO queryAiAgentByAgentId(String aiAgentId) {
        return null;
    }

    @Override
    public List<AiAgentClientFlowConfigVO> queryAiAgentClientsByAgentId(String aiAgentId) {
        return null;
    }

    @Override
    public List<AiAgentTaskScheduleVO> queryAllValidTaskSchedule() {
        return null;
    }

    @Override
    public List<Long> queryAllInvalidTaskScheduleIds() {
        return null;
    }

    @Override
    public void createTagOrder(AiRagOrderVO aiRagOrderVO) {

    }

    @Override
    public List<AiAgentVO> queryAvailableAgents() {
        return null;
    }

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByApiIds(List<String> apiIdList) {
        return null;
    }
}
