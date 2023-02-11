package com.example.lazier.user.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("ROLE_USER");

    private String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }
}
