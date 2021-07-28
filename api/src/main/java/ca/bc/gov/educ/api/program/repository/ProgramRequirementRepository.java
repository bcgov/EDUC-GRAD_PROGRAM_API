package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;

@Repository
public interface ProgramRequirementRepository extends JpaRepository<ProgramRequirementEntity, UUID> {

    List<ProgramRequirementEntity> findByGraduationProgramCode(String programCode);

    @Query("select p.programRequirementID from ProgramRequirementEntity p  inner join ProgramRequirementCodeEntity c on p.programRequirementCode.proReqCode = c.proReqCode where p.programRequirementCode.proReqCode=:ruleCode and p.graduationProgramCode=:programCode")
	UUID findIdByRuleCode(String ruleCode,String programCode);

    @Query("select p from ProgramRequirementEntity p inner join ProgramRequirementCodeEntity c on p.programRequirementCode.proReqCode = c.proReqCode where c.requirementTypeCode=:typeCode")
	List<ProgramRequirementEntity> existsByRequirementTypeCode(String typeCode);

    @Query("select p from ProgramRequirementEntity p  inner join ProgramRequirementCodeEntity c on p.programRequirementCode.proReqCode = c.proReqCode where p.programRequirementCode.proReqCode=:ruleCode")
	List<ProgramRequirementEntity> findByRuleCode(String ruleCode);

}
