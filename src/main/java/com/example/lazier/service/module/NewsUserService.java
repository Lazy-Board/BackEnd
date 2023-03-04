package com.example.lazier.service.module;

import com.example.lazier.dto.module.NewsPressDto;
import com.example.lazier.dto.module.NewsUserInput;
import com.example.lazier.exception.PressNotFoundException;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.NewsPress;
import com.example.lazier.persist.entity.module.NewsUser;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsUserRepository;
import com.example.lazier.service.user.MyPageService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsUserService {


  private final NewsUserRepository newsUserRepository;
  private final MyPageService myPageService;
  private final NewsPressRepository newsPressRepository;

  /**
   * 추가: 멤버조회(전체) 되면 -> 모듈멤버확인 ->없으면 -> 유저레포초기화 ; 수정: userInput//3개의 스트링(언론사명) ; 삭제: 하지 않음 (* 회원탈퇴의
   * 영역);
   */
  //
  @Transactional
  public void add(HttpServletRequest request) {
    long userId = Long.parseLong(request.getAttribute("userId").toString());
    //멤버인지 확인 -> 아닐경우 멤버서비스에서 Error Throw
    LazierUser lazierUser = myPageService.searchMember(userId);

    // 뉴스 레포에 정보가 없을 경우 초기화 정보 생성
    if (!newsUserRepository.existsByLazierUser(lazierUser)) {
      List<NewsPress> userPress = newsPressRepository.findFirstByOrderByPressIdAsc();
      newsUserRepository.save(
          NewsUser.builder().lazierUser(lazierUser).userPress(userPress).build());
    } else {
      throw new UserAlreadyExistException("사용이력이 있는 모듈입니다.기존 정보로 모듈을 사용합니다.");
    }
  }

  @Transactional
  public List<NewsPressDto> showUserPressList(HttpServletRequest request) {
    long userId = Long.parseLong(request.getAttribute("userId").toString());
    //멤버인지 확인 -> 아닐경우 멤버서비스에서 Error Throw
    LazierUser lazierUser = myPageService.searchMember(userId);
    NewsUser user = newsUserRepository.findByLazierUser(lazierUser)
        .orElseThrow(() -> new UserNotFoundException("해당모듈 사용자가 아닙니다."));

    return user.getUserPress().stream().map(NewsPressDto::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public void update(HttpServletRequest request, NewsUserInput userInput) {
    long userId = Long.parseLong(request.getAttribute("userId").toString());
    //멤버인지 확인 -> 아닐경우 멤버서비스에서 Error Throw
    LazierUser lazierUser = myPageService.searchMember(userId);

    NewsUser user = newsUserRepository.findByLazierUser(lazierUser)
        .orElseThrow(() -> new UserNotFoundException("해당모듈 사용자가 아닙니다."));

    List<NewsPress> newUserPressList = new ArrayList<>();

    NewsPress newsPress1 = newsPressRepository.findByPressName(userInput.getPress1())
        .orElseThrow(() -> new PressNotFoundException("존재하지 않는 언론사명입니다."));

    newUserPressList.add(newsPress1);

    user.update(newUserPressList);
    newsUserRepository.save(user);
  }
}