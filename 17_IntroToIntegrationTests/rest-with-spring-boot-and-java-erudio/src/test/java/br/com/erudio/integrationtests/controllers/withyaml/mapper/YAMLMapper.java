package br.com.erudio.integrationtests.controllers.withyaml.mapper;

import br.com.erudio.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class YAMLMapper implements io.restassured.mapper.ObjectMapper {

    private ObjectMapper objectMapper;

    public YAMLMapper() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public Object deserialize(ObjectMapperDeserializationContext context) {
        var content = context.getDataToDeserialize().asString();
        Class<?> type = (Class<?>) context.getType();
        try {
            return objectMapper.readValue(content, type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing YML", e);
        }
    }

    @Override
    public Object serialize(ObjectMapperSerializationContext context) {
        try {
            return objectMapper.writeValueAsString(context.getObjectToSerialize());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing object to YML", e);
        }
    }
}