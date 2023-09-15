package com.skyeng.testcase.api.service;


import com.skyeng.testcase.api.dto.MailDto;
import com.skyeng.testcase.api.dto.MailStateDto;
import com.skyeng.testcase.api.exceptions.BadRequestException;
import com.skyeng.testcase.api.repositories.MailRepo;
import com.skyeng.testcase.api.repositories.MailStateRepo;
import com.skyeng.testcase.api.repositories.PostRepo;
import com.skyeng.testcase.model.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class MailServiceImpl implements MailService {

    final MailRepo mailRepo;
    final MailStateRepo mailStateRepo;

    final PostRepo postRepo;

    @Override
    @Transactional
    public MailEntity registerMail(MailDto mailDto) {

        MailEntity mail = MailEntity.builder()
                .mailType(mailDto.getMailType())
                .recipientAddress(mailDto.getAddress())
                .recipientIndex(mailDto.getIndex())
                .recipientName(mailDto.getName())
                .build();

        mailRepo.saveAndFlush(mail);

        MailStateEntity mailStateEntity = MailStateEntity.builder()
                .mailEntity(mail)
                .mailStateType(MailStateType.REGISTERED)
                .build();

        mailStateRepo.saveAndFlush(mailStateEntity);
        return mail;
    }

    @Override
    public MailStateDto getCurrentStatus(Long mailId) throws BadRequestException {

        MailStateEntity mailStateEntity = mailStateRepo.findLastByMailId(mailId);
        if (mailStateEntity == null) throw new BadRequestException("Invalid mail Id");

        return MailStateDto.builder()
                .mailStateType(mailStateEntity.getMailStateType())
                .createdAt(mailStateEntity.getCreatedAt())
                .postName(mailStateEntity.getPostEntity() != null ? mailStateEntity.getPostEntity().getPostName() : "")
                .build();
    }

    @Override
    public List<MailStateDto> getMailHistory(Long mailId) throws BadRequestException {
        MailEntity mail = mailRepo
                .findById(mailId)
                .orElseThrow(() -> new BadRequestException("Invalid mail id"));
        return mail
                .getMailStates()
                .stream()
                .map(mailStateEntity -> MailStateDto.builder()
                        .mailStateType(mailStateEntity.getMailStateType())
                        .postName(mailStateEntity.getPostEntity() != null ? mailStateEntity.getPostEntity().getPostName() : "")
                        .createdAt(mailStateEntity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MailStateDto changeMailState (Long mailId, MailStateType newState, Optional<Long> postId) throws BadRequestException {

        PostEntity postEntity = postId.isPresent()
                ? postRepo
                    .findById(postId.get())
                    .orElseThrow(() -> new BadRequestException("Invalid post id"))
                : null;

        MailStateEntity mailState = MailStateEntity.builder()
                .mailEntity(mailRepo
                        .findById(mailId)
                        .orElseThrow(() -> new BadRequestException("Invalid mail id")))
                .mailStateType(newState)
                .postEntity(postEntity)
                .build();

        MailStateEntity savedMailState = mailStateRepo.saveAndFlush(mailState);
        return MailStateDto.builder()
                .mailStateType(savedMailState.getMailStateType())
                .postName(savedMailState.getPostEntity() != null ? savedMailState.getPostEntity().getPostName() : "")
                .createdAt(savedMailState.getCreatedAt())
                .build();
    }
}
