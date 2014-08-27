package com.rgsinfotech.simpleapp.config;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@ComponentScan(basePackages = { "com.rgsinfotech.simpleapp.controller",
		"com.rgsinfotech.simpleapp.service" })
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories("com.rgsinfotech.simpleapp.repository")
@PropertySource(name = "application", value = {
		"classpath:database.properties", "classpath:messagesource.properties"})
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    private static final String PROP_NAME_MESSAGESOURCE_BASE = "messagesource.base";
    private static final String PROP_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE = "messagesource.use_code_as_default_message";
	
	@Resource
	private Environment environment;

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasename(environment
				.getRequiredProperty(PROP_NAME_MESSAGESOURCE_BASE));
		messageSource
				.setUseCodeAsDefaultMessage(Boolean.parseBoolean(environment
						.getRequiredProperty(PROP_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE)));

		return messageSource;
	}

	@Override
	public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix(VIEW_RESOLVER_PREFIX);
		resolver.setSuffix(VIEW_RESOLVER_SUFFIX);

		return resolver;
	}
//    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix(VIEW_RESOLVER_PREFIX);
//        resolver.setSuffix(VIEW_RESOLVER_SUFFIX);
//        return resolver;
//    }
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
	}
	
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}