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

    @Query(value="select DISTINCT p.* from grad_special_program_rules c inner join grad_special_program p on p.id = c.FK_GRAD_SPECIAL_PROGRAM_ID where p.id=:specialProgramID",nativeQuery=true)
	Optional<OptionalProgramEntity> findIfChildRecordsExists(@Valid UUID specialProgramID);

	Optional<OptionalProgramEntity> findByGraduationProgramCodeAndOptProgramCode(String programCode, String specialProgramCode);

	List<OptionalProgramEntity> findByProgramCode(String programCode);

}
