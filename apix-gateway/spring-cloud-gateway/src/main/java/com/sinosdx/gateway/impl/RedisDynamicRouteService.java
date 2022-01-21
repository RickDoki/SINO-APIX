package com.sinosdx.gateway.impl;

import com.alibaba.fastjson.JSON;
import com.sinosdx.common.toolkit.common.LogUtil;
import com.sinosdx.gateway.repository.RedisRouteDefinitionRepository;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengjiahu
 * @date 2021-07-02 00:31
 * @description
 */
@Slf4j
@Service
public class RedisDynamicRouteService implements ApplicationEventPublisherAware {

    @Resource
    private RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 增加路由
     *
     * @param routeDefinition
     * @return
     */
    public Boolean add(RouteDefinition routeDefinition) {
        LogUtil.debug(log, "RedisDynamicRouteService add param:{}", JSON.toJSON(routeDefinition));
        redisRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * 更新
     *
     * @param routeDefinition
     * @return
     */
    public Boolean update(RouteDefinition routeDefinition) {
        LogUtil.debug(log, "RedisDynamicRouteService update param:{}", JSON.toJSON(routeDefinition));
        redisRouteDefinitionRepository.delete(Mono.just(routeDefinition.getId()));
        redisRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public Mono<ResponseEntity<Object>> delete(String id) {
        LogUtil.debug(log, "RedisDynamicRouteService delete param:{}", id);
        return redisRouteDefinitionRepository.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException,
                        t -> Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 获取全部路由信息
     */
    public Flux<RouteDefinition> findAll() {
        return redisRouteDefinitionRepository.getRouteDefinitions();
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
