package com.capitole.presentation_letter.adapters.out.mongodb;

import com.capitole.presentation_letter.domain.model.PriceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MongoPriceRepository extends MongoRepository<PriceModel, String> {
    List<PriceModel> findByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
            Long brandId,
            Long productId,
            LocalDateTime dateTime1,
            LocalDateTime dateTime2
    );
}
