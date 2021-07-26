package br.com.zupacademy.victor.orangetalents06templateproposta.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String toJson(Object obj) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
