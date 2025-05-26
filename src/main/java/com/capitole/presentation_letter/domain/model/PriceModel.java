package com.capitole.presentation_letter.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PriceModel {
    private final Long brandID;
    private final Long productId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long priceList;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    public PriceModel(Long brandID, Long productId, LocalDateTime startDate, LocalDateTime endDate,
                      Long priceList, Integer priority, BigDecimal price, String currency) {
        this.brandID = brandID;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }
}
