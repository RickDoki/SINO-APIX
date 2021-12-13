package com.sinosdx.gateway.handler;

/**
 * @author pengjiahu
 * @date 2020/8/4 15:41
 * @description
 */
//@Slf4j
//@RestController
//@RequestMapping("swagger-resources")
//public class SwaggerHandler {
//
//    @Autowired
//    @Qualifier("swaggerResourceConfiguration")
//    private SwaggerResourcesProvider swaggerResources;
//
//    @Autowired(required = false)
//    private SecurityConfiguration securityConfiguration;
//
//    @Autowired(required = false)
//    private UiConfiguration uiConfiguration;
//
//    @GetMapping("configuration/security")
//    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
//        return Mono.just(
//                new ResponseEntity<>(
//                        Optional.ofNullable(securityConfiguration)
//                                .orElse(SecurityConfigurationBuilder.builder().build()),
//                        HttpStatus.OK));
//    }
//
//    @GetMapping("configuration/ui")
//    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
//        return Mono.just(
//                new ResponseEntity<>(
//                        Optional.ofNullable(uiConfiguration)
//                                .orElse(UiConfigurationBuilder.builder().build()),
//                        HttpStatus.OK));
//    }
//
//    @GetMapping
//    public Mono<ResponseEntity> swaggerResources() {
//        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
//    }
//}
