package com.example.security.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


@Entity
@Data
//@SuperBuilder
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
//@DiscriminatorColumn(name = "Resource_Type")
public class Resources {

    @Id
    @GeneratedValue
    private long id;
    @NotBlank
    private String name;
    @NotNull
    private Long size;

   @Lob
  // @Column(length = 1000)
    private byte[] video;
  @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
/*
    // @JsonIgnore
    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDate createdAt;
    // @JsonIgnore
    @LastModifiedDate
    @Column(
            insertable = false
    )
    private LocalDate lastModifiedAt;
    // @JsonIgnore
    @CreatedBy
    @Column(
            nullable = false,
            updatable = false
    )
    private Integer createdBy;
    //@JsonIgnore
    @LastModifiedBy
    @Column(
            insertable = false
    )
    private Integer lastModifiedBy;
*/
}
