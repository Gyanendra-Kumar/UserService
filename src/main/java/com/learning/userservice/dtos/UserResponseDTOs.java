package com.learning.userservice.dtos;

import com.learning.userservice.models.Role;
import com.learning.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTOs {
    private Long userId;
    private String email;
    private List<Role> roles;

    public static UserResponseDTOs from(User user){
        if(user == null) return null;

        UserResponseDTOs userResponseDTOs = new UserResponseDTOs();
        userResponseDTOs.setUserId(user.getId());
        userResponseDTOs.setEmail(user.getEmail());
        userResponseDTOs.setRoles(user.getRoles());

        return userResponseDTOs;
    }
}
