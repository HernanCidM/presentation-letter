package com.capitole.presentation_letter.application.service;

import com.capitole.presentation_letter.domain.model.PriceModel;
import com.capitole.presentation_letter.domain.ports.out.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceServiceTest {

    private PriceRepository priceRepository;
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceService = new PriceService(priceRepository);
    }

    @Test
    void shouldReturnApplicablePrice() {
        // given
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.now();

        PriceModel model = new PriceModel(
                brandId,
                productId,
                date.minusDays(1),
                date.plusDays(1),
                1L,
                1,
                BigDecimal.valueOf(35.5),
                "EUR"
        );

        when(priceRepository.findApplicablePrice(brandId, productId, date))
                .thenReturn(Optional.of(model));


        Optional<PriceModel> result = priceService.getApplicablePrice(brandId, productId, date);


        assertTrue(result.isPresent());
        assertEquals(brandId, result.get().getBrandId());
        assertEquals(productId, result.get().getProductId());
        assertEquals(BigDecimal.valueOf(35.5), result.get().getPrice());
    }

    @Test
    void shouldReturnEmptyIfNoApplicablePriceFound() {

        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.now();

        when(priceRepository.findApplicablePrice(brandId, productId, date))
                .thenReturn(Optional.empty());

        Optional<PriceModel> result = priceService.getApplicablePrice(brandId, productId, date);

        assertTrue(result.isEmpty());
    }
}