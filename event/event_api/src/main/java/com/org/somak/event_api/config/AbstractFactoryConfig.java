package com.org.somak.event_api.config;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.org.somak.event_api.service.AbstractServiceFactory;

@Configuration
public class AbstractFactoryConfig {

	@Bean
	public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
	    ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
	    bean.setServiceLocatorInterface(AbstractServiceFactory.class);
	    return bean;
	}
	
	@Bean
	public AbstractServiceFactory abstractServiceFactory()
	{
	    return (AbstractServiceFactory) serviceLocatorFactoryBean().getObject();
	}
}
