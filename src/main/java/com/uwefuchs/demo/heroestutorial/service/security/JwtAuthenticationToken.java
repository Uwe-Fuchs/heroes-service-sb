package com.uwefuchs.demo.heroestutorial.service.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	private final Object principal;
	private String jwtToken;

	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal,
			String jwtToken) {
		super(authorities);
		this.principal = principal;
		this.jwtToken = jwtToken;
		super.setAuthenticated(true);
	}

	public JwtAuthenticationToken(Object principal,
			String jwtToken) {
		super(null);
		this.principal = principal;
		this.jwtToken = jwtToken;
		super.setAuthenticated(false);
	}

	@Override
	public Object getCredentials() {
		return getJwtToken();
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public String getJwtToken() {
		return jwtToken;
	}
}
