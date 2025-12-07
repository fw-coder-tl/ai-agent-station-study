package com.sweet.ai.domain.agent.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.sweet.ai.domain.agent.IArmoryService;
import com.sweet.ai.domain.agent.adapter.repository.IAgentRepository;
import com.sweet.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.sweet.ai.domain.agent.model.valobj.AiAgentClientFlowConfigVO;
import com.sweet.ai.domain.agent.model.valobj.AiAgentVO;
import com.sweet.ai.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.sweet.ai.domain.agent.service.armory.node.factory.DefaultArmoryStrategyFactory;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 装配服务
 */
public class ArmoryService implements IArmoryService {

    @Resource
    private IAgentRepository repository;
    @Resource
    private DefaultArmoryStrategyFactory defaultArmoryStrategyFactory;


    @Override
    public List<AiAgentVO> acceptArmoryAllAvailableAgents() {
        List<AiAgentVO> aiAgentVOS = repository.queryAvailableAgents();
        for(AiAgentVO aiAgentVO:aiAgentVOS){
            String agentId = aiAgentVO.getAgentId();
            acceptArmoryAgent(agentId);
        }
        return aiAgentVOS;
    }

    @Override
    public void acceptArmoryAgent(String agentId) {
        List<AiAgentClientFlowConfigVO> aiAgentClientFlowConfigVOS = repository.queryAiAgentClientsByAgentId(agentId);
        if(aiAgentClientFlowConfigVOS.isEmpty()) return;

        // 获取客户端集合
        List<String> commandIdList = aiAgentClientFlowConfigVOS.stream()
                .map(AiAgentClientFlowConfigVO::getClientId)
                .collect(Collectors.toList());

        StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryCommandEntityDynamicContextStringStrategyHandler = defaultArmoryStrategyFactory.armoryStrategyHandler();
        try {
            armoryCommandEntityDynamicContextStringStrategyHandler.apply(
                    ArmoryCommandEntity.builder()
                            .commandIdList(commandIdList)
                            .commandType(AiAgentEnumVO.AI_CLIENT.getCode()).build(),
                    new DefaultArmoryStrategyFactory.DynamicContext()
            );
        } catch (Exception e) {
            throw new RuntimeException("装配智能体失败", e);
        }

    }

    @Override
    public List<AiAgentVO> queryAvailableAgents() {
        return repository.queryAvailableAgents();
    }

    @Override
    public void acceptArmoryAgentClientModelApi(String apiId) {
        try {
            StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryStrategyHandler =
                    defaultArmoryStrategyFactory.armoryStrategyHandler();

            armoryStrategyHandler.apply(
                    ArmoryCommandEntity.builder()
                            .commandType(AiAgentEnumVO.AI_CLIENT_API.getCode())
                            .commandIdList(Collections.singletonList(apiId))
                            .build(),
                    new DefaultArmoryStrategyFactory.DynamicContext());
        } catch (Exception e) {
            throw new RuntimeException("装配智能体失败", e);
        }
    }
}
