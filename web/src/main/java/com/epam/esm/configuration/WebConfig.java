package com.epam.esm.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class WebConfig extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }

    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.addBasenames("ValidationMessages");
        messages.setDefaultEncoding("UTF-8");
        return messages;
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(WebConfig.class);
//    }
}