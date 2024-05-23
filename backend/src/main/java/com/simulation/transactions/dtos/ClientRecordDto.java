package com.simulation.transactions.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRecordDto(
   @NotBlank String name,
   @NotNull Integer age,
   @NotBlank @Email String email,
   @NotBlank String numAccount
) {}

