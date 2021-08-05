package ca.bc.gov.educ.api.program.controller;

import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import ca.bc.gov.educ.api.program.model.dto.CareerProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramAlgorithmData;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgram;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.dto.RequirementTypeCode;
import ca.bc.gov.educ.api.program.service.ProgramService;
import ca.bc.gov.educ.api.program.util.GradValidation;
import ca.bc.gov.educ.api.program.util.MessageHelper;
import ca.bc.gov.educ.api.program.util.ResponseHelper;


@ExtendWith(MockitoExtension.class)
public class ProgramControllerTest {

	@Mock
	private ProgramService programService;
	
	@Mock
	ResponseHelper response;
	
	@InjectMocks
	private ProgramController programController;
	
	@Mock
	GradValidation validation;
	
	@Mock
	MessageHelper messagesHelper;
	
	@Mock
	OAuth2AuthenticationDetails oAuth2AuthenticationDetails;
	
	@Mock
	SecurityContextHolder securityContextHolder;
	
	@Mock
	ResponseEntity<List<GraduationProgramCode>> ent;
	
	@Test
	public void testGetAllProgramList() {
		List<GraduationProgramCode> gradProgramList = new ArrayList<>();
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		gradProgramList.add(obj);
		obj = new GraduationProgramCode();
		obj.setProgramCode("AC");
		obj.setProgramName("Autobody");
		gradProgramList.add(obj);
		
		Mockito.when(programService.getAllProgramList()).thenReturn(gradProgramList);
		programController.getAllPrograms();
		Mockito.verify(programService).getAllProgramList();
	}
	
	@Test
	public void testGetAllProgramList_emptyList() {		
		Mockito.when(programService.getAllProgramList()).thenReturn(new ArrayList<>());
		ResponseEntity<List<GraduationProgramCode>> programList = programController.getAllPrograms();
		Mockito.verify(programService).getAllProgramList();
		assertNull(programList);
	}
	
	@Test
	public void testGetSpecificProgram() {
		String programCode="AB";
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		Mockito.when(programService.getSpecificProgram(programCode)).thenReturn(obj);
		programController.getSpecificProgram(programCode);
		Mockito.verify(programService).getSpecificProgram(programCode);
	}
	
	@Test
	public void testGetSpecificProgram_noObject() {
		String programCode="AB";
		Mockito.when(programService.getSpecificProgram(programCode)).thenReturn(null);
		ResponseEntity<GraduationProgramCode> obj = programController.getSpecificProgram(programCode);
		Mockito.verify(programService).getSpecificProgram(programCode);
		assertNull(obj);
	}
	
	@Test
	public void testdeleteGradPrograms() {
		String programCode = "DC";
		Mockito.when(programService.deleteGradPrograms(programCode)).thenReturn(1);
		programController.deleteGradPrograms(programCode);
		Mockito.verify(programService).deleteGradPrograms(programCode);
	}
	
	@Test
	public void testCreateGradPrograms() {
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		Mockito.when(programService.createGradProgram(obj)).thenReturn(obj);
		programController.createGradPrograms(obj);
	}
	
	@Test
	public void testCreateGradPrograms_error() {
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramName("Autobody");
		Mockito.when(programService.createGradProgram(obj)).thenReturn(obj);
		programController.createGradPrograms(obj);
	}
	
	@Test
	public void testUpdateGradPrograms() {
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		Mockito.when(programService.updateGradProgram(obj)).thenReturn(obj);
		programController.updateGradPrograms(obj);
	}
	
	@Test
	public void testUpdateGradPrograms_error() {
		GraduationProgramCode obj = new GraduationProgramCode();
		obj.setProgramName("Autobody");
		Mockito.when(programService.updateGradProgram(obj)).thenReturn(obj);
		programController.updateGradPrograms(obj);
	}
	
	@Test
	public void testCreateGradProgramRules() {		
		ProgramRequirement obj = new ProgramRequirement();
		obj.setGraduationProgramCode("AB");
		ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		obj.setProgramRequirementCode(code);
		Mockito.when(programService.createGradProgramRules(obj)).thenReturn(obj);
		programController.createGradProgramRules(obj);
	}
	
