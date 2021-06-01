package com.fans.bravegirls.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		//.apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
	
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
	            "NEO REST API ", //title
	            "스프링부트 샘플 프로젝트", //description
	            "v2", //version
	            "서비스 약관 URL", //termsOfServiceUrl
	            "linked2ev", //contactName
	            "License", //license
	            "13.209.32.248:19876/"); //licenseUrl
    }
	
}
