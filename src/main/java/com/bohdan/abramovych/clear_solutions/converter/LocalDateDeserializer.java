package com.bohdan.abramovych.clear_solutions.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("[dd][d][/][.][-][:][MM][M][/][.][-][:][yyyy][yy]");

    @Override
    @SneakyThrows
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        String dateStr = jsonParser.getText();
        try {
            return LocalDate.parse(dateStr, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new RuntimeException("Error with deserialize date: " + e.getMessage());
        }
    }
}
