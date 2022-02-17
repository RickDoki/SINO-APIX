/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.gateway.repository;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengjiahu
 * @date 2021-07-02 00:31
 * @description
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    /**
     * hash存储的key
     */
    public static final String GATEWAY_ROUTES = "gateway_dynamic_route";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取全部路由信息
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<Object> values = redisTemplate.opsForHash().values(GATEWAY_ROUTES);
        List<RouteDefinition> definitions = values.stream().map(routeDefinition -> {
            RouteDefinition _routeDefinition = JSONUtil
                    .toBean(routeDefinition.toString(), RouteDefinition.class);
            return _routeDefinition;
        }).collect(Collectors.toList());
        return Flux.fromIterable(definitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
//            String str = (String) redisTemplate.opsForHash().get(GATEWAY_ROUTES, routeDefinition.getId());
//            JSONObject jsonObject = JSONObject.parseObject(str);
//            // 同一个服务地址放入同一个路由集合
//            if (null != jsonObject) {
//                JSONObject predicate = jsonObject.getJSONArray("predicates").getJSONObject(0);
//                JSONObject args = predicate.getJSONObject("args");
//                String jsonStr = args.toJSONString();
//                JSONObject newArgs = JSONObject.parseObject(jsonStr);
//                PredicateDefinition predicateDefinition = routeDefinition.getPredicates().get(0);
//                for (String uri : predicateDefinition.getArgs().values()) {
//                    if (!newArgs.containsValue(uri)) {
//                        newArgs.put("_genkey_" + newArgs.size(), uri);
//                    }
//                }
//                predicate.put("args", newArgs);
//                redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
//                        JSONUtil.toJsonStr(jsonObject));
//                return Mono.empty();
//            }
            redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(), JSON.toJSONString(routeDefinition));
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            boolean exist = redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id);
            if (exist) {
                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono
                    .error(new NotFoundException(
                            "route definition is not found, routeId:" + routeId)));
        });
    }

}
