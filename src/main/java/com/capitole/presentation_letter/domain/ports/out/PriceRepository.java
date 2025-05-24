package com.capitole.presentation_letter.domain.ports.out;

import com.capitole.presentation_letter.domain.model.PriceModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepository {
    Optional<PriceModel> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);
    void saveAll(List<PriceModel> prices);
}
