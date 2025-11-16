package com.sweet.ai.domain.agent.service.armory.business.data;

import com.sweet.ai.domain.agent.adapter.repository.IAgentRepository;
import com.sweet.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.sweet.ai.domain.agent.model.valobj.AiClientApiVO;
import com.sweet.ai.domain.agent.model.valobj.AiClientVO;
import com.sweet.ai.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.sweet.ai.domain.agent.service.armory.node.factory.DefaultArmoryStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * API 数据加载
 */
@Slf4j
@Service("aiClientApiLoadDataStrategy")
public class AiClientApiLoadDataStrategy implements ILoadDataStrategy {

    @Resource
    private IAgentRepository repository;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) {
        // 获取参数
        List<String> apiIdList = armoryCommandEntity.getCommandIdList();

        CompletableFuture<List<AiClientApiVO>> aiClientApiListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_api) {}", apiIdList);
            return repository.queryAiClientApiVOListByApiIds(apiIdList);
        }, threadPoolExecutor);

        CompletableFuture.allOf(aiClientApiListFuture).thenRun(() -> {
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT_API.getDataName(), aiClientApiListFuture.join());
        }).join();
    }
}
