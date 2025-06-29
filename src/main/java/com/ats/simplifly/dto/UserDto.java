package com.ats.simplifly.dto;


import com.ats.simplifly.model.enums.Role;

public class UserDto {
    private int id;
    private String username;
    private Role role;

    public UserDto() {
    }

    public UserDto(int id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
