package com.example.lazier.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginInput {

    @NotBlank(message = "이메일 항목은 필수 입니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호 항목은 필수 입니다")
    private String password;
}
