package com.example.security.Security.Token;

import com.example.security.Models.Users;
import com.example.security.Models.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Component
public class NumberConfirmationToken {
    @Id
    @GeneratedValue
    private int id;
    private String code;

    // @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdDate=LocalDateTime.now();

    private Boolean valid;
    private LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10);

    @OneToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name="user_id")
    public Users user;

    private String user_email;

}
