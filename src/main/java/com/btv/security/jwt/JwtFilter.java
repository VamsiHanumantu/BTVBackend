package com.btv.security.jwt;

import com.btv.security.CustomUserDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {

			String token = header.substring(7);
			String username = jwtUtil.extractUsername(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				var userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtUtil.validateToken(token, userDetails)) {

					var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
