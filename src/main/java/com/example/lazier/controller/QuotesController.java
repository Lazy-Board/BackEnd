package com.example.lazier.controller;

import com.example.lazier.service.QuotesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/quotes")
public class QuotesController {

    public final QuotesService quotesService;

    @GetMapping
    public ResponseEntity<?> getQuotes() {
        return new ResponseEntity<>(quotesService.get(), HttpStatus.OK);
    }
}
