package com.epam.esm.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebConfig implements WebMvcConfigurer {

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
}
