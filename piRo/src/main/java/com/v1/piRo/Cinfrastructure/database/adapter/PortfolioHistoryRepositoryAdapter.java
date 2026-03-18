package com.v1.piRo.Cinfrastructure.database.adapter;
import com.v1.piRo.Cinfrastructure.database.entity.PortfolioHistoryJpaEntity;
import com.v1.piRo.Cinfrastructure.database.repository.PortfolioHistorySpringDataRepository;
import com.v1.piRo.Ddomain.PortfolioHistory;
import com.v1.piRo.Ddomain.repository.PortfolioHistoryRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PortfolioHistoryRepositoryAdapter implements PortfolioHistoryRepository {

    private final PortfolioHistorySpringDataRepository springDataRepo;

    public PortfolioHistoryRepositoryAdapter(PortfolioHistorySpringDataRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public void save(PortfolioHistory domainObj) {
        PortfolioHistoryJpaEntity entity = new PortfolioHistoryJpaEntity();
        entity.setId(domainObj.getId());
        entity.setRecordDate(domainObj.getRecordDate());
        entity.setTotalNetWorth(domainObj.getTotalNetWorth());

        springDataRepo.save(entity);
    }

    @Override
    public List<PortfolioHistory> findAllOrderByDateAsc() {
        return springDataRepo.findAllByOrderByRecordDateAsc().stream()
                .map(entity -> new PortfolioHistory(
                        entity.getId(),
                        entity.getRecordDate(),
                        entity.getTotalNetWorth()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PortfolioHistory> findByDate(LocalDate date) {
        return springDataRepo.findByRecordDate(date)
                .map(entity -> new PortfolioHistory(
                        entity.getId(),
                        entity.getRecordDate(),
                        entity.getTotalNetWorth()));
    }
}