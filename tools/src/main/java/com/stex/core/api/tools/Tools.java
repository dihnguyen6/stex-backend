package com.stex.core.api.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tools.class);

    public static String toString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(String.valueOf(e));
        }
        return jsonString;
    }


}
