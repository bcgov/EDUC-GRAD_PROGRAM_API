package ca.bc.gov.educ.api.program.repository;

import ca.bc.gov.educ.api.program.model.entity.OptionalProgramCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalProgramCodeRepository extends JpaRepository<OptionalProgramCodeEntity, String> {
}
