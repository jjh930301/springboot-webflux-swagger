package com.webflux.example.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class ExampleHandler {

  public Mono<ServerResponse> insert(ServerRequest request) {
    return ServerResponse.ok().build();
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    return ServerResponse.ok().build();
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    return ServerResponse.ok().build();
  }

  public Mono<ServerResponse> get(ServerRequest request) {
    return ServerResponse.ok().build();
  }

}
