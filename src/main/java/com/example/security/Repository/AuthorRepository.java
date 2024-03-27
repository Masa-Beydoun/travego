package com.example.security.Repository;

import com.example.security.Models.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer>, JpaSpecificationExecutor<Author> {

  //  @Modifying
    //@Transactional
    //List<Author> findTheAuthorOne(String firstName);
    @Modifying
    @Transactional
    @Query("update Author a set a.email=:email")
    void updateAllAuthorsEmail(String email);

    @Modifying
    @Transactional
    @Query("select Author from Author where email=:email")
    void SelectAllAuthorsEmail(String email);

    @Modifying
    @Transactional
    ResponseEntity<?> findAuthorById(int id);

    Optional<Author> findByEmail (String email);


}
