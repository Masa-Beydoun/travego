package com.example.security.Security.Token;

import com.example.security.Models.BaseUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Component
public class ConfirmationToken {
    @Id

    private int id;
    private String token;

   // @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDate createdDate;

    @OneToOne(targetEntity = BaseUser.class,fetch =FetchType.EAGER)
    @JoinColumn(nullable = false,name="user_id")
    private BaseUser user;

    public ConfirmationToken(BaseUser user)
    {
        this.user=user;
        this.createdDate=LocalDate.now();
        this.token= UUID.randomUUID().toString();

    }
}
