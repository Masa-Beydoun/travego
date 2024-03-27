package com.example.security.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntitiy {

    private String title;

    private String description;

    private Double price;
  // @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
     Author author;

    @OneToMany(mappedBy ="course",fetch = FetchType.EAGER)
   //@JsonIgnore
    List<Lecture> lectures;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses",fetch = FetchType.EAGER)
    List<Student> students;

}
