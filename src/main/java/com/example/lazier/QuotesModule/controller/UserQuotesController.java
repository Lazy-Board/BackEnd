package com.example.lazier.QuotesModule.controller;

import com.example.lazier.QuotesModule.model.UserQuotesInput;
import com.example.lazier.QuotesModule.service.UserQuoteService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<?> get(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        return new ResponseEntity<>(userQuoteService.get(userId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request,
        @RequestBody @Valid UserQuotesInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        userQuoteService.update(parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        userQuoteService.delete(userId);
        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
