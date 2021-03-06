package com.rft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
		.antMatchers("/user/logout")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/user/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/user")
		.hasRole("ADMIN")
		//users
		.antMatchers(HttpMethod.GET,"/users")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/users/?/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/users/?/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/users/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/users/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/users/**")
		.hasRole("ADMIN")
		//jobseeker
		.antMatchers(HttpMethod.POST,"/jobSeekers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/jobSeekers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobSeekers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobSeekers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobSeekers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobSeekers/**")
		.hasRole("ADMIN")
		//jobOfferer
		.antMatchers(HttpMethod.POST,"/jobOfferers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/jobOfferers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobOfferers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobOfferers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobOfferers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobOfferers/**")
		.hasRole("ADMIN")
		//job
		.antMatchers(HttpMethod.POST,"/jobs")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/jobs/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobs")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobs/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobs")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobs/**")
		.hasRole("ADMIN")
		//role
		.antMatchers(HttpMethod.GET,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/roles/**")
		.hasRole("ADMIN")
		//school
		.antMatchers(HttpMethod.POST,"/schools")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/schools/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/schools")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/schools/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/schools")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/schools/**")
		.hasRole("ADMIN")
		//workplace
		.antMatchers(HttpMethod.POST,"/workPlaces")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/workPlaces/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/workPlaces")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/workPlaces/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/workPlaces")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/workPlaces/**")
		.hasRole("ADMIN")
		//useractivation
		.antMatchers(HttpMethod.GET,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/userActivations/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/userActivations/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/userActivations/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/userActivations/**")
		.hasRole("ADMIN")
		//accestokenentity
		.antMatchers(HttpMethod.GET,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		//refreshtokenentity
		.antMatchers(HttpMethod.GET,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		//jobcategory
		.antMatchers(HttpMethod.POST,"/jobCategories")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/jobCategories/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobCategories")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/jobCategories/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobCategories")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/jobCategories/**")
		.hasRole("ADMIN");
		//.and()
		//.rememberMe();
	}

}
