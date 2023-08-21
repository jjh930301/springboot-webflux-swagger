package com.webflux.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleDto {
  @Schema(
    name = "id"
  )
  private String id;

  @Schema(
    name = "password"
  )
  private String password;
}
