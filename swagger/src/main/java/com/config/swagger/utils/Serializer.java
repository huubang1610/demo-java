package com.config.swagger.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Serializer {

    public static final SimpleDateFormat SIMPLE_DATE_TIME_ZONE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    @Data
    public static class SmplTimestampSerializer extends JsonSerializer<Timestamp> implements TimePattern<SimpleDateFormat> {
        private SimpleDateFormat simpleDateFormat = SIMPLE_DATE_TIME_ZONE;
        @Override
        public void serialize(Timestamp value, JsonGenerator gen,
                              SerializerProvider arg2) throws IOException, JsonProcessingException {
            String result = value != null  ? simpleDateFormat.format(value) : "";
            gen.writeString(result);
        }

        @Override
        public SimpleDateFormat getPattern(SimpleDateFormat simpleDateFormat) {
            this.simpleDateFormat = simpleDateFormat;
            return this.simpleDateFormat;
        }
    }
    public interface TimePattern<T> {
        SimpleDateFormat getPattern(T t);
    }
}
