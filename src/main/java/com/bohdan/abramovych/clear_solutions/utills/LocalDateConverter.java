package com.bohdan.abramovych.clear_solutions.service;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.bohdan.abramovych.clear_solutions.utills.LocalDateDeserializer.DATE_TIME_FORMATTER;

@Service
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@NonNull String source) {
        return LocalDate.parse(source, DATE_TIME_FORMATTER);
    }
}