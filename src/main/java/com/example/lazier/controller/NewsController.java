package com.example.lazier.controller;

import com.example.lazier.dto.module.NewsDto;
import com.example.lazier.service.module.NewsService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/news", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class NewsController {

  private final NewsService newsService;
//    private final CacheManager redisCacheManager;

  /**
   * 동작 확인용 // 미사용 컨트롤러,
   * TODO 배포시 주석처리 필요
   */
  @ApiOperation(value = "미사용 API입니다:News DB정보 초기화용// 추후 삭처리 예정 ")
  @GetMapping("/init")
  public ResponseEntity<?> init() {
    this.newsService.dbInit();
    return ResponseEntity.ok("news DB가 크롤링되었습니다.");
  }

  @ApiOperation(value = "사용자가 선택한 언론사 뉴스 리스트 JSON 반환. ")
  @GetMapping
  public ResponseEntity<?> showNews(HttpServletRequest request) {
    List<NewsDto> newsDtoList = this.newsService.showNewsByUser(request);
    return ResponseEntity.ok(newsDtoList);
  }

  @ApiOperation(value = "전체 언론사 리스트 반환 ")
  @GetMapping("/press")
  public ResponseEntity<?> showNewsPress(HttpServletRequest request) {
    return ResponseEntity.ok(this.newsService.showEntireNewsPressList(request));
  }

}


