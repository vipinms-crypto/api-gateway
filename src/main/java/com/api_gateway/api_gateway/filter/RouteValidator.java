package com.api_gateway.api_gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	private RouteValidator() {
	}

	public static final List<String> openApiEndpoints = List.of("/api/register", "/api/authenticate", "/api/login");

	public static final Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}