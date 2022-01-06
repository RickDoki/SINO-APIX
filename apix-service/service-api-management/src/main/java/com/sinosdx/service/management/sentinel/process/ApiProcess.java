package com.sinosdx.service.management.sentinel.process;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.sinosdx.service.management.sentinel.entity.ApiDefinitionEntity;
import com.sinosdx.service.management.sentinel.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenjian
 * @create 2021-12-30 16:44
 * @Description 处理自定义api分组信息
 */
@Service
public class ApiProcess {

    @Autowired
    @Qualifier("nacosConfigService")
    ConfigService configService;

    @Autowired
    private ApiRepository repository;

    @Autowired
    @Qualifier("apiEncoder")
    private Converter<List<ApiDefinitionEntity>, String> encoderConverter;

    @Autowired
    @Qualifier("apiDecoder")
    private Converter<String, List<ApiDefinitionEntity>> decoderConverter;

    private final String dataId = "sentinel-gw-api";

    private final String groupId = "SENTINEL_GROUP";

    /**
     * 初始化 api 分组信息 到内存
     */
    public void initApi(){
        apiProvider();
    }

    /**
     * 保存 api分组 到内存，并保存到 nacos
     * @param apiGroupList
     * @return
     */
    public String save(List<ApiDefinitionEntity> apiGroupList) {
        String appId = apiGroupList.get(0).getApp();
        repository.delOldApi(appId);
        String s = repository.saveApiToMem(apiGroupList);
        if(s.equals("ok")){
            apiPublisher(repository.getAllApi());
        }
        return s;
    }

    /**
     * 发布 api信息 到 nacos
     * @param listEntity
     */
    private void apiPublisher(List<ApiDefinitionEntity> listEntity){
        try {
            configService.publishConfig(dataId, groupId, encoderConverter.convert(listEntity));
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 nacos 拉取 api信息
     * @return
     */
    private void apiProvider(){
        String listEntity = "";
        try {
            listEntity = configService.getConfig(dataId,groupId, 3000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        if (StringUtil.isEmpty(listEntity)) {
            return;
        }
        List<ApiDefinitionEntity> convert = decoderConverter.convert(listEntity);
        repository.saveApiFromNacos(convert);
    }

}
