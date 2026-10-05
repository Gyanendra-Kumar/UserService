package com.learning.userservice.services;

import com.learning.userservice.exceptions.InvalidTokeException;
import com.learning.userservice.exceptions.PasswordMismatchException;
import com.learning.userservice.models.Token;
import com.learning.userservice.models.User;

public interface UserService {
    User signup(String name, String email, String password);

    Token login(String email, String password) throws PasswordMismatchException;

    User validateToken(String tokenValue) throws InvalidTokeException;

//    boolean logout();
}
