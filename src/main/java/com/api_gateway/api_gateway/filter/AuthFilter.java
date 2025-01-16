package com.api_gateway.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.api_gateway.api_gateway.exception.MissingHeaderException;
import com.api_gateway.api_gateway.utils.JwtUtil;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

	private RouteValidator validator;

	private JwtUtil jwtUtil;

	public AuthFilter(RouteValidator validator, JwtUtil jwtUtil) {
		super(Config.class);
		this.validator = validator;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new MissingHeaderException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				jwtUtil.validateToken(authHeader);
			}
			return chain.filter(exchange);
		});
	}

	public static class Config {

	}
}