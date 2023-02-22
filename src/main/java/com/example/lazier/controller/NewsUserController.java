package com.example.lazier.controller;

import com.example.lazier.dto.module.NewsPressDto;
import com.example.lazier.dto.module.NewsUserInput;
import com.example.lazier.service.Impl.NewsUserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/newsuser")
@RequiredArgsConstructor
public class NewsUserController {

  private final NewsUserService newsUserService;
//    private final CacheManager redisCacheManager;

  /**
   * 동작 확인용 // 미사용 컨트롤러,
   * TODO 배포시 주석처리 필요
   */
  @GetMapping
  public ResponseEntity<?> getUserPressList(HttpServletRequest request) {
    List<NewsPressDto> userPressList = newsUserService.showUserPressList(request);
    return ResponseEntity.ok(userPressList);
  }

  @PostMapping
  public ResponseEntity<?> addUserToNewsModule(HttpServletRequest request) {
    newsUserService.add(request);
    return ResponseEntity.ok("유저의 모듈사용이 정상적으로 추가 및 초기화되었습니다.");
  }

  @PutMapping
  public ResponseEntity<?> updateUserPressList(HttpServletRequest request, NewsUserInput userInput) {
    newsUserService.update(request,userInput);
    return ResponseEntity.ok("유저의 언론사 리스트가 정상적으로 수정되었습니다.");
  }



}


