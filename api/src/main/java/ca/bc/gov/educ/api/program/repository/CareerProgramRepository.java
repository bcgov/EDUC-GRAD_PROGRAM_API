package ca.bc.gov.educ.api.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.CareerProgramEntity;

@Repository
public interface CareerProgramRepository extends JpaRepository<CareerProgramEntity, String> {

    List<CareerProgramEntity> findAll();

}
