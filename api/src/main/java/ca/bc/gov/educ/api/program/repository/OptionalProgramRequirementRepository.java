package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementEntity;

@Repository
public interface OptionalProgramRequirementRepository extends JpaRepository<OptionalProgramRequirementEntity, UUID> {

	List<OptionalProgramRequirementEntity> findBySpecialProgramID(UUID programCode);

    @Query("select c from GradSpecialProgramRulesEntity c where c.specialProgramID=:specialProgramID and "
    + "(:requirementType is null or c.requirementType=:requirementType)")
	List<OptionalProgramRequirementEntity> findBySpecialProgramIDAndRequirementType(UUID specialProgramID, String requirementType);

    @Query("select c.id from OptionalProgramRequirementEntity p inner join OptionalProgramRequirementCodeEntity c on p.optionalProgramRequirementCode.optProReqCode = c.optProReqCode where c.optProReqCode=:ruleCode and p.optionalProgramID=:specialProgramID")
	UUID findIdByRuleCode(String ruleCode,UUID specialProgramID);

    @Query("select p from OptionalProgramRequirementEntity p inner join OptionalProgramRequirementCodeEntity c on p.optionalProgramRequirementCode.optProReqCode = c.optProReqCode where c.requirementTypeCode=:typeCode")
	List<OptionalProgramRequirementEntity> existsByRequirementTypeCode(String typeCode);

}
