package com.sweet.ai.domain.agent.service.dispatch;

import com.sweet.ai.domain.agent.adapter.repository.IAgentRepository;
import com.sweet.ai.domain.agent.model.entity.ExecuteCommandEntity;
import com.sweet.ai.domain.agent.model.valobj.AiAgentVO;
import com.sweet.ai.domain.agent.service.IAgentDispatchService;
import com.sweet.ai.domain.agent.service.IExecuteStrategy;
import com.sweet.ai.types.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class AgentDispatchDispatchService implements IAgentDispatchService {

    @Resource
    private IAgentRepository repository;

    @Resource
    private Map<String,IExecuteStrategy> executeStrategyMap;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void dispatch(ExecuteCommandEntity executeCommandEntity, ResponseBodyEmitter responseBodyEmitter) throws Exception {
        AiAgentVO aiAgentVO = repository.queryAiAgentByAgentId(executeCommandEntity.getAiAgentId());

        // 获取执行策略
        String strategy = aiAgentVO.getStrategy();
        // 获取执行器
        IExecuteStrategy executeStrategy = executeStrategyMap.get(strategy);
        if (executeStrategy == null) {
            throw new BizException("不存在的执行策略类型 strategy:"+strategy);
        }

        // 异步执行AutoAgent
        threadPoolExecutor.execute(()->{
            try {
                executeStrategy.execute(executeCommandEntity,responseBodyEmitter);
            } catch (Exception e) {
                log.error("AutoAgent执行异常：{}",e.getMessage(),e);
                try {
                    responseBodyEmitter.send("执行异常："+e.getMessage());
                } catch (IOException ex) {
                    log.error("发送异常信息失败：{}",ex.getMessage(),ex);
                }
            } finally {
                try {
                    responseBodyEmitter.complete();
                } catch (Exception e) {
                    log.error("完成流式输出失败：{}", e.getMessage(), e);
                }
            }
        });
    }
}
