package com.benz.oauth2.api.config;


import com.benz.oauth2.api.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

         private AuthenticationManager authenticationManager;
         private PasswordEncoder passwordEncoder;
         private DataSource dataSource;
         private SecurityProperties securityProperties;
         private TokenStore tokenStore;
         private UserDetailsService userDetailsService;

         public AuthorizationServerConfig(AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder,
                                           DataSource dataSource,SecurityProperties securityProperties,UserDetailsService userDetailsService)
         {
             this.authenticationManager=authenticationManager;
             this.passwordEncoder=passwordEncoder;
             this.dataSource=dataSource;
             this.securityProperties=securityProperties;
             this.userDetailsService=userDetailsService;
         }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
         security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients.inMemory().withClient("web")
                .secret(passwordEncoder.encode("wso2")).scopes("READ","WRITE")
                .authorizedGrantTypes("password","authorization_code","refresh_token","authorized_grant_type");*/
       clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
               .accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailsService)
                .tokenStore(tokenStore());
    }



    @Bean
    public TokenStore tokenStore()
    {
        if(tokenStore==null)
            tokenStore=new JwtTokenStore(jwtAccessTokenConverter());

        return tokenStore;
    }

    @Bean
    public DefaultTokenServices defaultTokenServices(TokenStore tokenStore,ClientDetailsService clientDetailsService)
    {
       DefaultTokenServices tokenServices=new DefaultTokenServices();
       tokenServices.setSupportRefreshToken(true);
       tokenServices.setTokenStore(tokenStore);
       tokenServices.setClientDetailsService(clientDetailsService);
       tokenServices.setAuthenticationManager(authenticationManager);
       return tokenServices;

    }


    private JwtAccessTokenConverter jwtAccessTokenConverter() {

        SecurityProperties.JwtProperties jwtProperties=securityProperties.getJwt();

        KeyPair keyPair=keyPair(jwtProperties,keyStoreKeyFactory(jwtProperties));

        JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair);
        return converter;
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties,KeyStoreKeyFactory keyStoreKeyFactory)
    {
       return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(),jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties)
    {
      return new KeyStoreKeyFactory(jwtProperties.getKeyStore(),jwtProperties.getKeyStorePassword().toCharArray());
    }

}
