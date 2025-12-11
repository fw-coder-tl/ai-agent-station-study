package com.sweet.ai.domain.agent.service;

import com.sweet.ai.domain.agent.model.entity.ExecuteCommandEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * Agent 调度服务
 */
public interface IAgentDispatchService {

    void dispatch (ExecuteCommandEntity executeCommandEntity, ResponseBodyEmitter responseBodyEmitter) throws Exception;

}
