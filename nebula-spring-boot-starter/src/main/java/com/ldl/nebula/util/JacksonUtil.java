package com.ldl.nebula.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author lideliang
 * @date 2023/02/26 15:10
 */
public class JacksonUtil {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);
    private static final ObjectMapper MAPPER = createObjectMapper();
    private JacksonUtil() {}

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(df);
        return objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }

    public static String compressByView(Object o, Class<?> clazz) {
        if (o == null) {
            return null;
        }
        try {
            return MAPPER.writerWithView(clazz).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static JsonNode readTree(String json) {
        if (json == null || json.length() == 0) {return null;}
        try {
            return MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static JsonNode readTree(Object obj) {
        if (obj == null) {return null;}
        return MAPPER.valueToTree(obj);
    }

    public static <T> T extractByType(JsonNode jsonNode, TypeReference<T> tp) {
        if (jsonNode == null) {return null;}
        try {
            return MAPPER.readerFor(tp).readValue(jsonNode);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T extractByType(String json, TypeReference<T> tp) {
        if (json == null || json.length() == 0) {return null;}
        try {
            return MAPPER.readValue(json, tp);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T extractByType(JsonNode jsonNode, Type type) {
        if (jsonNode == null) {return null;}
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructType(type);
            return MAPPER.readerFor(javaType).readValue(jsonNode);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T extractByType(String json, Type type) {
        JavaType javaType = MAPPER.getTypeFactory().constructType(type);
        return extractByType(json, javaType);
    }

    public static <T> T extractByType(String json, JavaType type) {
        if (json == null || json.length() == 0) {return null;}
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T extractByClass(JsonNode jsonNode, Class<T> clazz) {
        if (jsonNode == null) {return null;}
        try {
            return MAPPER.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T extractByClass(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {return null;}
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T extractByClass(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {return null;}
        return MAPPER.convertValue(map, clazz);
    }

    public static <T> String compress(T object) {
        if (object == null) {return null;}
        try {
            return MAPPER.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}