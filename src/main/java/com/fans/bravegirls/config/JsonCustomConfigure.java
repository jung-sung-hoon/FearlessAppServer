package com.fans.bravegirls.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;

//@Configuration
public class JsonCustomConfigure {

    @Bean
    public MappingJackson2HttpMessageConverter converter() {

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        CustomObjectMapper mapper = new CustomObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        converter.setObjectMapper(mapper);

        return converter;

    }

}







