package com.capitole.presentation_letter.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PriceModel {
    private Long brandID;
    private Long productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long priceList;
    private Integer priority;
    private BigDecimal price;
    private String currency;

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
