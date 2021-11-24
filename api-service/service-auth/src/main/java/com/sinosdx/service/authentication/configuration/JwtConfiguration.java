package com.sinosdx.service.authentication.configuration;

import com.sinosdx.service.authentication.dao.entity.BaseUser;
import com.sinosdx.service.authentication.security.CommonUserConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/11/27
 */
@Configuration
public class JwtConfiguration {

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                if (null != authentication.getUserAuthentication()) {
                    BaseUser baseUser = (BaseUser) authentication.getUserAuthentication().getPrincipal();

                    final Map<String, Object> addToJwtInformation = new HashMap<>();
                    addToJwtInformation.put("userId", baseUser.getId());
                    addToJwtInformation.put("username", baseUser.getUsername());
                    addToJwtInformation.put("mobile", baseUser.getMobile());
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addToJwtInformation);
                }

                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
                return enhancedToken;
            }
        };
        jwtAccessTokenConverter.setSigningKey("sinosdx");

        //        KeyPair keyPair = new KeyStoreKeyFactory(
        //                new ClassPathResource("keystore.jks"), "secret".toCharArray())
        //                .getKeyPair("test");
        //        jwtAccessTokenConverter.setKeyPair(keyPair);

        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter());
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        return jwtAccessTokenConverter;
    }

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        return new CommonUserConverter();
    }
}
