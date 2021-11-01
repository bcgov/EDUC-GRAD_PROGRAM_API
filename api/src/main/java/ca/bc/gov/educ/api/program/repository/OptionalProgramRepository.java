package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.OptionalProgramEntity;

@Repository
public interface OptionalProgramRepository extends JpaRepository<OptionalProgramEntity, UUID> {

    List<OptionalProgramEntity> findAll();

    @Query(value="select DISTINCT p.* from optional_program_requirement c inner join optional_program p on p.optionalProgramID = c.optionalProgramID where p.optionalProgramID=:optionalProgramID",nativeQuery=true)
	Optional<OptionalProgramEntity> findIfChildRecordsExists(@Valid UUID optionalProgramID);

	Optional<OptionalProgramEntity> findByGraduationProgramCodeAndOptProgramCode(String programCode, String optionalProgramCode);

	List<OptionalProgramEntity> findByGraduationProgramCode(String programCode);

}
