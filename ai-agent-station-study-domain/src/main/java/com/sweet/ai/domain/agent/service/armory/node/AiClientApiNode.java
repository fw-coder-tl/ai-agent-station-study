package com.sweet.ai.domain.agent.service.armory.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.sweet.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.sweet.ai.domain.agent.model.valobj.AiClientApiVO;
import com.sweet.ai.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.sweet.ai.domain.agent.service.armory.node.factory.DefaultArmoryStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * OpenAI API配置节点
 */
@Slf4j
@Service
public class AiClientApiNode extends AbstractArmorySupport {

    @Resource
    private AiClientToolMcpNode aiClientToolMcpNode;

    @Override
    protected String doApply(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Client 构建，API 构建节点 {}", JSON.toJSONString(armoryCommandEntity));
        // 从上下文获取参数
        List<AiClientApiVO> aiClientApiList = dynamicContext.getValue(AiAgentEnumVO.AI_CLIENT_API.getDataName());

        if (aiClientApiList == null || aiClientApiList.isEmpty()) {
            log.warn("没有需要初始化的ai client api");
            return null;
        }

        for (AiClientApiVO aiClientApiVO : aiClientApiList) {
            // 构建 OpenAiApi
            OpenAiApi openAiApi = OpenAiApi.builder()
                    .baseUrl(aiClientApiVO.getBaseUrl())
                    .apiKey(aiClientApiVO.getApiKey())
                    .completionsPath(aiClientApiVO.getCompletionsPath())
                    .embeddingsPath(aiClientApiVO.getEmbeddingsPath())
                    .build();

            // 注册 OpenAiApi Bean 对象
            registerBean(AiAgentEnumVO.AI_CLIENT_API.getBeanName(aiClientApiVO.getApiId()), OpenAiApi.class, openAiApi);
        }

        return router(armoryCommandEntity,dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientToolMcpNode;
    }

    @Override
    public String beanName(String beanId){
        return  AiAgentEnumVO.AI_CLIENT_API.getBeanName(beanId);
    }

    @Override
    public String dataName(){
        return AiAgentEnumVO.AI_CLIENT_API.getDataName();
    }
}
