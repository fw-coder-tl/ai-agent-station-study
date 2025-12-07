package com.sweet.ai.domain.agent;

import com.sweet.ai.domain.agent.model.entity.ExecuteCommandEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

public interface IExecuteStrategy {
    /**
     * 执行命令
     * @param executeCommandEntity 执行命令实体
     * @param responseBodyEmitter 响应体发射器
     */
    void execute (ExecuteCommandEntity executeCommandEntity, ResponseBodyEmitter responseBodyEmitter) throws Exception;

}
