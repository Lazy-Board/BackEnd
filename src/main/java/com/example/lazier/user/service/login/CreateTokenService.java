package com.example.lazier.user.service.login;

import com.example.lazier.user.dto.TokenDTO;
import com.example.lazier.user.model.UserLogin;

public interface CreateTokenService {
    TokenDTO createAccessToken(UserLogin userLogin);
}
