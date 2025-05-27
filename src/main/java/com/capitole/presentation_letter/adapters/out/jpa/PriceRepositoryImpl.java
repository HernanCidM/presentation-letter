package com.capitole.presentation_letter.adapters.out.jpa;

import com.capitole.presentation_letter.adapters.out.jpa.entity.PriceEntity;
import com.capitole.presentation_letter.domain.model.PriceModel;
import com.capitole.presentation_letter.domain.ports.out.PriceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaRepository;

    public PriceRepositoryImpl(JpaPriceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<PriceModel> findApplicablePrice(Long brandId, Long productId, LocalDateTime dateTime) {
        return jpaRepository.findByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
                        brandId, productId, dateTime, dateTime)
                .stream()
                .max(Comparator.comparingInt(PriceEntity::getPriority))
                .map(this::toDomainModel);
    }

    @Override
    public void saveAll(List<PriceEntity> prices) {
        jpaRepository.saveAll(prices);
    }

    private PriceModel toDomainModel(PriceEntity entity) {
        return new PriceModel(
                entity.getBrandId(), entity.getProductId(), entity.getStartDate(), entity.getEndDate(),
                entity.getPriceList(), entity.getPriority(), entity.getPrice(), entity.getCurrency());
    }

    private PriceEntity fromDomainModel(PriceModel model) {
        return PriceEntity.builder()
                .brandId(model.getBrandId())
                .productId(model.getProductId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .priceList(model.getPriceList())
                .priority(model.getPriority())
                .price(model.getPrice())
                .currency(model.getCurrency())
                .build();
    }
}