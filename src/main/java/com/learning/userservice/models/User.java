package com.learning.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;

    @ManyToMany
    private List<Role> roles;
}


/*
  1    ->    M
User ----- Roles  => M:M
  M   <-    1

 */