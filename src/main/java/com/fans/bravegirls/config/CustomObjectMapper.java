package com.fans.bravegirls.config;

import com.fasterxml.jackson.databind.ObjectMapper;



public class CustomObjectMapper extends ObjectMapper {



    private static final long serialVersionUID = 1L;



    public CustomObjectMapper(){

        getSerializerProvider().setNullValueSerializer(new NullToEmptyStringSerializer());

    }



}


