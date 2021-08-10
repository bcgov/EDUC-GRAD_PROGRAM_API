package ca.bc.gov.educ.api.program.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.program.model.dto.CareerProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramAlgorithmData;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgram;
import ca.bc.gov.educ.api.program.model.dto.RequirementTypeCode;
import ca.bc.gov.educ.api.program.model.entity.CareerProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GraduationProgramCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.entity.RequirementTypeCodeEntity;
import ca.bc.gov.educ.api.program.repository.CareerProgramRepository;
import ca.bc.gov.educ.api.program.repository.GraduationProgramCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementRepository;
import ca.bc.gov.educ.api.program.repository.RequirementTypeCodeRepository;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
import ca.bc.gov.educ.api.program.util.GradValidation;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProgramServiceTest {

	@Autowired
	private ProgramService programService;
	
	@MockBean
	private GraduationProgramCodeRepository graduationProgramCodeRepository;
	
	@MockBean
	private ProgramRequirementRepository programRequirementRepository;
	
	@MockBean
	private OptionalProgramRequirementRepository optionalProgramRequirementRepository;
	
	@MockBean
	private OptionalProgramRepository optionalProgramRepository;
	
	@MockBean
    private ProgramRequirementCodeRepository programRequirementCodeRepository; 
    
	@MockBean
    private OptionalProgramRequirementCodeRepository optionalProgramRequirementCodeRepository;

	@MockBean
	private CareerProgramRepository gradCareerProgramRepository;
	
	@MockBean
	private RequirementTypeCodeRepository requirementTypeCodeRepository;
	
	@Autowired
	GradValidation validation;
	
	@Mock
	WebClient webClient;
	
	@Test
	public void testGetAllProgramList() {
		List<GraduationProgramCodeEntity> gradProgramList = new ArrayList<>();
		GraduationProgramCodeEntity obj = new GraduationProgramCodeEntity();
		obj.setProgramCode("2018-EN");
		obj.setProgramName("2018 Graduation Program");
		gradProgramList.add(obj);
		obj = new GraduationProgramCodeEntity();
		obj.setProgramCode("1950-EN");
		obj.setProgramName("1950 Graduation Program");
		gradProgramList.add(obj);
		Mockito.when(graduationProgramCodeRepository.findAll()).thenReturn(gradProgramList);
		programService.getAllProgramList();
	}
	
	@Test
	public void testGetSpecificProgramCode() {
		String programCode = "2018-EN";
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramCode("2018-EN");
		obj.setProgramName("2018 Graduation Program");
		GraduationProgramCodeEntity objEntity = new GraduationProgramCodeEntity();
		objEntity.setProgramCode("1950-EN");
		objEntity.setProgramName("1950 Graduation Program");
		Optional<GraduationProgramCodeEntity> ent = Optional.of(objEntity);
		Mockito.when(graduationProgramCodeRepository.findById(programCode)).thenReturn(ent);
		programService.getSpecificProgram(programCode);
		Mockito.verify(graduationProgramCodeRepository).findById(programCode);
	}
	
	@Test
	public void testGetSpecificProgramCodeReturnsNull() {
		String programCode = "2018-EN";
		Mockito.when(graduationProgramCodeRepository.findById(programCode)).thenReturn(Optional.empty());
		programService.getSpecificProgram(programCode);
		Mockito.verify(graduationProgramCodeRepository).findById(programCode);
	}
	
	
	@Test
	public void testGetSpecificRuleDetails() {
		String ruleCode = "100";
		List<ProgramRequirementEntity> gradProgramRule = new ArrayList<ProgramRequirementEntity>();
		ProgramRequirementEntity ruleObj = new ProgramRequirementEntity();
		ruleObj.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity code = new ProgramRequirementCodeEntity();
		code.setProReqCode("100");
		ruleObj.setProgramRequirementCode(code);
		gradProgramRule.add(ruleObj);
		ruleObj = new ProgramRequirementEntity();
		ruleObj.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity code2 = new ProgramRequirementCodeEntity();
		code2.setProReqCode("100");
		ruleObj.setProgramRequirementCode(code2);
		gradProgramRule.add(ruleObj);
		
		List<OptionalProgramRequirementEntity> gradSpecialProgramRule = new ArrayList<OptionalProgramRequirementEntity>();
		OptionalProgramRequirementEntity specialRuleObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity opE = new OptionalProgramEntity();
		opE.setOptionalProgramID(new UUID(1, 1));
		specialRuleObj.setOptionalProgramID(opE);
		OptionalProgramRequirementCodeEntity code3 = new OptionalProgramRequirementCodeEntity();
		code3.setOptProReqCode("100");
		specialRuleObj.setOptionalProgramRequirementCode(code3);
		gradSpecialProgramRule.add(specialRuleObj);
		specialRuleObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(1, 1));;
		specialRuleObj.setOptionalProgramID(opE2);
		OptionalProgramRequirementCodeEntity code4 = new OptionalProgramRequirementCodeEntity();
		code4.setOptProReqCode("100");
		specialRuleObj.setOptionalProgramRequirementCode(code4);
		gradSpecialProgramRule.add(specialRuleObj);
		
		OptionalProgramEntity specialProgramObj = new OptionalProgramEntity();
		specialProgramObj.setGraduationProgramCode("2018-EN");
		specialProgramObj.setOptProgramCode("FI");
		specialProgramObj.setOptionalProgramName("French Immersion");
		specialProgramObj.setOptionalProgramID(new UUID(1, 1));
		
		UUID specialProgramID = new UUID(1, 1);
		
		Mockito.when(programRequirementRepository.findByRuleCode(ruleCode)).thenReturn(gradProgramRule);
		Mockito.when(optionalProgramRequirementRepository.findByRuleCode(ruleCode)).thenReturn(gradSpecialProgramRule);
		Mockito.when(optionalProgramRepository.findById(specialProgramID)).thenReturn(Optional.of(specialProgramObj));
		List<GradRuleDetails> result = programService.getSpecificRuleDetails(ruleCode);
		assertEquals(4,result.size());
	}
	
	
	
	@Test
	public void testGetSpecificRuleDetails_noAssociatedRuleDetails() {
		String ruleCode = "100";		
		Mockito.when(programRequirementRepository.findByRuleCode(ruleCode)).thenReturn(new ArrayList<ProgramRequirementEntity>());
		Mockito.when(optionalProgramRequirementRepository.findByRuleCode(ruleCode)).thenReturn(new ArrayList<OptionalProgramRequirementEntity>());
		List<GradRuleDetails> result = programService.getSpecificRuleDetails(ruleCode);
		assertEquals(0,result.size());
	}
	
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradProgram_exception() {
		GraduationProgramCode gradProgram = new GraduationProgramCode();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGH");
		
		GraduationProgramCodeEntity graduationProgramCodeEntity = new GraduationProgramCodeEntity();
		graduationProgramCodeEntity.setProgramCode("ABCD");
		graduationProgramCodeEntity.setProgramName("EFGH");
		Optional<GraduationProgramCodeEntity> ent = Optional.of(graduationProgramCodeEntity);
		Mockito.when(graduationProgramCodeRepository.findById(gradProgram.getProgramCode())).thenReturn(ent);
		programService.createGradProgram(gradProgram);
		
	}
	
	@Test
	public void testCreateGradProgram() {
		GraduationProgramCode gradProgram = new GraduationProgramCode();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGH");	
		try {
			gradProgram.setEffectiveDate(new SimpleDateFormat("yyyy/MM/dd").parse("2021/07/15"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		GraduationProgramCodeEntity graduationProgramCodeEntity = new GraduationProgramCodeEntity();
		graduationProgramCodeEntity.setProgramCode("ABCD");
		graduationProgramCodeEntity.setProgramName("EFGH");
		graduationProgramCodeEntity.setDisplayOrder(0);
		
		try {
			graduationProgramCodeEntity.setExpiryDate(new SimpleDateFormat("yyyy/MM/dd").parse("2099/12/31"));
			graduationProgramCodeEntity.setEffectiveDate(new SimpleDateFormat("yyyy/MM/dd").parse("2021/07/15"));
		} catch (ParseException e) {
			e.getMessage();
		}
		Mockito.when(graduationProgramCodeRepository.findById(gradProgram.getProgramCode())).thenReturn(Optional.empty());
		Mockito.when(graduationProgramCodeRepository.save(graduationProgramCodeEntity)).thenReturn(graduationProgramCodeEntity);
		programService.createGradProgram(gradProgram);
		
	}
	
	@Test
	public void testUpdateGradProgram() {
		GraduationProgramCode gradProgram = new GraduationProgramCode();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGHF");	
		GraduationProgramCodeEntity toBeSaved = new GraduationProgramCodeEntity();
		toBeSaved.setProgramCode("ABCD");
		toBeSaved.setProgramName("EFGHF");	
		
		GraduationProgramCodeEntity graduationProgramCodeEntity = new GraduationProgramCodeEntity();
		graduationProgramCodeEntity.setProgramCode("ABCD");
		graduationProgramCodeEntity.setProgramName("EFGH");
		Optional<GraduationProgramCodeEntity> ent = Optional.of(graduationProgramCodeEntity);
		Mockito.when(graduationProgramCodeRepository.findById(gradProgram.getProgramCode())).thenReturn(ent);
		Mockito.when(graduationProgramCodeRepository.save(graduationProgramCodeEntity)).thenReturn(toBeSaved);
		programService.updateGradProgram(gradProgram);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgram_excpetion() {
		GraduationProgramCode gradProgram = new GraduationProgramCode();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGHF");
		Mockito.when(graduationProgramCodeRepository.findById(gradProgram.getProgramCode())).thenReturn(Optional.empty());
		programService.updateGradProgram(gradProgram);			
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgram_exception() {
		GraduationProgramCode gradProgram = new GraduationProgramCode();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGHF");
		Mockito.when(graduationProgramCodeRepository.findById(gradProgram.getProgramCode())).thenReturn(Optional.empty());
		programService.updateGradProgram(gradProgram);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void deleteGradProgram_exception_childrecords() {
		String programCode="2018-EN";
		GraduationProgramCodeEntity graduationProgramCodeEntity = new GraduationProgramCodeEntity();
		graduationProgramCodeEntity.setProgramCode("ABCD");
		graduationProgramCodeEntity.setProgramName("EFGH");
		Optional<GraduationProgramCodeEntity> ent = Optional.of(graduationProgramCodeEntity);
		Mockito.when(graduationProgramCodeRepository.findIfChildRecordsExists(programCode)).thenReturn(ent);
		programService.deleteGradPrograms(programCode);
	}
	
	@Test
	public void testDeleteGradProgram() {
		String programCode="2018-EN";
		GraduationProgramCodeEntity graduationProgramCodeEntity = new GraduationProgramCodeEntity();
		graduationProgramCodeEntity.setProgramCode("ABCD");
		graduationProgramCodeEntity.setProgramName("EFGH");
		Mockito.when(graduationProgramCodeRepository.findIfChildRecordsExists(programCode)).thenReturn(Optional.empty());
		Mockito.when(graduationProgramCodeRepository.findIfSpecialProgramsExists(programCode)).thenReturn(Optional.empty());
		programService.deleteGradPrograms(programCode);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testDeleteGradProgram_exception_specialprogramcheck() {
		String programCode="2018-EN";
		GraduationProgramCodeEntity graduationProgramCodeEntity = new GraduationProgramCodeEntity();
		graduationProgramCodeEntity.setProgramCode("ABCD");
		graduationProgramCodeEntity.setProgramName("EFGH");
		Optional<GraduationProgramCodeEntity> ent = Optional.of(graduationProgramCodeEntity);
		Mockito.when(graduationProgramCodeRepository.findIfChildRecordsExists(programCode)).thenReturn(Optional.empty());
		Mockito.when(graduationProgramCodeRepository.findIfSpecialProgramsExists(programCode)).thenReturn(ent);
		programService.deleteGradPrograms(programCode);
	}
	
	@Test
	public void testDeleteGradProgramRules() {
		UUID ruleID=new UUID(1, 1);
		ProgramRequirementEntity gradProgramRulesEntity = new ProgramRequirementEntity();
		ProgramRequirementCodeEntity code = new ProgramRequirementCodeEntity();
		code.setProReqCode("100");
		gradProgramRulesEntity.setProgramRequirementCode(code);
		gradProgramRulesEntity.setGraduationProgramCode("2018-EN");
		Mockito.when(programRequirementRepository.findById(ruleID)).thenReturn(Optional.of(gradProgramRulesEntity));
		programService.deleteGradProgramRules(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void deleteGradProgram_exception_exception() {
		UUID ruleID=new UUID(1, 1);
		Mockito.when(programRequirementRepository.findById(ruleID)).thenReturn(Optional.empty());
		programService.deleteGradProgramRules(ruleID);
	}
	
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgram_exception() {
		OptionalProgram gradSpecialProgram = new OptionalProgram();
		gradSpecialProgram.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgram.setOptProgramCode("FI");
		gradSpecialProgram.setGraduationProgramCode("ABCD");
		gradSpecialProgram.setOptionalProgramName("EFGH");
		
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Optional<OptionalProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(gradSpecialProgram.getGraduationProgramCode(),gradSpecialProgram.getOptProgramCode())).thenReturn(ent);
		programService.createGradSpecialProgram(gradSpecialProgram);
		
	}
	
	@Test
	public void testCreateGradSpecialProgram() {
		OptionalProgram gradSpecialProgram = new OptionalProgram();
		gradSpecialProgram.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgram.setOptProgramCode("FI");
		gradSpecialProgram.setGraduationProgramCode("ABCD");
		gradSpecialProgram.setOptionalProgramName("EFGH");
		
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(gradSpecialProgram.getGraduationProgramCode(),gradSpecialProgram.getOptProgramCode())).thenReturn(Optional.empty());
		Mockito.when(optionalProgramRepository.save(gradSpecialProgramEntity)).thenReturn(gradSpecialProgramEntity);
		programService.createGradSpecialProgram(gradSpecialProgram);
		
	}
	
	@Test
	public void testUpdateGradSpecialProgram() {
		OptionalProgram gradSpecialProgram = new OptionalProgram();
		gradSpecialProgram.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgram.setOptProgramCode("FI");
		gradSpecialProgram.setGraduationProgramCode("ABCD");
		gradSpecialProgram.setOptionalProgramName("EFGH");
		OptionalProgramEntity toBeSaved = new OptionalProgramEntity();
		toBeSaved.setOptionalProgramID(new UUID(1, 1));
		toBeSaved.setOptProgramCode("FI");
		toBeSaved.setGraduationProgramCode("ABCD");
		toBeSaved.setOptionalProgramName("EFGH");
		
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Optional<OptionalProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(optionalProgramRepository.findById(gradSpecialProgram.getOptionalProgramID())).thenReturn(ent);
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(gradSpecialProgram.getGraduationProgramCode(),gradSpecialProgram.getOptProgramCode())).thenReturn(Optional.empty());
		Mockito.when(optionalProgramRepository.save(gradSpecialProgramEntity)).thenReturn(toBeSaved);
		programService.updateGradSpecialPrograms(gradSpecialProgram);
		
	}
	
	@Test
	public void testUpdateGradSpecialProgram_optionalProgramChanged() {
		validation.clear();
		OptionalProgram gradSpecialProgram = new OptionalProgram();
		gradSpecialProgram.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgram.setOptProgramCode("FI");
		gradSpecialProgram.setGraduationProgramCode("ABCD");
		gradSpecialProgram.setOptionalProgramName("EFGH");
		OptionalProgramEntity toBeSaved = new OptionalProgramEntity();
		toBeSaved.setOptionalProgramID(new UUID(1, 1));
		toBeSaved.setOptProgramCode("FI");
		toBeSaved.setGraduationProgramCode("ABCD");
		toBeSaved.setOptionalProgramName("EFGH");
		
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("DD");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Optional<OptionalProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(optionalProgramRepository.findById(gradSpecialProgram.getOptionalProgramID())).thenReturn(ent);
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(gradSpecialProgram.getGraduationProgramCode(),gradSpecialProgram.getOptProgramCode())).thenReturn(Optional.of(gradSpecialProgramEntity));
		Mockito.when(optionalProgramRepository.save(gradSpecialProgramEntity)).thenReturn(toBeSaved);
		
		try {
			programService.updateGradSpecialPrograms(gradSpecialProgram);
		} catch (GradBusinessRuleException e) {
			List<String> errors = validation.getErrors();
			assertEquals(1, errors.size());
			return;
		}		
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode() {
		String programCode="2018-EN";
		String optionalProgramCode="FI";
		
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("2018-EN");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("French Immersion");
		
		List<OptionalProgramRequirementEntity> gradProgramRuleList = new ArrayList<OptionalProgramRequirementEntity>();
		OptionalProgramRequirementEntity ruleObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity opE = new OptionalProgramEntity();
		opE.setOptionalProgramID(new UUID(1, 1));
		ruleObj.setOptionalProgramID(opE);
		OptionalProgramRequirementCodeEntity code = new OptionalProgramRequirementCodeEntity();
		code.setOptProReqCode("100");
		ruleObj.setOptionalProgramRequirementCode(code);
		gradProgramRuleList.add(ruleObj);
		ruleObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(2, 2));
		ruleObj.setOptionalProgramID(opE2);
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");
		ruleObj.setOptionalProgramRequirementCode(code2);
		gradProgramRuleList.add(ruleObj);
		
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, optionalProgramCode)).thenReturn(Optional.of(gradSpecialProgramEntity));
		Mockito.when(optionalProgramRequirementRepository.findByOptionalProgramID(gradSpecialProgramEntity.getOptionalProgramID())).thenReturn(gradProgramRuleList);
		
		programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, optionalProgramCode);
	}
	
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgram_excpetion() {
		OptionalProgram gradSpecialProgram = new OptionalProgram();
		gradSpecialProgram.setGraduationProgramCode("ABCD");
		gradSpecialProgram.setOptionalProgramName("EFGHF");
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(gradSpecialProgram.getGraduationProgramCode(),gradSpecialProgram.getOptProgramCode())).thenReturn(Optional.empty());
		programService.updateGradSpecialPrograms(gradSpecialProgram);			
	}
	
	
	@Test
	public void testDeleteGradSpecialProgram() {
		UUID ruleID=new UUID(1, 1);
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.findById(ruleID)).thenReturn(Optional.of(gradSpecialProgramEntity));
		programService.deleteGradSpecialPrograms(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testDeleteGradSpecialProgram_exception() {
		UUID ruleID=new UUID(1, 1);
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.findById(ruleID)).thenReturn(Optional.empty());
		programService.deleteGradSpecialPrograms(ruleID);
	}
	
		
	@Test
	public void testGetAllSpecialProgramList() {
		List<OptionalProgramEntity> gradProgramList = new ArrayList<>();
		OptionalProgramEntity obj = new OptionalProgramEntity();
		obj.setOptionalProgramID(new UUID(1, 1));;
		obj.setOptionalProgramName("2018 Graduation Program");
		obj.setOptProgramCode("FI");
		gradProgramList.add(obj);
		obj = new OptionalProgramEntity();
		obj.setOptionalProgramID(new UUID(1, 1));;
		obj.setOptionalProgramName("2018 Graduation Program");
		obj.setOptProgramCode("FI");
		gradProgramList.add(obj);
		Mockito.when(optionalProgramRepository.findAll()).thenReturn(gradProgramList);
		programService.getAllSpecialProgramList();
	}
	
	@Test
	public void testGetSpecialProgramByID() {
		UUID specialProgramID = new UUID(1, 1);
		OptionalProgramEntity obj = new OptionalProgramEntity();
		obj.setOptionalProgramID(new UUID(1, 1));;
		obj.setOptionalProgramName("2018 Graduation Program");
		obj.setOptProgramCode("FI");
		Mockito.when(optionalProgramRepository.findById(specialProgramID)).thenReturn(Optional.of(obj));
		programService.getSpecialProgramByID(specialProgramID);
	}	
	
	@Test
	public void testGetSpecialProgram() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		OptionalProgramEntity obj = new OptionalProgramEntity();
		obj.setOptionalProgramID(new UUID(1, 1));
		obj.setGraduationProgramCode("2018-EN");
		obj.setOptionalProgramName("2018 Graduation Program");
		obj.setOptProgramCode("FI");        
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, specialProgramCode)).thenReturn(Optional.of(obj));
		programService.getSpecialProgram(programCode, specialProgramCode);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testGetSpecialProgram_exception() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, specialProgramCode)).thenReturn(Optional.empty());
		programService.getSpecialProgram(programCode, specialProgramCode);
	}
	
	@Test
	public void testDeleteGradSpecialProgramRules() {
		UUID ruleID=new UUID(1, 1);
		OptionalProgramRequirementEntity gradSpecialProgramRulesEntity = new OptionalProgramRequirementEntity();
		OptionalProgramRequirementCodeEntity code = new OptionalProgramRequirementCodeEntity();
		code.setOptProReqCode("100");
		gradSpecialProgramRulesEntity.setOptionalProgramRequirementCode(code);
		OptionalProgramEntity opE = new OptionalProgramEntity();
		opE.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramRulesEntity.setOptionalProgramID(opE);
		Mockito.when(optionalProgramRequirementRepository.findById(ruleID)).thenReturn(Optional.of(gradSpecialProgramRulesEntity));
		programService.deleteGradSpecialProgramRules(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void deleteGradSpecialProgram_exception_exception() {
		UUID ruleID=new UUID(1, 1);
		Mockito.when(optionalProgramRequirementRepository.findById(ruleID)).thenReturn(Optional.empty());
		programService.deleteGradSpecialProgramRules(ruleID);
	}
	
	@Test
	public void testGetAllProgramRequirementCodeList() {
		List<ProgramRequirementCodeEntity> gradProgramList = new ArrayList<>();
		ProgramRequirementCodeEntity obj = new ProgramRequirementCodeEntity();
		obj.setProReqCode("300");
		obj.setDescription("2018 Graduation Program");
		gradProgramList.add(obj);
		obj = new ProgramRequirementCodeEntity();
		obj.setProReqCode("300");
		obj.setDescription("2018 Graduation Program");
		gradProgramList.add(obj);
		Mockito.when(programRequirementCodeRepository.findAll()).thenReturn(gradProgramList);
		programService.getAllProgramRequirementCodeList();
	}
	
	@Test
	public void testGetAllOptionalProgramRequirementCodeList() {
		List<OptionalProgramRequirementCodeEntity> gradProgramList = new ArrayList<>();
		OptionalProgramRequirementCodeEntity obj = new OptionalProgramRequirementCodeEntity();
		obj.setOptProReqCode("300");
		obj.setDescription("2018 Graduation Program");
		gradProgramList.add(obj);
		obj = new OptionalProgramRequirementCodeEntity();
		obj.setOptProReqCode("400");
		obj.setDescription("2018 Graduation Program");
		gradProgramList.add(obj);
		Mockito.when(optionalProgramRequirementCodeRepository.findAll()).thenReturn(gradProgramList);
		programService.getAllOptionalProgramRequirementCodeList();
	}
	
	@Test
	public void testGetAllCareerProgramsCodeList() {
		List<CareerProgramEntity> gradCareerProgramList = new ArrayList<>();
		CareerProgramEntity obj = new CareerProgramEntity();
		obj.setCode("AY");
		obj.setDescription("Archaeology");
		gradCareerProgramList.add(obj);
		obj = new CareerProgramEntity();
		obj.setCode("BE");
		obj.setDescription("Business Education");
		gradCareerProgramList.add(obj);
		Mockito.when(gradCareerProgramRepository.findAll()).thenReturn(gradCareerProgramList);
		programService.getAllCareerProgramCodeList();
	}
	
	@Test
	public void testGetSpecificCareerProgramCode() {
		String cpcCode = "AY";
		CareerProgram obj = new CareerProgram();
		obj.setCode("AY");
		obj.setDescription("Archaeology");
		obj.toString();
		CareerProgramEntity objEntity = new CareerProgramEntity();
		objEntity.setCode("AY");
		objEntity.setDescription("Archaeology");
		Optional<CareerProgramEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCareerProgramRepository.findById(cpcCode)).thenReturn(ent);
		programService.getSpecificCareerProgramCode(cpcCode);
	}
	
	@Test
	public void testGetSpecificCareerProgramCodeReturnsNull() {
		String cpcCode = "AZ";
		Mockito.when(gradCareerProgramRepository.findById(cpcCode)).thenReturn(Optional.empty());
		programService.getSpecificCareerProgramCode(cpcCode);
	}
	
	@Test
	public void testGetAllAlgorithmData() {
		String optionalProgramCode="FI";
		String programCode="2018-EN";
		
		List<ProgramRequirementEntity> gradProgramRuleList = new ArrayList<ProgramRequirementEntity>();
		ProgramRequirementEntity ruleObj = new ProgramRequirementEntity();
		ruleObj.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity code = new ProgramRequirementCodeEntity();
		code.setProReqCode("100");
		ruleObj.setProgramRequirementCode(code);
		gradProgramRuleList.add(ruleObj);
		ruleObj = new ProgramRequirementEntity();
		ruleObj.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity code2 = new ProgramRequirementCodeEntity();
		code2.setProReqCode("200");
		ruleObj.setProgramRequirementCode(code2);
		gradProgramRuleList.add(ruleObj);
    	Mockito.when(programRequirementRepository.findByGraduationProgramCode(programCode)).thenReturn(gradProgramRuleList);
    	
    	OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("2018-EN");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("French Immersion");
		
		List<OptionalProgramRequirementEntity> gradOptionalProgramRuleList = new ArrayList<OptionalProgramRequirementEntity>();
		OptionalProgramRequirementEntity ruleOptionalObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity opE = new OptionalProgramEntity();
		opE.setOptionalProgramID(new UUID(1, 1));
		ruleOptionalObj.setOptionalProgramID(opE);
		OptionalProgramRequirementCodeEntity codeOp2 = new OptionalProgramRequirementCodeEntity();
		codeOp2.setOptProReqCode("100");
		ruleOptionalObj.setOptionalProgramRequirementCode(codeOp2);
		gradOptionalProgramRuleList.add(ruleOptionalObj);
		ruleOptionalObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(2, 2));
		ruleOptionalObj.setOptionalProgramID(opE2);
		OptionalProgramRequirementCodeEntity codeOp3 = new OptionalProgramRequirementCodeEntity();
		codeOp3.setOptProReqCode("100");
		ruleOptionalObj.setOptionalProgramRequirementCode(codeOp3);
		gradOptionalProgramRuleList.add(ruleOptionalObj);
		
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, optionalProgramCode)).thenReturn(Optional.of(gradSpecialProgramEntity));
		Mockito.when(optionalProgramRequirementRepository.findByOptionalProgramID(gradSpecialProgramEntity.getOptionalProgramID())).thenReturn(gradOptionalProgramRuleList);
		
		GradProgramAlgorithmData data = programService.getAllAlgorithmData(programCode, optionalProgramCode);
		assertNotNull(data);
		assertEquals(2, data.getOptionalProgramRules().size());
    	
	}
	
	@Test
	public void testCreateRequirementTypeCode() {
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		RequirementTypeCodeEntity objEntity = new RequirementTypeCodeEntity();
		objEntity.setReqTypeCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(requirementTypeCodeRepository.findById(obj.getReqTypeCode())).thenReturn(Optional.empty());
		Mockito.when(requirementTypeCodeRepository.save(objEntity)).thenReturn(objEntity);
		programService.createRequirementTypeCode(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateRequirementTypeCode_codeAlreadyExists() {
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		RequirementTypeCodeEntity objEntity = new RequirementTypeCodeEntity();
		objEntity.setReqTypeCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<RequirementTypeCodeEntity> ent = Optional.of(objEntity);
		Mockito.when(requirementTypeCodeRepository.findById(obj.getReqTypeCode())).thenReturn(ent);
		programService.createRequirementTypeCode(obj);
		
	}
	
	@Test
	public void testUpdateRequirementTypeCode() {
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		RequirementTypeCodeEntity objEntity = new RequirementTypeCodeEntity();
		objEntity.setReqTypeCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<RequirementTypeCodeEntity> ent = Optional.of(objEntity);
		Mockito.when(requirementTypeCodeRepository.findById(obj.getReqTypeCode())).thenReturn(ent);
		Mockito.when(requirementTypeCodeRepository.save(objEntity)).thenReturn(objEntity);
		programService.updateRequirementTypeCode(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateRequirementTypeCode_codeAlreadyExists() {
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		RequirementTypeCodeEntity objEntity = new RequirementTypeCodeEntity();
		objEntity.setReqTypeCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(requirementTypeCodeRepository.findById(obj.getReqTypeCode())).thenReturn(Optional.empty());
		programService.updateRequirementTypeCode(obj);
		
	}
	
	@Test
	public void testGetAllRequirementTypesCodeList() {
		List<RequirementTypeCodeEntity> requirementTypeCodeList = new ArrayList<>();
		RequirementTypeCodeEntity obj = new RequirementTypeCodeEntity();
		obj.setReqTypeCode("M");
		obj.setDescription("MATCH");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		requirementTypeCodeList.add(obj);
		obj = new RequirementTypeCodeEntity();
		obj.setReqTypeCode("MC");
		obj.setDescription("MINCREDITS");
		requirementTypeCodeList.add(obj);
		Mockito.when(requirementTypeCodeRepository.findAll()).thenReturn(requirementTypeCodeList);
		programService.getAllRequirementTypeCodeList();
	}
	
	@Test
	public void testGetSpecificRequirementTypesCode() {
		String reqType = "M";
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("M");
		obj.setDescription("MATCH");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		RequirementTypeCodeEntity objEntity = new RequirementTypeCodeEntity();
		objEntity.setReqTypeCode("M");
		objEntity.setDescription("MATCH");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<RequirementTypeCodeEntity> ent = Optional.of(objEntity);
		Mockito.when(requirementTypeCodeRepository.findById(reqType)).thenReturn(ent);
		programService.getSpecificRequirementTypeCode(reqType);
	}
	
	@Test
	public void testGetSpecificRequirementTypesCodeReturnsNull() {
		String reqType = "E";
		Mockito.when(requirementTypeCodeRepository.findById(reqType)).thenReturn(Optional.empty());
		programService.getSpecificRequirementTypeCode(reqType);
	}
	
}
