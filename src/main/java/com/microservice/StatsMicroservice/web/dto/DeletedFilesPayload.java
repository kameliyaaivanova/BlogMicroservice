package com.microservice.StatsMicroservice.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedFilesPayload {

    @NotNull(message = "Amount cannot be null")
    private Long amount;
}

