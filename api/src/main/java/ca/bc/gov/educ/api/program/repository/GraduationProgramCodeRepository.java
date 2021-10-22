package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GraduationProgramCodeEntity;

@Repository
public interface GraduationProgramCodeRepository extends JpaRepository<GraduationProgramCodeEntity, String> {

    List<GraduationProgramCodeEntity> findAll();

    @Query(value="select DISTINCT p.* from program_requirement c inner join graduation_program_code p on p.GRADUATION_PROGRAM_CODE = c.GRADUATION_PROGRAM_CODE where p.GRADUATION_PROGRAM_CODE=:programCode",nativeQuery=true)
	Optional<GraduationProgramCodeEntity> findIfChildRecordsExists(@Valid String programCode);
    
    @Query(value="select DISTINCT p.* from optional_program_requirement c inner join graduation_program_code p on p.GRADUATION_PROGRAM_CODE = c.GRADUATION_PROGRAM_CODE where p.GRADUATION_PROGRAM_CODE=:programCode",nativeQuery=true)
	Optional<GraduationProgramCodeEntity> findIfOptionalProgramsExists(@Valid String programCode);

}
