package com.capitole.presentation_letter.application.service;

import com.capitole.presentation_letter.domain.model.PriceModel;
import com.capitole.presentation_letter.domain.ports.in.PriceUseCase;
import com.capitole.presentation_letter.domain.ports.out.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceService implements PriceUseCase {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<PriceModel> getApplicablePrice(Long brandId, Long productId, LocalDateTime dateTime) {
        return priceRepository.findApplicablePrice(brandId, productId, dateTime);
    }
}
