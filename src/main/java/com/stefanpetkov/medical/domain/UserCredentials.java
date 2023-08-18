package com.stefanpetkov.medical.domain;


import com.stefanpetkov.medical.security.ApplicationUserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_credentials")
public class UserCredentials implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credentials_id")
    private Long credentialsId;

    @NotEmpty(message = "Email should not be empty")
    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Column(length = 150, nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private ApplicationUserRole applicationUserRole;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private BaseUser baseUser;

    @Override
    public String toString() {
        return "Credentials{" +
                "credentialsId=" + credentialsId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + applicationUserRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCredentials that = (UserCredentials) o;

        return Objects.equals(credentialsId, that.credentialsId);
    }

    @Override
    public int hashCode() {
        return credentialsId != null ? credentialsId.hashCode() + 31 : 47;
    }

}
