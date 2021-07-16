package ca.bc.gov.educ.api.program.service;

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

import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.entity.GraduationProgramCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;
import ca.bc.gov.educ.api.program.repository.GraduationProgramCodeRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementRepository;
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
	
	/*
	@Test
	public void testGetSpecificRuleDetails() {
		String ruleCode = "100";
		List<ProgramRequirementEntity> gradProgramRule = new ArrayList<ProgramRequirementEntity>();
		ProgramRequirementEntity ruleObj = new ProgramRequirementEntity();
		ruleObj.setProgramCode("2018-EN");
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		gradProgramRule.add(ruleObj);
		ruleObj = new ProgramRequirementEntity();
		ruleObj.setProgramCode("2018-EN");
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		gradProgramRule.add(ruleObj);
		
		List<GradSpecialProgramRulesEntity> gradSpecialProgramRule = new ArrayList<GradSpecialProgramRulesEntity>();
		GradSpecialProgramRulesEntity specialRuleObj = new GradSpecialProgramRulesEntity();
		specialRuleObj.setSpecialProgramID(new UUID(1, 1));
		specialRuleObj.setRuleCode("100");
		specialRuleObj.setRequirementName("ABC");
		gradSpecialProgramRule.add(specialRuleObj);
		specialRuleObj = new GradSpecialProgramRulesEntity();
		specialRuleObj.setSpecialProgramID(new UUID(1, 1));
		specialRuleObj.setRuleCode("100");
		specialRuleObj.setRequirementName("ABC");
		gradSpecialProgramRule.add(specialRuleObj);
		
		GradSpecialProgramEntity specialProgramObj = new GradSpecialProgramEntity();
		specialProgramObj.setProgramCode("2018-EN");
		specialProgramObj.setSpecialProgramCode("FI");
		specialProgramObj.setSpecialProgramName("French Immersion");
		specialProgramObj.setId(new UUID(1, 1));
		
		UUID specialProgramID = new UUID(1, 1);
		
		Mockito.when(programRequirementRepository.findByRuleCode(ruleCode)).thenReturn(gradProgramRule);
		Mockito.when(gradSpecialProgramRulesRepository.findByRuleCode(ruleCode)).thenReturn(gradSpecialProgramRule);
		Mockito.when(gradSpecialProgramRepository.findById(specialProgramID)).thenReturn(Optional.of(specialProgramObj));
		List<GradRuleDetails> result = programService.getSpecificRuleDetails(ruleCode);
		assertEquals(4,result.size());
	}
	
	@Test
	public void testGetSpecificRuleDetails_noAssociatedRuleDetails() {
		String ruleCode = "100";		
		Mockito.when(programRequirementRepository.findByRuleCode(ruleCode)).thenReturn(new ArrayList<ProgramRequirementEntity>());
		Mockito.when(gradSpecialProgramRulesRepository.findByRuleCode(ruleCode)).thenReturn(new ArrayList<GradSpecialProgramRulesEntity>());
		List<GradRuleDetails> result = programService.getSpecificRuleDetails(ruleCode);
		assertEquals(0,result.size());
	}
	*/
	
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
			// TODO Auto-generated catch block
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
	
	/*
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgram_exception() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Optional<GradSpecialProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(ent);
		programService.createGradSpecialProgram(gradSpecialProgram);
		
	}
	
	@Test
	public void testCreateGradSpecialProgram() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(Optional.empty());
		Mockito.when(gradSpecialProgramRepository.save(gradSpecialProgramEntity)).thenReturn(gradSpecialProgramEntity);
		programService.createGradSpecialProgram(gradSpecialProgram);
		
	}
	
	@Test
	public void testUpdateGradSpecialProgram() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGH");	
		GradSpecialProgramEntity toBeSaved = new GradSpecialProgramEntity();
		toBeSaved.setProgramCode("ABCD");
		toBeSaved.setId(new UUID(1, 1));
		toBeSaved.setSpecialProgramCode("FI");
		toBeSaved.setSpecialProgramName("EFGH");	
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Optional<GradSpecialProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(gradSpecialProgramRepository.findById(gradSpecialProgram.getId())).thenReturn(ent);
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(Optional.empty());
		Mockito.when(gradSpecialProgramRepository.save(gradSpecialProgramEntity)).thenReturn(toBeSaved);
		programService.updateGradSpecialPrograms(gradSpecialProgram);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgram_excpetion() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGHF");
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(Optional.empty());
		programService.updateGradSpecialPrograms(gradSpecialProgram);			
	}
	
	@Test
	public void testDeleteGradSpecialProgram() {
		UUID ruleID=new UUID(1, 1);
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.findById(ruleID)).thenReturn(Optional.of(gradSpecialProgramEntity));
		programService.deleteGradSpecialPrograms(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testDeleteGradSpecialProgram_exception() {
		UUID ruleID=new UUID(1, 1);
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.findById(ruleID)).thenReturn(Optional.empty());
		programService.deleteGradSpecialPrograms(ruleID);
	}
	
	@Test
	public void testGetAllSpecialProgramList_withProgramCode() {
		String programCode = "2018-EN";
		List<GradSpecialProgramEntity> gradProgramList = new ArrayList<>();
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));;
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		gradProgramList.add(obj);
		obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));;
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		gradProgramList.add(obj);
		Mockito.when(gradSpecialProgramRepository.findAll()).thenReturn(gradProgramList);
		programService.getAllSpecialProgramList(programCode);
	}
	
	@Test
	public void testGetAllSpecialProgramList() {
		List<GradSpecialProgramEntity> gradProgramList = new ArrayList<>();
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));;
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		gradProgramList.add(obj);
		obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));;
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		gradProgramList.add(obj);
		Mockito.when(gradSpecialProgramRepository.findAll()).thenReturn(gradProgramList);
		programService.getAllSpecialProgramList();
	}
	
	@Test
	public void testGetSpecialProgramByID() {
		UUID specialProgramID = new UUID(1, 1);
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));;
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		Mockito.when(gradSpecialProgramRepository.findById(specialProgramID)).thenReturn(Optional.of(obj));
		programService.getSpecialProgramByID(specialProgramID);
	}	
	
	@Test
	public void testGetSpecialProgram() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));
		obj.setProgramCode("2018-EN");
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");        
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode)).thenReturn(Optional.of(obj));
		programService.getSpecialProgram(programCode, specialProgramCode);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testGetSpecialProgram_exception() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode)).thenReturn(Optional.empty());
		programService.getSpecialProgram(programCode, specialProgramCode);
	}
	
	@Test
	public void testGetRequirementTypeByType() {
		String typeCode="M";
		List<ProgramRequirementEntity> gradProgramRuleList = new ArrayList<ProgramRequirementEntity>();
		ProgramRequirementEntity ruleObj = new ProgramRequirementEntity();
		ruleObj.setProgramCode("2018-EN");
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		ruleObj.setRequirementType("M");
		gradProgramRuleList.add(ruleObj);
		ruleObj = new ProgramRequirementEntity();
		ruleObj.setProgramCode("2018-EN");
		ruleObj.setRuleCode("200");
		ruleObj.setRequirementName("ABC");
		ruleObj.setRequirementType("M");
		gradProgramRuleList.add(ruleObj);
		Mockito.when(programRequirementRepository.existsByRequirementTypeCode(typeCode)).thenReturn(gradProgramRuleList);
		boolean result = programService.getRequirementByRequirementType(typeCode);
		assertEquals(true, result);
	}
	
	@Test
	public void testGetRequirementTypeByType_emptyList() {
		String typeCode="M";
		Mockito.when(programRequirementRepository.existsByRequirementTypeCode(typeCode)).thenReturn(new ArrayList<>());
		boolean result = programService.getRequirementByRequirementType(typeCode);
		assertEquals(false, result);
	}
	
	@Test
	public void testDeleteGradSpecialProgramRules() {
		UUID ruleID=new UUID(1, 1);
		GradSpecialProgramRulesEntity gradSpecialProgramRulesEntity = new GradSpecialProgramRulesEntity();
		gradSpecialProgramRulesEntity.setRuleCode("100");
		gradSpecialProgramRulesEntity.setSpecialProgramID(new UUID(1, 1));
		Mockito.when(gradSpecialProgramRulesRepository.findById(ruleID)).thenReturn(Optional.of(gradSpecialProgramRulesEntity));
		programService.deleteGradSpecialProgramRules(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void deleteGradSpecialProgram_exception_exception() {
		UUID ruleID=new UUID(1, 1);
		Mockito.when(gradSpecialProgramRulesRepository.findById(ruleID)).thenReturn(Optional.empty());
		programService.deleteGradSpecialProgramRules(ruleID);
	}
	*/
}
