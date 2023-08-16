package com.skyeng.testcase.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skyeng.testcase.model.MailStateType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailStateDto {

    @JsonProperty("state")
    MailStateType mailStateType;

    @JsonProperty("created_at")
    Instant createdAt;

    @JsonProperty("post")
    String postName;
}
