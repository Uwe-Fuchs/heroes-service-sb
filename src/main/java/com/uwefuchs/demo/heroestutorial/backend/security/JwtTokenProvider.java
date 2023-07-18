package com.uwefuchs.demo.heroestutorial.backend.security;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTHORITIES_KEY = "auth";

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; // 1h

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public Authentication getAuthentication(String token) {
		final Claims claims;
		
		try {
			claims = Jwts.parser() //
				.setSigningKey(secretKey) //
				.parseClaimsJws(token) //
				.getBody(); //
		} catch (JwtException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
		} catch (IllegalArgumentException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.BAD_REQUEST);
		}

		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString()
				.split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new JwtAuthenticationToken(authorities, claims.getSubject(), token);
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
