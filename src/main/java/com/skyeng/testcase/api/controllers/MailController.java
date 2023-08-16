package com.skyeng.testcase.api.controllers;

import com.skyeng.testcase.api.dto.MailStateDto;
import com.skyeng.testcase.api.exceptions.BadRequestException;
import com.skyeng.testcase.api.service.MailService;
import com.skyeng.testcase.model.MailEntity;
import com.skyeng.testcase.model.MailStateType;
import com.skyeng.testcase.model.MailType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class MailController {

    final MailService mailService;

    public static final String REGISTER_MAIL = "/api/mail";
    public static final String FETCH_MAIL_HISTORY = "/api/mail/history/{mail_id}";
    public static final String GET_MAIL_STATUS = "/api/mail/status/{mail_id}";
    public static final String CHANGE_MAIL_STATE = "api/mail/{mail_id}";

    @PostMapping(REGISTER_MAIL)
    public MailEntity registerMailEntity (@RequestParam(value = "mail_type", required = false) Optional<MailType> mailType,
                                          @RequestParam(value = "recipient_index", required = false) Optional<Integer> index,
                                          @RequestParam(value = "recipient_address", required = false) Optional<String> address,
                                          @RequestParam(value = "recipient_name", required = false) Optional<String> name) {

        if (mailType.isEmpty()) {
            throw new BadRequestException("Invalid type");
        }

        if (index.isEmpty()) {
            throw new BadRequestException("Invalid index");
        }

        if (address.isEmpty()) {
            throw new BadRequestException("Invalid address");
        }

        if (name.isEmpty()) {
            throw new BadRequestException("Invalid name");
        }


        return mailService.registerMail(mailType.get(), index.get(), address.get(), name.get());
    }

    @PostMapping(CHANGE_MAIL_STATE)
    public MailStateDto changeMailState(@PathVariable("mail_id") Long mailId,
                                                  @RequestParam(value = "state", required = false) Optional<MailStateType> mailState,
                                                  @RequestParam(value = "post_id", required = false) Optional<Long> postId) {
        if (mailState.isEmpty()) {
            throw new BadRequestException("State cannot be empty");
        }

        return mailService.changeMailState(mailId, mailState.get(), postId);

    }

    @GetMapping(FETCH_MAIL_HISTORY)
    public List<MailStateDto> getHistory(@PathVariable("mail_id") Long mailId) {
        return mailService.getMailHistory(mailId);
    }

    @GetMapping(GET_MAIL_STATUS)
    public MailStateDto getCurrentStatus(@PathVariable("mail_id") Long mailId) {
        return mailService.getCurrentStatus(mailId);
    }
}
