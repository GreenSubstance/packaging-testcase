package com.skyeng.testcase.api.service;

import com.skyeng.testcase.api.dto.MailStateDto;
import com.skyeng.testcase.model.MailEntity;
import com.skyeng.testcase.model.MailStateEntity;
import com.skyeng.testcase.model.MailStateType;
import com.skyeng.testcase.model.MailType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MailService {
    MailEntity registerMail(MailType mailType, int recipientIndex, String recipientAddress, String recipientName);
    MailStateDto getCurrentStatus(Long mail_id);
    List<MailStateDto> getMailHistory(Long mailId);

    MailStateDto changeMailState(Long mailId, MailStateType newState, Optional<Long> postId);
}
