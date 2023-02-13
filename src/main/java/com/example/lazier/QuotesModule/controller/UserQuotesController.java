package com.example.lazier.QuotesModule.controller;

import com.example.lazier.QuotesModule.model.UserQuotesInput;
import com.example.lazier.QuotesModule.service.UserQuoteService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/userQuotes")
public class UserQuotesController {

    private final UserQuoteService userQuoteService;

    @PostMapping
    public ResponseEntity<?> add(HttpServletRequest request,
        @RequestBody @Valid UserQuotesInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        userQuoteService.add(parameter);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
    }
}
