package com.epam.esm.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class WebConfig implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
//                .json()
//                .modules(new JavaTimeModule()).build()
//                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
//    }

    // Todo Here I can set contextPath. Is it necessary ?
    //  should I all recourses transfer to main resource package ?


    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.addBasenames("error_messages");
        messages.setDefaultEncoding("UTF-8");
        return messages;
    }
}
