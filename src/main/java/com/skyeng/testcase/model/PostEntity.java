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
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "post_name")
    String postName;

    @Column(name = "recipient_address")
    String recipientAddress;

    @Singular
    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MailStateEntity> mailStates = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity that = (PostEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(postName, that.postName) && Objects.equals(recipientAddress, that.recipientAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postName, recipientAddress);
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", postName='" + postName + '\'' +
                ", recipientAddress='" + recipientAddress + '\'' +
                '}';
    }
}
