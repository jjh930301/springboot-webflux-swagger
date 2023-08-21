package com.webflux.example.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class HandlerInterceptor{

  public Mono<ServerResponse> filter(ServerRequest req , HandlerFunction<ServerResponse> next) {
    // 사용자 인증
    if(req.headers().header("Authorization") != null) {
      return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    }
    return next.handle(req);
  }
}
