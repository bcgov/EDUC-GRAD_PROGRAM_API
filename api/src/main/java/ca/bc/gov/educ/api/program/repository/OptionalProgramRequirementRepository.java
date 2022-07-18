package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementEntity;

@Repository
public interface OptionalProgramRequirementRepository extends JpaRepository<OptionalProgramRequirementEntity, UUID> {

    @Query("select c from OptionalProgramRequirementEntity c where c.optionalProgramID.optionalProgramID=:optionalProgramID")
	List<OptionalProgramRequirementEntity> findByOptionalProgramID(UUID optionalProgramID);

    @Query("select p.id from OptionalProgramRequirementEntity p inner join OptionalProgramRequirementCodeEntity c on p.optionalProgramRequirementCode.optProReqCode = c.optProReqCode where c.optProReqCode=:ruleCode and p.optionalProgramID=:optionalProgramID")
	UUID findIdByRuleCode(String ruleCode,UUID optionalProgramID);
    
    @Query("select p from OptionalProgramRequirementEntity p inner join OptionalProgramRequirementCodeEntity c on p.optionalProgramRequirementCode.optProReqCode = c.optProReqCode where c.optProReqCode=:ruleCode")
    List<OptionalProgramRequirementEntity> findByRuleCode(String ruleCode);

    @Query("select p from OptionalProgramRequirementEntity p inner join OptionalProgramRequirementCodeEntity c on p.optionalProgramRequirementCode.optProReqCode = c.optProReqCode where c.traxReqNumber=:traxReqNumber")
    List<OptionalProgramRequirementEntity> findByTraxReqNumber(String traxReqNumber);

    @Query("select p from OptionalProgramRequirementEntity p inner join OptionalProgramRequirementCodeEntity c on p.optionalProgramRequirementCode.optProReqCode = c.optProReqCode where c.requirementTypeCode=:typeCode")
	List<OptionalProgramRequirementEntity> existsByRequirementTypeCode(String typeCode);

}
