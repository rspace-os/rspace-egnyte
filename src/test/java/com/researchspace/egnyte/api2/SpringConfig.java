package com.researchspace.egnyte.api2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class SpringConfig {
	
	@Bean("validator")
	LocalValidatorFactoryBean localValidatorFactoryBean () {
		return new LocalValidatorFactoryBean();
	}

}
