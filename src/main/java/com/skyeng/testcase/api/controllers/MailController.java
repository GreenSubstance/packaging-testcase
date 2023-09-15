package com.skyeng.testcase.api.controllers;

import com.skyeng.testcase.api.dto.MailDto;
import com.skyeng.testcase.api.dto.MailStateDto;
import com.skyeng.testcase.api.exceptions.BadRequestException;
import com.skyeng.testcase.api.service.MailService;
import com.skyeng.testcase.model.MailEntity;
import com.skyeng.testcase.model.MailStateType;
import com.skyeng.testcase.model.MailType;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public MailEntity registerMailEntity (@Valid @RequestBody MailDto mailDto) {
        return mailService.registerMail(mailDto);
    }

    @PostMapping(CHANGE_MAIL_STATE)
    public MailStateDto changeMailState(@PathVariable("mail_id") Long mailId,
                                        @RequestParam(value = "state") MailStateType mailState,
                                        @RequestParam(value = "post_id", required = false) Optional<Long> postId){

        try {
            return mailService.changeMailState(mailId, mailState, postId);
        }
        catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect mail id or post id", e);
        }
    }

    @GetMapping(FETCH_MAIL_HISTORY)
    public List<MailStateDto> getHistory(@PathVariable("mail_id") Long mailId) {
        try {
            return mailService.getMailHistory(mailId);
        }
        catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect id", e);
        }
    }

    @GetMapping(GET_MAIL_STATUS)
    public MailStateDto getCurrentStatus(@PathVariable("mail_id") Long mailId) {
        try {
            return mailService.getCurrentStatus(mailId);
        }
        catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect id", e);
        }
    }
}
