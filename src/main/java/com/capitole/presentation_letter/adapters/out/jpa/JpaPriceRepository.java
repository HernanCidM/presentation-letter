package com.capitole.presentation_letter.adapters.out.jpa;

import com.capitole.presentation_letter.adapters.out.jpa.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, String> {
    List<PriceEntity> findByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
            Long brandId,
            Long productId,
            LocalDateTime dateTime1,
            LocalDateTime dateTime2
    );
}
