package com.example.pethospitalbackend.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.persistence.AttributeConverter;

public class JsonNodeConverter implements AttributeConverter<JsonNode, String>
{
    private static final Logger logger = LoggerFactory.getLogger( JsonNodeConverter.class );

    /**
     * @param jsonNode
     * @return
     */
    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode)
    {
        if( jsonNode == null)
        {
            logger.warn( "jsonNode input is null, returning null" );
            return null;
        }

        String jsonNodeString = jsonNode.toPrettyString();
        return jsonNodeString;
    }

    /**
     * @param jsonNodeString
     * @return
     */
    @Override
    public JsonNode convertToEntityAttribute(String jsonNodeString) {

        if ( StringUtils.isEmpty(jsonNodeString) )
        {
            logger.warn( "jsonNodeString input is empty, returning null" );
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.readTree( jsonNodeString );
        }
        catch( JsonProcessingException e )
        {
            logger.error( "Error parsing jsonNodeString", e );
        }
        return null;
    }

}