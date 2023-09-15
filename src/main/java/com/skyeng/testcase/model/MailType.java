package com.skyeng.testcase.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MailType {
    LETTER, PACKAGE, PARCEL, POSTCARD;

    @JsonCreator
    public static MailType mailTypeForValue(String value) {
        return MailType.valueOf(value.toUpperCase());
    }
}
