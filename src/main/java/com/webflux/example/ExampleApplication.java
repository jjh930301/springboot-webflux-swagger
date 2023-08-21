package com.webflux.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.example.dto.ExampleDto;
import com.webflux.example.handlers.ExampleHandler;
import com.webflux.example.interceptors.HandlerInterceptor;

@SpringBootApplication
public class ExampleApplication {

	@Autowired
	private HandlerInterceptor interceptor;

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

	@Bean
  @RouterOperations({
		@RouterOperation(
			path = "/test", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.POST, 
			beanClass = ExampleHandler.class, 
			beanMethod = "insert",
			operation = @Operation(
				operationId = "insert", 
				responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ExampleDto.class)))}
					, 
				requestBody = @RequestBody(
					content = @Content(
						schema = @Schema(
							implementation = ExampleDto.class
				)))
			)),
		@RouterOperation(
			path = "/test/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.PUT, 
			beanClass = ExampleHandler.class, 
			beanMethod = "update",
			operation = 
				@Operation(
					operationId = "update", 
					parameters = {@Parameter(in = ParameterIn.PATH, name = "id")},
					requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ExampleDto.class))),
					responses = {
						@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ExampleDto.class)))}
				)
		),
		@RouterOperation(
			path = "/test/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			beanClass = ExampleHandler.class, 
			method = RequestMethod.GET, 
			beanMethod = "get",
			operation = @Operation(
				operationId = "get", 
				parameters = {@Parameter(in = ParameterIn.PATH, name = "id")},
				responses = {
					@ApiResponse(responseCode = "200", description = "successful operation",content = @Content(schema = @Schema(implementation = ExampleDto.class)))}
					
		)),
		@RouterOperation(
			path = "/test/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.DELETE, 
			beanClass = ExampleHandler.class, 
			beanMethod = "delete", 
			operation = @Operation(
				operationId = "delete", 
				parameters = {@Parameter(in = ParameterIn.PATH, name = "id")},
				responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean"))
				}
		))
	})
  RouterFunction<ServerResponse> exampleRouter(ExampleHandler requestHandler) {
    return RouterFunctions
			.route(RequestPredicates.POST("/test"),requestHandler::insert)
			.andRoute(RequestPredicates.PUT("/test/{id}"),requestHandler::update)
			.andRoute(RequestPredicates.DELETE("/test/{id}"),requestHandler::delete)
			.andRoute(RequestPredicates.GET("/test/{id}"),requestHandler::get)
			.filter((request, next) -> interceptor.filter(request, next));
  }

}
