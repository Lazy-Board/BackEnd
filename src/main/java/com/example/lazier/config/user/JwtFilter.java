package com.example.lazier.config.user;

import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = jwtTokenProvider.resolveToken(request);

		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				request.setAttribute("userId", authentication.getName());
			}
		} catch (IllegalArgumentException | JwtException e) {
			log.warn("jwtException에러: " + e.getMessage());
			request.setAttribute("jwtexception", e.getMessage());
		} catch (Exception e) {
			log.warn("nonjwtException에러: " + e.getMessage());
			request.setAttribute("nonjwtexception", e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getMethod().equals("OPTIONS");
	}
}
