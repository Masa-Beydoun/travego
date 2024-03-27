package com.example.security.Security.Token;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token,Integer> {

    @Query(value = """
select t from Token t inner join Author a\s
on t.user.id=a.id\s where a.id=:id and (t.expired=false or t.revoked=false)\s
""")
    List<Token> findAllValidTokenByUser(int id);

    Optional<Token> findByToken(String token);

}
