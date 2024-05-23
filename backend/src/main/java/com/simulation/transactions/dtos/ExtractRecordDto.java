package com.simulation.transactions.dtos;

import com.simulation.transactions.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExtractRecordDto(
        @NotNull BigDecimal value,
        @NotNull TransactionType type
){}
