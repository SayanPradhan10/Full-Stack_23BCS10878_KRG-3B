package com.resumebuilder.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class for Bean Validation and message sources.
 * Provides centralized validation configuration and custom error messages.
 */
@Configuration
public class ValidationConfig {
    
    /**
     * Configure message source for validation error messages.
     * 
     * @return MessageSource bean for internationalization and custom messages
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/validation");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(300); // Cache for 5 minutes
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
    
    /**
     * Configure the validator factory to use custom message source.
     * 
     * @return LocalValidatorFactoryBean with custom message source
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}