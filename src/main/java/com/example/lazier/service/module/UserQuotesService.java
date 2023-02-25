package com.example.lazier.service.module;

import com.example.lazier.dto.module.UserQuotesDto;
import com.example.lazier.dto.module.UserQuotesInput;
import com.example.lazier.exception.QuotesException.UserAlreadyExistException;
import com.example.lazier.exception.QuotesException.UserNotFoundException;
import com.example.lazier.persist.entity.module.UserQuotes;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.UserQuotesRepository;
import com.example.lazier.service.user.MemberService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserQuotesService {

    private final UserQuotesRepository userQuotesRepository;
    private final MemberService memberService;

    public void add(HttpServletRequest request, UserQuotesInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);
        // 중복 아이디 예외
        if (userQuotesRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 존재합니다.");
        }

        UserQuotes userQuotes = UserQuotes.builder().lazierUser(lazierUser)
            .content(parameter.getContent()).build();

        userQuotesRepository.save(userQuotes);
    }

    public UserQuotesDto get(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        Optional<UserQuotes> optionalUserQuotes = userQuotesRepository.findByLazierUser(lazierUser);

        return optionalUserQuotes.map(UserQuotesDto::of).orElse(null);
    }

    @Transactional
    public void update(HttpServletRequest request, UserQuotesInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserQuotes userQuotes = userQuotesRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userQuotes.update(parameter.getContent());
    }

    public void delete(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserQuotes userQuotes = userQuotesRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userQuotesRepository.delete(userQuotes);
    }

}
