package com.example.lazier.user.controller;

import com.example.lazier.user.model.UserSignUp;
import com.example.lazier.user.service.join.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final JoinService joinService;

    @PostMapping("/signUp")
    public ResponseEntity<?> join(@RequestBody @Valid UserSignUp request) {

        return new ResponseEntity<>(joinService.signUp(request), HttpStatus.OK);
    }

    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam(value = "uuid") String uuid) {
        joinService.emailAuth(uuid);

        return ResponseEntity.ok().build();
    }
}
