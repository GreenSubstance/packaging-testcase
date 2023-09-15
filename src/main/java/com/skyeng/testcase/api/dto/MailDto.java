package com.skyeng.testcase.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skyeng.testcase.model.MailType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailDto {

    @NotNull
    @JsonProperty("mail_type")
    MailType mailType;

    @NotNull
    @JsonProperty("recipient_index")
    Integer index;

    @NotEmpty
    @JsonProperty("recipient_address")
    String address;

    @NotEmpty
    @JsonProperty("recipient_name")
    String name;

    @Override
    public String toString() {
        return "MailDto{" +
                "mailType='" + mailType + '\'' +
                ", index=" + index +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
