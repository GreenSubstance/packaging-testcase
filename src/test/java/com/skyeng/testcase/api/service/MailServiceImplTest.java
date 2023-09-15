package com.skyeng.testcase.api.service;

import com.skyeng.testcase.api.dto.MailDto;
import com.skyeng.testcase.api.dto.MailStateDto;
import com.skyeng.testcase.api.exceptions.BadRequestException;
import com.skyeng.testcase.api.repositories.MailRepo;
import com.skyeng.testcase.api.repositories.MailStateRepo;
import com.skyeng.testcase.api.repositories.PostRepo;
import com.skyeng.testcase.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {


    private MailStateRepo mailStateRepo;
    private MailRepo mailRepo;
    private PostRepo postRepo;
    private MailService mailService;

    private MailEntity commonMail;

    @BeforeEach
    void setup() {
        mailStateRepo = Mockito.mock(MailStateRepo.class);
        mailRepo = Mockito.mock(MailRepo.class);
        postRepo = Mockito.mock(PostRepo.class);
        mailService = new MailServiceImpl(mailRepo, mailStateRepo, postRepo);

        commonMail = MailEntity.builder()
                .mailType(MailType.PARCEL)
                .recipientAddress("ad")
                .recipientIndex(111)
                .recipientName("joe")
                .build();
    }

    @Test
    void shouldRegisterMail() {

        MailDto mailDto = MailDto.builder()
                .mailType(commonMail.getMailType())
                .address(commonMail.getRecipientAddress())
                .index(commonMail.getRecipientIndex())
                .name(commonMail.getRecipientName())
                .build();


        MailEntity actualMail = mailService.registerMail(mailDto);

        assertThat(actualMail.getMailType()).isEqualTo(commonMail.getMailType());
        assertThat(actualMail.getRecipientAddress()).isEqualTo(commonMail.getRecipientAddress());
        assertThat(actualMail.getRecipientIndex()).isEqualTo(commonMail.getRecipientIndex());
        assertThat(actualMail.getRecipientName()).isEqualTo(commonMail.getRecipientName());

    }

    @Test
    void shouldGetCurrentStatus() throws BadRequestException {

        Long mailId = 1L;

        MailStateEntity mailState = MailStateEntity.builder()
                .mailEntity(commonMail)
                .postEntity(null)
                .mailStateType(MailStateType.ARRIVED)
                .build();

        when(mailStateRepo.findLastByMailId(mailId)).thenReturn(mailState);

        MailStateDto actualMailStateDto = mailService.getCurrentStatus(mailId);

        assertThat(mailState.getMailStateType()).isEqualTo(actualMailStateDto.getMailStateType());
    }

    @Test
    void shouldGetMailHistory() throws BadRequestException {

        Long mailId = 1L;

        when(mailRepo.findById(mailId)).thenReturn(Optional.ofNullable(commonMail));

        List<MailStateDto> returnedHistory = mailService.getMailHistory(mailId);
        assertThat(returnedHistory).isNotNull();
    }

    @Test
    void shouldChangeMailState() throws BadRequestException {

        Long mailId = 1L;

        MailStateEntity mailState = MailStateEntity.builder()
                .mailEntity(commonMail)
                .postEntity(null)
                .mailStateType(MailStateType.ARRIVED)
                .build();

        PostEntity postEntity = PostEntity.builder()
                .postName("fff")
                .recipientAddress("ddd")
                .mailState(null)
                .build();

        when(mailRepo.findById(mailId)).thenReturn(Optional.ofNullable(commonMail));
        when(postRepo.findById(1L)).thenReturn(Optional.ofNullable(postEntity));
        when(mailStateRepo.saveAndFlush(Mockito.any())).thenReturn(mailState);

        MailStateDto mailStateDto = mailService.changeMailState(1L, MailStateType.ARRIVED, Optional.of(1L));
        assertThat(mailStateDto.getMailStateType()).isEqualTo(MailStateType.ARRIVED);
    }
}