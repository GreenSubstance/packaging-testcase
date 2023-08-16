package com.skyeng.testcase.api.repositories;

import com.skyeng.testcase.model.MailEntity;
import com.skyeng.testcase.model.MailStateEntity;
import com.skyeng.testcase.model.MailStateType;
import com.skyeng.testcase.model.MailType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MailStateRepoTest {

    @Autowired
    private MailStateRepo mailStateRepo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldFindLastByMailId() {

        MailEntity mail = MailEntity.builder()
                .mailType(MailType.PARCEL)
                .recipientAddress("ad")
                .recipientIndex(111)
                .recipientName("joe")
                .build();

        entityManager.persist(mail);

        MailStateEntity mailState1 = MailStateEntity.builder()
                .mailEntity(mail)
                .postEntity(null)
                .mailStateType(MailStateType.ARRIVED)
                .build();

        MailStateEntity mailState2 = MailStateEntity.builder()
                .mailEntity(mail)
                .postEntity(null)
                .mailStateType(MailStateType.DEPARTED)
                .build();

        MailStateEntity mailState3 = MailStateEntity.builder()
                .mailEntity(mail)
                .postEntity(null)
                .mailStateType(MailStateType.RECEIVED)
                .build();


        entityManager.persist(mailState1);
        entityManager.flush();
        entityManager.persist(mailState2);
        entityManager.flush();
        entityManager.persist(mailState3);
        entityManager.flush();

        MailStateEntity mailStateFound = mailStateRepo.findLastByMailId(mail.getId());

        assertThat(mailStateFound).isEqualTo(mailState1);
    }
}