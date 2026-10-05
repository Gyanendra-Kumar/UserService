package com.learning.userservice.services;

import com.learning.userservice.exceptions.InvalidTokeException;
import com.learning.userservice.exceptions.PasswordMismatchException;
import com.learning.userservice.models.Token;
import com.learning.userservice.models.User;
import com.learning.userservice.repositories.TokenRepository;
import com.learning.userservice.repositories.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;  // Inject the bean to use, by using constructor
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,  BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signup(String name, String email, String password) {
        // check if the user is already present with the email
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            //redirect to login page
            return user.get();
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);

        // use bcrypt password encoder to encrypt the password
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUser.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(newUser);
    }

    @Override
    public Token login(String email, String password) throws PasswordMismatchException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            // redirect the user to sign up page
            return null;
        }
        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new PasswordMismatchException("Incorrect username or password");
        }

        // login successful
        Token token = new Token();
        token.setUser(user);
        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDate = calendar.getTime();

        token.setExpiryAt(expiryDate);

        return tokenRepository.save(token);
    }

    @Override
    public User validateToken(String tokenValue) throws InvalidTokeException {
        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryAtGreaterThan(tokenValue, new Date());

        if(optionalToken.isEmpty()){
            // Invalid token
            throw new InvalidTokeException("Invalid token");
        }

        // Token valid
        Token token = optionalToken.get();

        return token.getUser();
    }
}
