package com.capitole.presentation_letter.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class PriceModel {
    private final Long brandID;
    private final Long productId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long priceList;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;
}