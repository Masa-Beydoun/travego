package com.example.security.Security.Token;

import com.example.security.Models.Author;
import com.example.security.Models.BaseUser;
import com.example.security.Models.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    public Integer id;
    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public  TokenType tokenType= TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

//@JsonIgnore
  //  @ManyToOne(fetch = FetchType.EAGER)
   // @JoinColumn(name = "AUTHOR_ID")
   // public Author author;


   // @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "STUDENT_ID")
   // public Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public BaseUser user;



}
