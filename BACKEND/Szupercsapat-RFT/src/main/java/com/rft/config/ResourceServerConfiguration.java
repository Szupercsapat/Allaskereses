package com.rft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${resource_id}")
	private String RESOURCE_ID;

	
	@Value("${spring.datasource.driverClassName}")
    private String oauthClass;

    @Value("${spring.datasource.url}")
    private String oauthUrl;

    //https://stackoverflow.com/questions/36904178/how-to-persist-oauth-access-tokens-in-spring-security-jdbc/37084761
    @Bean
    public TokenStore tokenStore() {
        DataSource tokenDataSource = DataSourceBuilder.create()
                        .driverClassName(oauthClass)
                        .username("rft_user")
                        .password("admin12345678")
                        .url(oauthUrl)
                        .build();
        return new JdbcTokenStore(tokenDataSource);
    }
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
		//.tokenStore(tokenStore());
	}
	
	// elejére raktam cors().and()-et kell a corshoz (obviously)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.authorizeRequests()
		.antMatchers("/admin/*")
		.hasRole("ADMIN")
		.antMatchers("/profile/*")
		.authenticated()
		.antMatchers("/user/logout")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/user/*")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/user")
		.hasRole("ADMIN");
		//.and()
		//.rememberMe();
	}

}
