package com.skyeng.testcase.api.service;

import com.skyeng.testcase.api.dto.MailDto;
import com.skyeng.testcase.api.dto.MailStateDto;
import com.skyeng.testcase.api.exceptions.BadRequestException;
import com.skyeng.testcase.model.MailEntity;
import com.skyeng.testcase.model.MailStateEntity;
import com.skyeng.testcase.model.MailStateType;
import com.skyeng.testcase.model.MailType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MailService {
    MailEntity registerMail(MailDto mailDto);
    MailStateDto getCurrentStatus(Long mail_id) throws BadRequestException;
    List<MailStateDto> getMailHistory(Long mailId) throws BadRequestException;

    MailStateDto changeMailState(Long mailId, MailStateType newState, Optional<Long> postId) throws BadRequestException;
}
