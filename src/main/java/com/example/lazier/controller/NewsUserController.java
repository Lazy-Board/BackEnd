package com.example.lazier.controller;

import com.example.lazier.dto.module.NewsPressDto;
import com.example.lazier.dto.module.NewsUserInput;
import com.example.lazier.service.module.NewsUserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @ApiOperation(value = "유저가 현재 선택해놓은 언론사 정보 리스트를 3개 리턴합니다.")
  @GetMapping
  public ResponseEntity<?> getUserPressList(HttpServletRequest request) {
    List<NewsPressDto> userPressList = newsUserService.showUserPressList(request);
    return ResponseEntity.ok(userPressList);
  }


  @ApiOperation(value = "유저를 모듈 사용자에 추가하고 기본 언론사로 초기화합니다. ")
  @PostMapping
  public ResponseEntity<?> addUserToNewsModule(HttpServletRequest request) {
    newsUserService.add(request);
    return ResponseEntity.ok("유저의 모듈사용이 정상적으로 추가 및 초기화되었습니다.");
  }

  @ApiOperation(value = "유저가 보고싶은 언론사를 3개 선택후 요청하게 합니다. *프론트 단에서 강제해주세요.")
  @PutMapping
  public ResponseEntity<?> updateUserPressList(HttpServletRequest request,
      @RequestBody NewsUserInput userInput) {
    newsUserService.update(request, userInput);
    return ResponseEntity.ok("유저의 언론사 리스트가 정상적으로 수정되었습니다.");
  }


}


