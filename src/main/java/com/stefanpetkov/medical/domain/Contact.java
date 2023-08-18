package com.stefanpetkov.medical.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Column(length = 50, nullable = false)
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Column(length = 50, nullable = false)
    private String email;

    @NotEmpty(message = "Phone should not be empty")
    @Column(length = 30, nullable = false)
    private String phone;


    @NotEmpty(message = "Message should not be empty")
    @Lob
    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDateTime createdMessageTimestamp;

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                ", createdMessage=" + createdMessageTimestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() + 11 : 17;
    }
}
