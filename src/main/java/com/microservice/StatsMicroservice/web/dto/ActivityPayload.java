package com.microservice.StatsMicroservice.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityPayload {

    @NotBlank(message = "Path cannot be empty")
    private String path;

    @NotNull(message = "UserId cannot be null")
    private Long userId;
}
