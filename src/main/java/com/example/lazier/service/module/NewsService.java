package com.example.lazier.service.module;

import com.example.lazier.dto.module.NewsDto;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.News;
import com.example.lazier.persist.entity.module.NewsPress;
import com.example.lazier.persist.entity.module.NewsUser;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.persist.repository.NewsUserRepository;
import com.example.lazier.scraper.NewsScraper;
import com.example.lazier.service.user.MemberService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

  private final NewsScraper newsScraper;
  private final NewsRepository newsRepository;
  private final NewsPressRepository newsPressRepository;
  private final NewsUserRepository newsUserRepository;
  private final MemberService memberService;


  /**
   * db 초기화로직 // 배포시 삭제 또는 주석처리 필요.
   */
  public void dbInit() {
    String year = Integer.toString(LocalDateTime.now().getYear());
    String month = Integer.toString(LocalDateTime.now().getMonthValue());
    if (month.length() < 2) {
      month = "0" + month;
    }
    String day = Integer.toString(LocalDateTime.now().getDayOfMonth());
    if (day.length() < 2) {
      day = "0" + day;
    }
    String todayString = year + month + day;
    newsScraper.crawlPressList().stream().map(NewsPress::new).forEach(entity ->
        {
          boolean exists = newsPressRepository.existsById(entity.getPressId());
          if (!exists) {
            newsPressRepository.save(entity);
            log.info(entity.getPressName() + "is saved");
          }
        }
    );

    List<String> pressIdList = newsPressRepository.findAll().stream().map(NewsPress::getPressId)
        .collect(Collectors.toList());

    pressIdList
        .forEach(id -> newsScraper.crawlNewsByPressAndDate(id, todayString).stream().map(
                News::new).forEach(entity -> {
              boolean exists = newsRepository.existsById(entity.getNewsId());
              if (!exists) {
                newsRepository.save(entity);
                log.info(entity.getSubject() + "is saved");
              }
            })
        );
  }

  public List<NewsDto> showNewsByUser(HttpServletRequest request) {
    long userId = Long.parseLong(request.getAttribute("userId").toString());
    //멤버인지 확인 -> 아닐경우 멤버서비스에서 Error Throw
    LazierUser lazierUser = memberService.searchMember(userId);
    NewsUser user = newsUserRepository.findByLazierUser(lazierUser)
        .orElseThrow(() -> new UserNotFoundException("해당모듈 사용자가 아닙니다."));

    List<NewsDto> newsByUser = new ArrayList<>();
    user.getUserPress()
        .forEach(entity -> newsRepository.findTop10ByPressIdOrderByNewsIdDesc(
            entity.getPressId()).stream().map(NewsDto::from).forEach(newsByUser::add));
    return newsByUser;
  }


}
