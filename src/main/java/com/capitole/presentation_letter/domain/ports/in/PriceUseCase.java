package com.capitole.presentation_letter.domain.ports.in;

import com.capitole.presentation_letter.domain.model.PriceModel;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceUseCase {
    Optional<PriceModel> getApplicablePrice(Long brandId, Long productId, LocalDateTime dateTime);
}
