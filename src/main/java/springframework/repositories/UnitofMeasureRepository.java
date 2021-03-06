package springframework.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitofMeasureRepository extends CrudRepository<UnitOfMeasure,Long> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
