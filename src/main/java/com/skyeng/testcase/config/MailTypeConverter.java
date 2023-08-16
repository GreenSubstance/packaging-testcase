package com.skyeng.testcase.config;

import com.skyeng.testcase.model.MailType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MailTypeConverter implements Converter<String, MailType> {
    @Override
    public MailType convert(String source) {
        if (source.isBlank()) {
            return null;
        }
        try {
            return MailType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
