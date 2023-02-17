package com.example.lazier.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSignupInput {

    @Email
    @NotBlank
    String userEmail;

    @Size(min = 8)
    @NotBlank
    String password;

    @NotBlank
    String userName;

    @Size(min = 2)
    @NotBlank
    String nickName;

    @NotBlank
    @Pattern(regexp = "[0-9]{10,11}", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
    String phoneNumber;
}
