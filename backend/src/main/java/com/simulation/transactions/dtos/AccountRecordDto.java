package com.simulation.transactions.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountRecordDto(
        @NotBlank String numAccount,
        @NotBlank @NotNull BigDecimal balance
) {}
