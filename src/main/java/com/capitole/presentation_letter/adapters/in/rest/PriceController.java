package com.capitole.presentation_letter.adapters.in.rest;

import com.capitole.presentation_letter.adapters.in.rest.dto.PriceResponseDTO;
import com.capitole.presentation_letter.domain.exceptions.PriceNotFoundException;
import com.capitole.presentation_letter.domain.model.PriceModel;
import com.capitole.presentation_letter.domain.ports.in.PriceUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceUseCase priceUseCase;

    public PriceController(PriceUseCase priceUseCase) {
        this.priceUseCase = priceUseCase;
    }

    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
            @RequestParam Long brandId,
            @RequestParam Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate
    ) {
        PriceModel model = priceUseCase.getApplicablePrice(brandId, productId, applicationDate)
                .orElseThrow(() -> new PriceNotFoundException(brandId ,productId, applicationDate));

        return ResponseEntity.ok(toDto(model));
    }

    private PriceResponseDTO toDto(PriceModel model) {
        return PriceResponseDTO.builder()
                .brandId(model.getBrandId())
                .productId(model.getProductId())
                .priceList(model.getPriceList())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .price(model.getPrice())
                .currency(model.getCurrency())
                .build();
    }
}
