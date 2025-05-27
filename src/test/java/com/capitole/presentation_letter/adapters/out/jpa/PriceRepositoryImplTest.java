package com.capitole.presentation_letter.adapters.out.jpa;

import com.capitole.presentation_letter.adapters.out.jpa.entity.PriceEntity;
import com.capitole.presentation_letter.domain.model.PriceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRepositoryImplTest {
    private JpaPriceRepository jpaRepository;
    private PriceRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(JpaPriceRepository.class);
        repository = new PriceRepositoryImpl(jpaRepository);
    }

    @Test
    void shouldReturnPriceModelWithHighestPriority() {
        // given
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.now();

        PriceEntity lowPriority = PriceEntity.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .priority(0)
                .priceList(1L)
                .price(BigDecimal.valueOf(30.50))
                .currency("EUR")
                .build();

        PriceEntity highPriority = PriceEntity.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .priority(1)
                .priceList(2L)
                .price(BigDecimal.valueOf(50.00))
                .currency("EUR")
                .build();

        when(jpaRepository.findByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
                brandId, productId, date, date))
                .thenReturn(List.of(lowPriority, highPriority));

        Optional<PriceModel> result = repository.findApplicablePrice(brandId, productId, date);

        assertTrue(result.isPresent());
        assertEquals(BigDecimal.valueOf(50.00), result.get().getPrice());
        assertEquals(1, result.get().getPriority());
    }

    @Test
    void shouldReturnEmptyWhenNoPricesFound() {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.now();

        when(jpaRepository.findByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
                brandId, productId, date, date)).thenReturn(List.of());
        Optional<PriceModel> result = repository.findApplicablePrice(brandId, productId, date);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCallSaveAll() {
        PriceEntity price1 = new PriceEntity();
        PriceEntity price2 = new PriceEntity();
        List<PriceEntity> prices = List.of(price1, price2);
        repository.saveAll(prices);

        verify(jpaRepository, times(1)).saveAll(prices);
    }

}