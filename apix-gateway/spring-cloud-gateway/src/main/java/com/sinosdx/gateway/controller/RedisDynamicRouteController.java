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
package com.sinosdx.gateway.controller;

import com.sinosdx.gateway.impl.RedisDynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengjiahu
 * @date 2021-07-02 00:31
 * @description
 */
@RestController
@RequestMapping("gateway/route")
public class RedisDynamicRouteController {

    @Autowired
    private RedisDynamicRouteService redisDynamicRouteService;

    @PostMapping
    public Boolean create(@RequestBody RouteDefinition entity) {
        return redisDynamicRouteService.add(entity);
    }

    @PutMapping()
    public Boolean update(@RequestBody RouteDefinition entity) {
        return redisDynamicRouteService.update(entity);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        return redisDynamicRouteService.delete(id);
    }

    @GetMapping
    public Flux<RouteDefinition> findAll() {
        return redisDynamicRouteService.findAll();
    }

}
