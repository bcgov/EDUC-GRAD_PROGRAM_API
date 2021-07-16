package ca.bc.gov.educ.api.program.controller;

import static org.junit.Assert.assertNull;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgramRule;
import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirementCode;
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
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
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
	
	/*
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
	public void testGetRequirementByRequirementType() {
		String typeCode="AB";
		Mockito.when(programService.getRequirementByRequirementType(typeCode)).thenReturn(true);
		programController.getRequirementByRequirementType(typeCode);
		Mockito.verify(programService).getRequirementByRequirementType(typeCode);
	}
	
	@Test
	public void testGetAllSpecialProgramsByID() {
		String specialProgramID=new UUID(1, 1).toString();
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		Mockito.when(programService.getSpecialProgramByID(UUID.fromString(specialProgramID))).thenReturn(gradSpecialProgram);
		programController.getAllSpecialProgramsByID(specialProgramID);
	}
	
	@Test
	public void testGetAllSpecialPrograms() {
		List<GradSpecialProgram> list = new ArrayList<GradSpecialProgram>();
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		Mockito.when(programService.getAllSpecialProgramList()).thenReturn(list);
		programController.getAllSpecialPrograms();
	}
	
	@Test
	public void testGetAllSpecialPrograms_emptyList() {
		Mockito.when(programService.getAllSpecialProgramList()).thenReturn(new ArrayList<>());
		programController.getAllSpecialPrograms();
	}
	
	@Test
	public void testGetAllSpecialProgramsByProgram() {
		String programCode = "2018-EN";
		List<GradSpecialProgram> list = new ArrayList<GradSpecialProgram>();
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		Mockito.when(programService.getAllSpecialProgramList(programCode)).thenReturn(list);
		programController.getAllSpecialPrograms(programCode);
	}
	
	@Test
	public void testGetAllSpecialProgramsByProgram_emptyList() {
		String programCode = "2018-EN";
		Mockito.when(programService.getAllSpecialProgramList(programCode)).thenReturn(new ArrayList<>());
		programController.getAllSpecialPrograms(programCode);
	}
	
	@Test
	public void testGetSpecialProgramsByProgramCodeSpecialProgramCode() {
		String programCode = "2018-EN";
		String specialProgramCode="FI";
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		Mockito.when(programService.getSpecialProgram(programCode,specialProgramCode)).thenReturn(gradSpecialProgram);
		programController.getSpecialPrograms(programCode,specialProgramCode);
	}
	
	@Test
	public void testCreateGradSpecialPrograms() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramCode("FI");
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programService.createGradSpecialProgram(obj)).thenReturn(obj);
		programController.createGradSpecialPrograms(obj);
	}
	
	@Test
	public void testCreateGradSpecialPrograms_error() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programService.createGradSpecialProgram(obj)).thenReturn(obj);
		programController.createGradSpecialPrograms(obj);
	}
	
	@Test
	public void testUpdateGradSpecialPrograms() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramCode("FI");
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programService.updateGradSpecialPrograms(obj)).thenReturn(obj);
		programController.updateGradSpecialPrograms(obj);
	}
	
	@Test
	public void testUpdateGradSpecialPrograms_error() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramName("French Immersion");
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
	public void testGetSpecialProgramRules() {
		String specialProgramID = new UUID(1, 1).toString();
		String requirementType = "M";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	Mockito.when(programService.getAllSpecialProgramRuleList(UUID.fromString(specialProgramID),requirementType,null)).thenReturn(list);
		programController.getAllSpecialProgramRules(specialProgramID,requirementType);
	}
	
	@Test
	public void testGetSpecialProgramRules_emptyList() {
		String specialProgramID = new UUID(1, 1).toString();
		String requirementType = "M";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
    	Mockito.when(programService.getAllSpecialProgramRuleList(UUID.fromString(specialProgramID),requirementType,null)).thenReturn(new ArrayList<>());
		programController.getAllSpecialProgramRules(specialProgramID,requirementType);
	}
	
	@Test
	public void testCreateGradSpecialProgramRules() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		obj.setRequirementName("Match");
		Mockito.when(programService.createGradSpecialProgramRules(obj,null)).thenReturn(obj);
		programController.createGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testCreateGradSpecialProgramRules_error() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		Mockito.when(programService.createGradSpecialProgramRules(obj,null)).thenReturn(obj);
		programController.createGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradSpecialProgramRules() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		obj.setRequirementName("Match");
		Mockito.when(programService.updateGradSpecialProgramRules(obj, null)).thenReturn(obj);
		programController.updateGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradSpecialProgramRules_error() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		Mockito.when(programService.updateGradSpecialProgramRules(obj, null)).thenReturn(obj);
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
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	
    	Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when(programService.getAllSpecialProgramRulesList(null)).thenReturn(list);
		programController.getAllSpecialProgramRules();
	}
	
	@Test
	public void testGetAllSpecialProgramRules_emptyList() {
    	
    	Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when(programService.getAllSpecialProgramRulesList(null)).thenReturn(new ArrayList<>());
		programController.getAllSpecialProgramRules();
	}
	*/
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
	/*
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode() {
		String specialProgramCode= "FI";
		String requirementType = "M";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	
		Mockito.when(programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,null)).thenReturn(list);
		programController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode,requirementType);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_emptyList() {
		String specialProgramCode= "FI";
		String requirementType = "M";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		
		Mockito.when(programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,null)).thenReturn(new ArrayList<>());
		programController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode,requirementType);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_withoutRequirementType() {
		String specialProgramCode= "FI";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	
		Mockito.when(programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,null,null)).thenReturn(list);
		programController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_withoutRequirementType_emptyList() {
		String specialProgramCode= "FI";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		
		Mockito.when(programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,null,null)).thenReturn(new ArrayList<>());
		programController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
	}
	*/
}
