package com.skyeng.testcase.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mail_states")
public class MailStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_state")
    MailStateType mailStateType;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "mail_id")
    MailEntity mailEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id")
    PostEntity postEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailStateEntity mailState = (MailStateEntity) o;
        return id.equals(mailState.id) && Objects.equals(createdAt, mailState.createdAt) && mailStateType == mailState.mailStateType && Objects.equals(mailEntity, mailState.mailEntity) && Objects.equals(postEntity, mailState.postEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, mailStateType, mailEntity, postEntity);
    }

    @Override
    public String toString() {
        return "MailStateEntity{" +
                "createdAt=" + createdAt +
                ", mailStateType=" + mailStateType +
                ", mailEntity=" + mailEntity +
                '}';
    }
}
