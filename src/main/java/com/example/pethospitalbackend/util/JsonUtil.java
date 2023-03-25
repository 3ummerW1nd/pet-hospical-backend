package com.example.pethospitalbackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static JsonNode parseJson(String jsonNodeString) {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.readTree( jsonNodeString );
        }
        catch( JsonProcessingException e )
        {
            e.printStackTrace();
        }
        return null;
    }
}
