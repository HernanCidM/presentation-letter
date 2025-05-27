package com.capitole.presentation_letter.domain.exceptions;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException{

    public PriceNotFoundException(Long brandId, Long productId, LocalDateTime date) {
        super("No applicable price found for the combination: brandId=" + brandId +
                ", productId=" + productId + ", date=" + date);
    }
}
