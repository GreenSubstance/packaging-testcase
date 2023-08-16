package com.skyeng.testcase.config;

import com.skyeng.testcase.model.MailStateType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MailStateConverter implements Converter<String, MailStateType> {
    @Override
    public MailStateType convert(String source) {
        if (source.isBlank()) {
            return null;
        }
        try {
            return MailStateType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
