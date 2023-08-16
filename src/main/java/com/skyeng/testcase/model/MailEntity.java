package com.skyeng.testcase.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mail")
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_type")
    MailType mailType;

    @Column(name = "recipient_index")
    int recipientIndex;

    @Column(name = "recipient_address")
    String recipientAddress;

    @Column(name = "recipient_name")
    String recipientName;

    @Singular
    @OneToMany(mappedBy = "mailEntity", cascade = CascadeType.ALL, orphanRemoval = true) //all
    List<MailStateEntity> mailStates = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailEntity that = (MailEntity) o;
        return recipientIndex == that.recipientIndex && id.equals(that.id) && mailType == that.mailType && Objects.equals(recipientAddress, that.recipientAddress) && Objects.equals(recipientName, that.recipientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mailType, recipientIndex, recipientAddress, recipientName);
    }

    @Override
    public String toString() {
        return "MailEntity{" +
                "mailType=" + mailType +
                ", recipientIndex=" + recipientIndex +
                ", recipientAddress='" + recipientAddress + '\'' +
                ", recipientName='" + recipientName + '\'' +
                '}';
    }
}
