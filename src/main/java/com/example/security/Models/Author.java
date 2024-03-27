package com.example.security.Models;

import com.example.security.RolesAndPermission.Roles;
import com.example.security.Security.Token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter

/*@NamedQuery(
        name = "Author.findTheAuthorOne",
query = "select Author from Author where  authorName=:firstName")
*
 */
public class Author extends BaseUser implements UserDetails {


    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
   @JsonIgnore
    private List<Course> courses;
    //TRY REMOVE IT
//@Nullable
//@JsonIgnore
  //  @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
   // private List<Token> tokens;
    @JsonIgnore
    @Nullable
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

//EDIT THIS AFTER ADDING ROLES


}
