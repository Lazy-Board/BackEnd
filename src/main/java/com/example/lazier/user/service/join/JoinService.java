package com.example.lazier.user.service.join;

import com.example.lazier.user.dto.JoinDTO;
import com.example.lazier.user.model.UserSignUp;

public interface JoinService {

    JoinDTO signUp(UserSignUp request);
    void emailAuth(String uuid);
}
