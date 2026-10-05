package com.learning.userservice.repositories;

import com.learning.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenValue(String token);

    Token save(Token token);

    // validate the token
    // check if the token is present in the table with the given value;
    // check if the expiryTime of the token is greater than current time
    // select * from token where token_value = ? and expiry_at > ?

    Optional<Token> findByTokenValueAndExpiryAtGreaterThan(String token, Date expiryAt);
}
