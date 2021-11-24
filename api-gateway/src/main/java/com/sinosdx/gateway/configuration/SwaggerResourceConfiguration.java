package com.sinosdx.gateway.configuration;

/**
 * 整合swagger统一入口
 *
 * @author pengjiahu
 * @date 2020/8/4 15:41
 * @description
 */
//@Slf4j
//@Configuration
//@EnableConfigurationProperties(SwaggerRouteProperties.class)
//@Primary
//public class SwaggerResourceConfiguration implements SwaggerResourcesProvider {
//
//    private static final String API_URI = "/v2/api-docs";
//
//    @Autowired
//    private RouteLocator routeLocator;
//
//    @Autowired
//    private GatewayProperties gatewayProperties;
//
//    @Autowired
//    private SwaggerRouteProperties swaggerRouteProperties;
//
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = Lists.newArrayList();
//        List<String> routes = Lists.newArrayList();
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//        gatewayProperties.getRoutes().stream()
//                .filter(
//                        routeDefinition -> {
//                            SwaggerRouteResource swaggerRouteResource = getSwaggerRouteResource(
//                                    routeDefinition.getId());
//                            return swaggerRouteResource != null;
//                        })
//                .sorted(Comparator.comparing(RouteDefinition::getOrder))
//                .forEach(
//                        routeDefinition ->
//                                routeDefinition.getPredicates().stream()
//                                        .filter(
//                                                predicateDefinition ->
//                                                        ("Path").equalsIgnoreCase(
//                                                                predicateDefinition.getName()))
//                                        .forEach(
//                                                predicateDefinition ->
//                                                        resources.add(
//                                                                swaggerResource(
//                                                                        routeDefinition.getId(),
//                                                                        predicateDefinition
//                                                                                .getArgs()
//                                                                                .get(NameUtils.GENERATED_NAME_PREFIX
//                                                                                        + "0")
//                                                                                .replace("/**",
//                                                                                        API_URI)))));
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location) {
//        SwaggerRouteResource swaggerRouteResource = getSwaggerRouteResource(name);
//        if (swaggerRouteResource == null) {
//            log.warn("not found swaggerRouteResource by name");
//            return new SwaggerResource();
//        }
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(swaggerRouteResource.getName());
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion(swaggerRouteResource.getVersion());
//        return swaggerResource;
//    }
//
//    private SwaggerRouteResource getSwaggerRouteResource(String name) {
//        for (SwaggerRouteResource routeResource : swaggerRouteProperties.getResources()) {
//            if (routeResource.getService().contains(name)) {
//                return routeResource;
//            }
//        }
//        return null;
//    }
//}
