package com.rft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${resource_id}")
	private String RESOURCE_ID;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
	}
	
	// elejére raktam cors().and()-et kell a corshoz (obviously)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.authorizeRequests()
		.antMatchers("/admin/*")
		.hasRole("ADMIN")
		.antMatchers("/hello/*")
		.authenticated()
		.antMatchers("/helloka/findall")
		.authenticated()
		.antMatchers("/find/*")
		.authenticated()
		.antMatchers("/profile/test")
		.authenticated()
		.antMatchers(HttpMethod.GET,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/upload")
		.hasRole("ADMIN");
	}

}
