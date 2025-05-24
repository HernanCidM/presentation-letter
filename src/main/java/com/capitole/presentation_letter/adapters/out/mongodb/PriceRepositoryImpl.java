package com.capitole.presentation_letter.adapters.out.mongodb;

import com.capitole.presentation_letter.domain.model.PriceModel;
import com.capitole.presentation_letter.domain.ports.out.PriceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final MongoPriceRepository mongoRepository;

    public PriceRepositoryImpl(MongoPriceRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Optional<PriceModel> findApplicablePrice(Long brandId, Long productId, LocalDateTime dateTime) {
        return mongoRepository.findByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
                        brandId, productId, dateTime, dateTime)
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPriority(), p1.getPriority()))
                .findFirst()
                .map(this::toDomainModel);
    }

    @Override
    public void saveAll(List<PriceModel> prices) {
        List<PriceModel> entities = prices.stream()
                .map(this::fromDomainModel)
                .toList();
        mongoRepository.saveAll(entities);
    }

    private PriceModel toDomainModel(PriceModel doc) {
        return new PriceModel(doc.getBrandID(), doc.getProductId(), doc.getStartDate(), doc.getEndDate(),
                doc.getPriceList(), doc.getPriority(), doc.getPrice(), doc.getCurrency());
    }

    private PriceModel fromDomainModel(PriceModel price) {
        PriceModel doc = new PriceModel();
        doc.setBrandID(price.getBrandID());
        doc.setProductId(price.getProductId());
        doc.setStartDate(price.getStartDate());
        doc.setEndDate(price.getEndDate());
        doc.setPriceList(price.getPriceList());
        doc.setPriority(price.getPriority());
        doc.setPrice(price.getPrice());
        doc.setCurrency(price.getCurrency());
        return doc;
    }
}
