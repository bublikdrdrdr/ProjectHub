package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * Created by Bublik on 23-Dec-17.
 */
@Configuration
@EnableAuthorizationServer
public class SecurityConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String CLIENT_USER = "client";
    public static final String CLIENT_ANONYMOUS = "anonymous";
    public static final String CLIENT_ADMIN = "admin";
    public static final String ROLE_PREFIX = "role_";

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");//???
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory().withClient(CLIENT_USER)
                .authorizedGrantTypes(authorizedGrantTypes)
                .authorities(ROLE_PREFIX+CLIENT_USER).scopes("read", "write", "trust")
                .resourceIds("oauth2-resource").accessTokenValiditySeconds(60).refreshTokenValiditySeconds(90*24*60*60).and()

                .withClient(CLIENT_ANONYMOUS).authorizedGrantTypes("none")
                .authorities(ROLE_PREFIX+CLIENT_ANONYMOUS).scopes("read").resourceIds("oauth2-resource").and().

                withClient("my-client-with-secret")
                .authorizedGrantTypes("client_credentials", "password").authorities("ROLE_CLIENT").scopes("read")
                .resourceIds("oauth2-resource").secret("secret");

        clients.inMemory()
                .withClient(clientId)
                .authorizedGrantTypes(authorizedGrantTypes)
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .secret("P@ssw0rd")
                .accessTokenValiditySeconds(accessTokenTime).
                refreshTokenValiditySeconds(refreshTokenTime);
                */
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
    }
}
