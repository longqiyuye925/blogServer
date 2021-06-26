package com.example.blogserver.config;

import com.example.blogserver.service.JWTokenEnhancer;
import com.example.blogserver.service.MyUserDetailsService;
import com.example.blogserver.util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/25
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenStore jwtTokenStore;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JWTokenEnhancer jwTokenEnhancer;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("test1")//客户端ID
                .secret(new MyPasswordEncoder().encode("test1111"))//（受信任的客户端需要）客户端机密，如果有的话。
                .authorizedGrantTypes("password", "refresh_token", "authorization_code", "client_credentials")//授权客户端使用的授权类型。默认值为空。
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600)
                .scopes("all")//客户端受限的范围。如果范围未定义或为空（默认），则客户端不受范围限制。
                .autoApprove(true)  // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                .redirectUris("http://www.baidu.com/");// 客户端回调地址

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(jwTokenEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(myUserDetailsService);
    }
}
