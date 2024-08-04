package com.tinqinacademy.hotel.rest.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Java 8 date/time type `java.time.LocalDate` not supported by default
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS); // Jackson will handle empty beans by serializing them to an empty JSON object ( {} ) instead of throwing an exception
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // better readability of dates
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // exclude null fields from JSON
        return mapper;
    }
}