	@Test
	public void testCreateGradProgramRules_error() { 
		ProgramRequirement obj = new ProgramRequirement();
		obj.setGraduationProgramCode("AB");
		ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		obj.setProgramRequirementCode(code);
		Mockito.when(programService.createGradProgramRules(obj)).thenReturn(obj);
		programController.createGradProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradProgramRules() {
		
		ProgramRequirement obj = new ProgramRequirement();
		obj.setGraduationProgramCode("AB");
		ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		obj.setProgramRequirementCode(code);
		Mockito.when(programService.updateGradProgramRules(obj)).thenReturn(obj);
		programController.updateGradProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradProgramRules_error() {		
		ProgramRequirement obj = new ProgramRequirement();
		obj.setGraduationProgramCode("AB");
		ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		obj.setProgramRequirementCode(code);
		Mockito.when(programService.updateGradProgramRules(obj)).thenReturn(obj);
		programController.updateGradProgramRules(obj);
	}
	
	@Test
	public void testDeleteGradProgramRules() {
		UUID ruleID = new UUID(1, 1);
		Mockito.when(programService.deleteGradProgramRules(ruleID)).thenReturn(1);
		programController.deleteGradProgramRules(ruleID.toString());		
		
	}	
	
	
	@Test
	public void testGetSpecificRuleDetails() {
		String ruleCode="200";
		List<GradRuleDetails> gradRuleDetails = new ArrayList<>();
		GradRuleDetails ruleEntity = new GradRuleDetails();
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementName("Match");
        ruleEntity.setRuleCode("200");
        ruleEntity.setSpecialProgramCode("FI");
        gradRuleDetails.add(ruleEntity);
        GradRuleDetails ruleEntity2 = new GradRuleDetails();
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementName("Match");
        ruleEntity.setRuleCode("200");
        ruleEntity.setSpecialProgramCode("FI");
        gradRuleDetails.add(ruleEntity2);
        
        Mockito.when(programService.getSpecificRuleDetails(ruleCode)).thenReturn(gradRuleDetails);
        programController.getSpecificRuleDetails(ruleCode);
	}	
	
	@Test
	public void testGetAllSpecialProgramsByID() {
		String specialProgramID=new UUID(1, 1).toString();
		OptionalProgram optionalProgram = new OptionalProgram();
		optionalProgram.setGraduationProgramCode("ABCD");
		optionalProgram.setOptionalProgramID(new UUID(1, 1));
		optionalProgram.setOptProgramCode("FI");
		optionalProgram.setOptionalProgramName("EFGH");
		Mockito.when(programService.getSpecialProgramByID(UUID.fromString(specialProgramID))).thenReturn(optionalProgram);
		programController.getAllSpecialProgramsByID(specialProgramID);
	}
	
	@Test
	public void testGetAllSpecialPrograms() {
		List<OptionalProgram> list = new ArrayList<OptionalProgram>();
		OptionalProgram optionalProgram = new OptionalProgram();
		optionalProgram.setGraduationProgramCode("ABCD");
		optionalProgram.setOptionalProgramID(new UUID(1, 1));
		optionalProgram.setOptProgramCode("FI");
		optionalProgram.setOptionalProgramName("EFGH");
		list.add(optionalProgram);
		optionalProgram = new OptionalProgram();
		optionalProgram.setGraduationProgramCode("ABCD");
		optionalProgram.setOptionalProgramID(new UUID(1, 1));
		optionalProgram.setOptProgramCode("FI");
		optionalProgram.setOptionalProgramName("EFGH");
		list.add(optionalProgram);
		Mockito.when(programService.getAllSpecialProgramList()).thenReturn(list);
		programController.getAllSpecialPrograms();
	}
	
	
	@Test
	public void testGetAllSpecialPrograms_emptyList() {
		Mockito.when(programService.getAllSpecialProgramList()).thenReturn(new ArrayList<>());
		programController.getAllSpecialPrograms();
	}
	
		
	@Test
	public void testetSpecialProgramRulesByProgramCodeAndSpecialProgramCode() {
		String programCode = "2018-EN";
		String specialProgramCode="FI";
		OptionalProgram optionalProgram = new OptionalProgram();
		optionalProgram.setGraduationProgramCode("ABCD");
		optionalProgram.setOptionalProgramID(new UUID(1, 1));
		optionalProgram.setOptProgramCode("FI");
		optionalProgram.setOptionalProgramName("EFGH");
		Mockito.when(programService.getSpecialProgram(programCode,specialProgramCode)).thenReturn(optionalProgram);
		programController.getSpecialPrograms(programCode,specialProgramCode);
	}
	
	@Test
	public void testCreateGradSpecialPrograms() {
		OptionalProgram obj = new OptionalProgram();
		obj.setGraduationProgramCode("AB");
		obj.setOptionalProgramID(new UUID(1, 1));
		obj.setOptProgramCode("FI");
		obj.setOptionalProgramName("French Immersion");
		Mockito.when(programService.createGradSpecialProgram(obj)).thenReturn(obj);
		programController.createGradSpecialPrograms(obj);
	}
	
	@Test
	public void testCreateGradSpecialPrograms_error() {
		OptionalProgram obj = new OptionalProgram();
		obj.setGraduationProgramCode("AB");
		obj.setOptionalProgramID(new UUID(1, 1));
		obj.setOptionalProgramName("French Immersion");
		Mockito.when(programService.createGradSpecialProgram(obj)).thenReturn(obj);
		programController.createGradSpecialPrograms(obj);
	}
	
	@Test
	public void testUpdateGradSpecialPrograms() {
		OptionalProgram obj = new OptionalProgram();
		obj.setGraduationProgramCode("AB");
		obj.setOptionalProgramID(new UUID(1, 1));
		obj.setOptProgramCode("FI");
		obj.setOptionalProgramName("French Immersion");
		Mockito.when(programService.updateGradSpecialPrograms(obj)).thenReturn(obj);
		programController.updateGradSpecialPrograms(obj);
	}
	
	@Test
	public void testUpdateGradSpecialPrograms_error() {
		OptionalProgram obj = new OptionalProgram();
		obj.setGraduationProgramCode("AB");
		obj.setOptionalProgramID(new UUID(1, 1));
		obj.setOptionalProgramName("French Immersion");
		Mockito.when(programService.updateGradSpecialPrograms(obj)).thenReturn(obj);
		programController.updateGradSpecialPrograms(obj);
	}
	
	@Test
	public void testDeleteSpecialPrograms() {
		String specialProgramID = new UUID(1, 1).toString();
		Mockito.when(programService.deleteGradSpecialPrograms(UUID.fromString(specialProgramID))).thenReturn(1);
		programController.deleteGradSpecialPrograms(specialProgramID);
	}
	
	
	@Test
	public void testCreateGradSpecialProgramRules() {		
		OptionalProgramRequirement obj = new OptionalProgramRequirement();
		obj.setOptionalProgramRequirementID(new UUID(1, 1));
		OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
		code.setOptProReqCode("100");
		obj.setOptionalProgramRequirementCode(code);
		Mockito.when(programService.createGradSpecialProgramRules(obj)).thenReturn(obj);
		programController.createGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testCreateGradSpecialProgramRules_error() {		
		OptionalProgramRequirement obj = new OptionalProgramRequirement();
		obj.setOptionalProgramRequirementID(new UUID(1, 1));
		OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
		code.setOptProReqCode("100");
		obj.setOptionalProgramRequirementCode(code);
		Mockito.when(programService.createGradSpecialProgramRules(obj)).thenReturn(obj);
		programController.createGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradSpecialProgramRules() {
		OptionalProgramRequirement obj = new OptionalProgramRequirement();
		obj.setOptionalProgramRequirementID(new UUID(1, 1));
		OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
		code.setOptProReqCode("100");
		obj.setOptionalProgramRequirementCode(code);
		Mockito.when(programService.updateGradSpecialProgramRules(obj)).thenReturn(obj);
		programController.updateGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradSpecialProgramRules_error() {
		OptionalProgramRequirement obj = new OptionalProgramRequirement();
		obj.setOptionalProgramRequirementID(new UUID(1, 1));
		OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
		code.setOptProReqCode("101");
		obj.setOptionalProgramRequirementCode(code);
		Mockito.when(programService.updateGradSpecialProgramRules(obj)).thenReturn(obj);
		programController.updateGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testDeleteGradSpecialProgramRules() {
		UUID ruleID = new UUID(1, 1);
		Mockito.when(programService.deleteGradSpecialProgramRules(ruleID)).thenReturn(1);
		programController.deleteGradSpecialProgramRules(ruleID.toString());		
		
	}
	
	@Test
	public void testGetAllSpecialProgramRules() {
		List<OptionalProgramRequirement> list = new ArrayList<>();
		OptionalProgramRequirement obj = new OptionalProgramRequirement();
		obj.setOptionalProgramRequirementID(new UUID(1, 1));
		OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
		code.setOptProReqCode("101");
		obj.setOptionalProgramRequirementCode(code);
    	list.add(obj);    	
		
		Mockito.when(programService.getAllSpecialProgramRulesList()).thenReturn(list);
		programController.getAllSpecialProgramRules();
	}
	
	@Test
	public void testGetAllSpecialProgramRules_emptyList() {		
		Mockito.when(programService.getAllSpecialProgramRulesList()).thenReturn(new ArrayList<>());
		programController.getAllSpecialProgramRules();
	}
	
	@Test
	public void getAllProgramsRules() {
		List<ProgramRequirement> list = new ArrayList<>();
		ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
    	list.add(gradProgramRule);
		
		Mockito.when(programService.getAllProgramRulesList()).thenReturn(list);
		programController.getAllProgramsRules();
	}
	
	@Test
	public void testGetAllProgramsRules_emptyList() {		
		Mockito.when(programService.getAllProgramRulesList()).thenReturn(new ArrayList<>());
		programController.getAllProgramsRules();
	}
	
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode() {
		String specialProgramCode= "FI";
		String programCode="2018-EN";
		
		List<OptionalProgramRequirement> list = new ArrayList<>();
		OptionalProgramRequirement gradProgramRule = new OptionalProgramRequirement();
    	gradProgramRule.setOptionalProgramID(new UUID(1, 1));
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
		code.setOptProReqCode("100");
		gradProgramRule.setOptionalProgramRequirementCode(code);
    	list.add(gradProgramRule);
    	
		Mockito.when(programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode)).thenReturn(list);
		programController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_emptyList() {
		String specialProgramCode= "FI";
		String programCode="2018-EN";
		Mockito.when(programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode)).thenReturn(new ArrayList<>());
		programController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
	}
	
	@Test
	public void testgetAllProgramsRules() {		
		List<ProgramRequirement> list = new ArrayList<ProgramRequirement>();
		ProgramRequirement obj = new ProgramRequirement();
		obj.setGraduationProgramCode("AB");
		ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		obj.setProgramRequirementCode(code);
		list.add(obj);
		Mockito.when(programService.getAllProgramRulesList()).thenReturn(list);
		programController.getAllProgramsRules();
	}
	
	@Test
	public void testgetAllProgramsRules_empty() {		
		Mockito.when(programService.getAllProgramRulesList()).thenReturn(new ArrayList<ProgramRequirement>());
		programController.getAllProgramsRules();
	}
	
	@Test
	public void testgetAllProgramRequirementCode() {		
		List<ProgramRequirementCode> list = new ArrayList<ProgramRequirementCode>();
		ProgramRequirementCode obj = new ProgramRequirementCode();
		obj.setProReqCode("100");
		list.add(obj);
		Mockito.when(programService.getAllProgramRequirementCodeList()).thenReturn(list);
		programController.getAllProgramRequirementCode();
	}
	
	@Test
	public void testgetAllProgramRequirementCode_empty() {		
		Mockito.when(programService.getAllProgramRequirementCodeList()).thenReturn(new ArrayList<ProgramRequirementCode>());
		programController.getAllProgramRequirementCode();
	}
	
	@Test
	public void testgetAllOptionalProgramRequirementCode() {		
		List<OptionalProgramRequirementCode> list = new ArrayList<OptionalProgramRequirementCode>();
		OptionalProgramRequirementCode obj = new OptionalProgramRequirementCode();
		obj.setOptProReqCode("100");
		list.add(obj);
		Mockito.when(programService.getAllOptionalProgramRequirementCodeList()).thenReturn(list);
		programController.getAllOptionalProgramRequirementCode();
	}
	
	@Test
	public void testgetAllOptionalProgramRequirementCode_empty() {		
		Mockito.when(programService.getAllOptionalProgramRequirementCodeList()).thenReturn(new ArrayList<OptionalProgramRequirementCode>());
		programController.getAllOptionalProgramRequirementCode();
	}
	
	@Test
	public void testGetAllAlgorithmData() {
		String programCode="2018-EN";
		String optionalProgramCode="FI";
		GradProgramAlgorithmData data = new GradProgramAlgorithmData();
		List<ProgramRequirement> list = new ArrayList<ProgramRequirement>();
		ProgramRequirement obj = new ProgramRequirement();
		obj.setGraduationProgramCode("AB");
		ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		obj.setProgramRequirementCode(code);
		list.add(obj);
		data.setProgramRules(list);
		
		List<OptionalProgramRequirement> listOptional = new ArrayList<OptionalProgramRequirement>();
		OptionalProgramRequirement objs = new OptionalProgramRequirement();
		objs.setOptionalProgramID(new UUID(1, 1));
		OptionalProgramRequirementCode codes = new OptionalProgramRequirementCode();
		codes.setOptProReqCode("100");
		objs.setOptionalProgramRequirementCode(codes);
		listOptional.add(objs);
		
		data.setOptionalProgramRules(listOptional);
		
		Mockito.when(programService.getAllAlgorithmData(programCode,optionalProgramCode)).thenReturn(data);
		programController.getAllAlgorithmData(programCode, optionalProgramCode);
	}
	
	@Test
	public void testGetAllCareerProgramCodeList() {
		List<CareerProgram> gradCareerProgramList = new ArrayList<>();
		CareerProgram obj = new CareerProgram();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		gradCareerProgramList.add(obj);
		obj = new CareerProgram();
		obj.setCode("CC");
		obj.setDescription("Courses not complete");
		gradCareerProgramList.add(obj);
		Mockito.when(programService.getAllCareerProgramCodeList()).thenReturn(gradCareerProgramList);
		programController.getAllCareerPrograms();
		Mockito.verify(programService).getAllCareerProgramCodeList();
	}
	
	@Test
	public void testGetSpecificCareerProgramCode() {
		String requirementType = "DC";
		CareerProgram obj = new CareerProgram();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		Mockito.when(programService.getSpecificCareerProgramCode(requirementType)).thenReturn(obj);
		programController.getSpecificCareerProgramCode(requirementType);
		Mockito.verify(programService).getSpecificCareerProgramCode(requirementType);
	}
	
	@Test
	public void testGetSpecificCareerProgramCode_noContent() {
		String requirementType = "AB";	
		Mockito.when(programService.getSpecificCareerProgramCode(requirementType)).thenReturn(null);
		programController.getSpecificCareerProgramCode(requirementType);
		Mockito.verify(programService).getSpecificCareerProgramCode(requirementType);
	}
	
	@Test
	public void testGetAllRequirementTypesCodeList() {
		List<RequirementTypeCode> gradRequirementTypesList = new ArrayList<>();
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		gradRequirementTypesList.add(obj);
		obj = new RequirementTypeCode();
		obj.setReqTypeCode("CC");
		obj.setDescription("Courses not complete");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		gradRequirementTypesList.add(obj);
		Mockito.when(programService.getAllRequirementTypeCodeList()).thenReturn(gradRequirementTypesList);
		programController.getAllRequirementTypeCodeList();
		Mockito.verify(programService).getAllRequirementTypeCodeList();
	}
	
	@Test
	public void testGetSpecificRequirementTypesCode() {
		String requirementType = "DC";
		RequirementTypeCode obj = new RequirementTypeCode();
		obj.setReqTypeCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(programService.getSpecificRequirementTypeCode(requirementType)).thenReturn(obj);
		programController.getSpecificRequirementTypeCode(requirementType);
		Mockito.verify(programService).getSpecificRequirementTypeCode(requirementType);
	}
	
	@Test
	public void testGetSpecificRequirementTypesCode_noContent() {
		String requirementType = "AB";	
		Mockito.when(programService.getSpecificRequirementTypeCode(requirementType)).thenReturn(null);
		programController.getSpecificRequirementTypeCode(requirementType);
		Mockito.verify(programService).getSpecificRequirementTypeCode(requirementType);
	}
}
