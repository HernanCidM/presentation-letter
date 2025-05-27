package com.capitole.presentation_letter.adapters.in.rest;

import com.capitole.presentation_letter.domain.model.PriceModel;
import com.capitole.presentation_letter.domain.ports.in.PriceUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(PriceController.class)
class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceUseCase priceUseCase;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PriceUseCase priceUseCase() {
            return mock(PriceUseCase.class);
        }
    }

    @Test
    void shouldReturnPriceWhenFound() throws Exception {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        String dateStr = date.format(DateTimeFormatter.ISO_DATE_TIME);

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

        when(priceUseCase.getApplicablePrice(brandId, productId, date))
                .thenReturn(Optional.of(model));

        mockMvc.perform(get("/prices")
                        .param("brandId", brandId.toString())
                        .param("productId", productId.toString())
                        .param("applicationDate", dateStr)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void shouldReturn404WhenPriceNotFound() throws Exception {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        String dateStr = date.format(DateTimeFormatter.ISO_DATE_TIME);

        when(priceUseCase.getApplicablePrice(brandId, productId, date))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/prices")
                        .param("brandId", brandId.toString())
                        .param("productId", productId.toString())
                        .param("applicationDate", dateStr)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}