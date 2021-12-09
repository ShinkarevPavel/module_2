package com.epam.esm.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@PropertySource("classpath:database.properties")
@EnableJpaRepositories(basePackages = "com.epam.esm")
@EntityScan("com.epam.esm.entity")
public class SpringDataSourceConfiguration {

}
