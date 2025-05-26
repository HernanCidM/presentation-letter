package com.capitole.presentation_letter.adapters.in.rest;

import com.capitole.presentation_letter.adapters.in.rest.dto.PriceResponseDTO;
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
        return priceUseCase.getApplicablePrice(brandId, productId, applicationDate)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private PriceResponseDTO toDto(PriceModel model) {
        PriceResponseDTO dto = new PriceResponseDTO();
        dto.setBrandId(model.getBrandID());
        dto.setProductId(model.getProductId());
        dto.setPriceList(model.getPriceList());
        dto.setStartDate(model.getStartDate());
        dto.setEndDate(model.getEndDate());
        dto.setPrice(model.getPrice());
        dto.setCurrency(model.getCurrency());
        return dto;
    }
}
