package com.epam.esm.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Locale;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
public class WebConfig {

    @Bean
    @Primary
    public MessageSource messageResource() {
        Locale.setDefault(Locale.US);
        ResourceBundleMessageSource messageResource = new ResourceBundleMessageSource();
        messageResource.setBasename("lang");
        messageResource.setDefaultEncoding("UTF-8");
        messageResource.setFallbackToSystemLocale(false);

        return messageResource;
    }
}
