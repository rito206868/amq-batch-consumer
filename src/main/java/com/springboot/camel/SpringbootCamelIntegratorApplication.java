package com.springboot.camel;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SpringbootCamelIntegratorApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootCamelIntegratorApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootCamelIntegratorApplication.class, args);
	}
	
	@Bean
	public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception{
		SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
		camelContext.addRoutes(new CamelServiceRoute());
		return camelContext;
		
	}
}
