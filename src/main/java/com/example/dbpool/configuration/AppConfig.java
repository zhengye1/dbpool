package com.example.dbpool.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class AppConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        // enables wrapping for root elements
        builder.featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE);
        return builder;
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
