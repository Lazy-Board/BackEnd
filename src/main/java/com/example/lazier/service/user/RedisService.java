package com.example.lazier.service.user;

import com.example.lazier.config.user.JwtTokenProvider;
import java.time.Duration;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate redisTemplate;
	private final JwtTokenProvider jwtTokenProvider;

	public void setValues(String token){
		ValueOperations<String, String> values = redisTemplate.opsForValue(); //key - value 설정
		String userId = jwtTokenProvider.getUserPk(token);

		values.set(userId, token, Duration.ofMinutes(6));  //refresh token 기한 6분, 6분 뒤 메모리에서 삭제 (test)
	}

	public String getValues(String userId){
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		return values.get(userId);
	}

	public void delValues(HttpServletRequest request) {
		String token = jwtTokenProvider.resolveToken(request);
		String userId = jwtTokenProvider.getUserPk(token);

		redisTemplate.delete(userId);
	}
}
