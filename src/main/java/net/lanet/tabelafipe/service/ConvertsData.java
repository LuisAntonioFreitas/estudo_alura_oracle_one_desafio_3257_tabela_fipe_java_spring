package net.lanet.tabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConvertsData implements IConvertsData {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> classModel) {
        try {
            return mapper.readValue(json, classModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> getList(String json, Class<T> classModel) {
        CollectionType listModel = mapper.getTypeFactory()
                .constructCollectionType(List.class, classModel);
        try {
            return mapper.readValue(json, listModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
