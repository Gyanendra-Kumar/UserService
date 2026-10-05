package com.learning.userservice.controllers;

import com.learning.userservice.dtos.LoginRequestDto;
import com.learning.userservice.dtos.SignUpRequestDto;
import com.learning.userservice.dtos.TokenDto;
import com.learning.userservice.dtos.UserResponseDTOs;
import com.learning.userservice.exceptions.InvalidTokeException;
import com.learning.userservice.exceptions.PasswordMismatchException;
import com.learning.userservice.models.Token;
import com.learning.userservice.models.User;
import com.learning.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

// Known as UserDTOs

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDTOs signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.signup(
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword()
        );
        return UserResponseDTOs.from(user);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) throws PasswordMismatchException {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return TokenDto.from(token);
    }

    @GetMapping("/validate/{tokenValue}")
    public UserResponseDTOs validateToken(@PathVariable("tokenValue") String tokenValue) throws InvalidTokeException {
        System.out.println("Validating Token");
        User user = userService.validateToken(tokenValue);
        return UserResponseDTOs.from(user);
    }

    @PostMapping("/logout")
    public boolean logout(){
        return false;
    }
}
