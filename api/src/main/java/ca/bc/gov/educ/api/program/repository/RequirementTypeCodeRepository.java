package ca.bc.gov.educ.api.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.RequirementTypeCodeEntity;

@Repository
public interface RequirementTypeCodeRepository extends JpaRepository<RequirementTypeCodeEntity, String> {

    List<RequirementTypeCodeEntity> findAll();

}
