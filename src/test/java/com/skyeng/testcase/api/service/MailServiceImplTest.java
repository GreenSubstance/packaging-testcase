package com.skyeng.testcase.api.service;

import com.skyeng.testcase.api.dto.MailStateDto;
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

    @BeforeEach
    void setup() {
        mailStateRepo = Mockito.mock(MailStateRepo.class);
        mailRepo = Mockito.mock(MailRepo.class);
        postRepo = Mockito.mock(PostRepo.class);
        mailService = new MailServiceImpl(mailRepo, mailStateRepo, postRepo);
    }

    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldRegisterMail() {


        MailEntity expectedMail = MailEntity.builder()
                .mailType(MailType.PARCEL)
                .recipientAddress("ad")
                .recipientIndex(111)
                .recipientName("joe")
                .build();


        MailEntity actualMail = mailService.registerMail(MailType.PARCEL, 111,"ad",  "joe");

        assertThat(actualMail.getMailType()).isEqualTo(expectedMail.getMailType());
        assertThat(actualMail.getRecipientAddress()).isEqualTo(expectedMail.getRecipientAddress());
        assertThat(actualMail.getRecipientIndex()).isEqualTo(expectedMail.getRecipientIndex());
        assertThat(actualMail.getRecipientName()).isEqualTo(expectedMail.getRecipientName());


    }

    @Test
    void getCurrentStatus() {

        MailEntity mail = MailEntity.builder()
                .mailType(MailType.PARCEL)
                .recipientAddress("ad")
                .recipientIndex(111)
                .recipientName("joe")
                .build();

        MailStateEntity mailState = MailStateEntity.builder()
                .mailEntity(mail)
                .postEntity(null)
                .mailStateType(MailStateType.ARRIVED)
                .build();

        when(mailStateRepo.findLastByMailId(Mockito.any())).thenReturn(mailState);

        MailStateDto actualMailStateDto = mailService.getCurrentStatus(1L);

        assertThat(mailState.getMailStateType()).isEqualTo(actualMailStateDto.getMailStateType());
    }

    @Test
    void getMailHistory() {
        MailEntity mail = MailEntity.builder()
                .mailType(MailType.PARCEL)
                .recipientAddress("ad")
                .recipientIndex(111)
                .recipientName("joe")
                .build();

        when(mailRepo.findById(Mockito.any())).thenReturn(Optional.ofNullable(mail));

        List<MailStateDto> returnedHistory = mailService.getMailHistory(1L);
        assertThat(returnedHistory).isNotNull();
    }

    @Test
    void changeMailState() {
        MailEntity mail = MailEntity.builder()
                .mailType(MailType.PARCEL)
                .recipientAddress("ad")
                .recipientIndex(111)
                .recipientName("joe")
                .build();

        MailStateEntity mailState = MailStateEntity.builder()
                .mailEntity(mail)
                .postEntity(null)
                .mailStateType(MailStateType.ARRIVED)
                .build();

        PostEntity postEntity = PostEntity.builder()
                .postName("fff")
                .recipientAddress("ddd")
                .mailState(null)
                .build();

        when(mailRepo.findById(Mockito.any())).thenReturn(Optional.ofNullable(mail));
        when(postRepo.findById(Mockito.any())).thenReturn(Optional.ofNullable(postEntity));
        when(mailStateRepo.saveAndFlush(Mockito.any())).thenReturn(mailState);

        MailStateDto mailStateDto = mailService.changeMailState(1L, MailStateType.ARRIVED, Optional.of(1L));
        assertThat(mailStateDto.getMailStateType()).isEqualTo(MailStateType.ARRIVED);
    }
}