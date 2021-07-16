package ca.bc.gov.educ.api.program.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
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

import ca.bc.gov.educ.api.program.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;
import ca.bc.gov.educ.api.program.repository.GraduationProgramCodeRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementRepository;
import ca.bc.gov.educ.api.program.util.EducGradProgramApiConstants;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings({"rawtypes","unchecked"})
public class WebClientTest {

    @MockBean
    WebClient webClient;

    @Autowired
    private EducGradProgramApiConstants constants;

    @Autowired
	private ProgramService programService;
    
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;
    @Mock
    private Mono<GradRequirementTypes> monoResponse;
	
    @MockBean
	private GraduationProgramCodeRepository graduationProgramCodeRepository;
	
    @MockBean
	private ProgramRequirementRepository programRequirementRepository;
	
	
	
	@Before
    public void setUp() {
        openMocks(this);
    }

    

	@After
    public void tearDown() {

    }
    
    
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradProgramRules_existingRecordCheck() {
    	ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
    	ProgramRequirementEntity ruleEntity = new ProgramRequirementEntity();
		ruleEntity.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity code2 = new ProgramRequirementCodeEntity();
		code2.setProReqCode("100");
		ruleEntity.setProgramRequirementCode(code2);
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(new UUID(1, 1));
    	programService.createGradProgramRules(gradProgramRule);
	}
    
    @Test
	public void testCreateGradProgramRules() {
    	ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setProgramRequirementID(new UUID(1, 1));
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
		ProgramRequirementEntity ruleEntity = new ProgramRequirementEntity();
    	ruleEntity.setProgramRequirementID(new UUID(1,1));
		ruleEntity.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity codeE = new ProgramRequirementCodeEntity();
		codeE.setProReqCode("100");
		ruleEntity.setProgramRequirementCode(codeE);
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(null);
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.createGradProgramRules(gradProgramRule);
	}
    
    @Test
	public void testUpdateGradProgramRules() {
    	ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setProgramRequirementID(new UUID(1, 1));
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
    	ProgramRequirementEntity ruleEntity = new ProgramRequirementEntity();
    	ruleEntity.setProgramRequirementID(new UUID(1,1));
		ruleEntity.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity codeE = new ProgramRequirementCodeEntity();
		codeE.setProReqCode("100");
		ruleEntity.setProgramRequirementCode(codeE);
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(programRequirementRepository.findById(gradProgramRule.getProgramRequirementID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(null);
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradProgramRules(gradProgramRule);
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgramRules_ruleCodeChanged() {
    	ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setProgramRequirementID(new UUID(1, 1));
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
		ProgramRequirementEntity ruleEntity = new ProgramRequirementEntity();
    	ruleEntity.setProgramRequirementID(new UUID(1,1));
		ruleEntity.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity codeE = new ProgramRequirementCodeEntity();
		codeE.setProReqCode("800");
		ruleEntity.setProgramRequirementCode(codeE);
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(programRequirementRepository.findById(gradProgramRule.getProgramRequirementID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(new UUID(1, 1));
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradProgramRules(gradProgramRule);
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgramRules_ruleCodeUnChanged() {
    	ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setProgramRequirementID(new UUID(1, 1));
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
		ProgramRequirementEntity ruleEntity = new ProgramRequirementEntity();
    	ruleEntity.setProgramRequirementID(new UUID(1,1));
		ruleEntity.setGraduationProgramCode("2018-EN");
		ProgramRequirementCodeEntity codeE = new ProgramRequirementCodeEntity();
		codeE.setProReqCode("100");
		ruleEntity.setProgramRequirementCode(codeE);      
        Mockito.when(programRequirementRepository.findById(gradProgramRule.getProgramRequirementID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(new UUID(1, 1));
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradProgramRules(gradProgramRule);
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgramRules_wrongRecord() {
    	ProgramRequirement gradProgramRule = new ProgramRequirement();
    	gradProgramRule.setProgramRequirementID(new UUID(1, 1));
    	gradProgramRule.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCode code = new ProgramRequirementCode();
		code.setProReqCode("100");
		gradProgramRule.setProgramRequirementCode(code);
        
        Mockito.when(programRequirementRepository.findById(gradProgramRule.getProgramRequirementID())).thenReturn(Optional.empty());    
    	programService.updateGradProgramRules(gradProgramRule);
	}
    
    /*
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgramRules_existingRecordCheck() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID())).thenReturn(gradSpecialProgramEntity);
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(new UUID(1, 1));
    	programService.createGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test
	public void testCreateGradSpecialProgramRules() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setSpecialProgramID(new UUID(2,2));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(2,2));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
    	
        GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID())).thenReturn(gradSpecialProgramEntity);
        
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(null);
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.createGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgramRules_requirementTypeError() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(null); 
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(null);
    	programService.createGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test
	public void testUpdateGradSpecialProgramRules() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(null);
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_ruleCodeChanged() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("800");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
    	GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID())).thenReturn(gradSpecialProgramEntity);
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(new UUID(1, 1));
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_ruleCodeUnChanged() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("F");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(null); 
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(new UUID(1, 1));
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_wrongRecord() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.empty());    
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		String requirementType="M";
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));
		obj.setProgramCode("2018-EN");
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		
		List<GradSpecialProgramRulesEntity> gradProgramRuleList = new ArrayList<GradSpecialProgramRulesEntity>();
		GradSpecialProgramRulesEntity ruleObj = new GradSpecialProgramRulesEntity();
		ruleObj.setSpecialProgramID(new UUID(1, 1));
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		ruleObj.setRequirementType("M");
		gradProgramRuleList.add(ruleObj);
		ruleObj = new GradSpecialProgramRulesEntity();
		ruleObj.setSpecialProgramID(new UUID(2, 2));
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		ruleObj.setRequirementType("M");
		gradProgramRuleList.add(ruleObj);
		
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode)).thenReturn(Optional.of(obj));
		Mockito.when(gradSpecialProgramRulesRepository.findBySpecialProgramIDAndRequirementType(obj.getId(),requirementType)).thenReturn(gradProgramRuleList);
		programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,"accessToken");
		
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_exception() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		String requirementType="M";
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));
		obj.setProgramCode("2018-EN");
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");        
        
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode)).thenReturn(Optional.empty());
		programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,"accessToken");
		
	}
    */
    @Test
    public void testGetAllProgramRulesList() {
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
		code2.setProReqCode("100");
		ruleObj.setProgramRequirementCode(code2);
		gradProgramRuleList.add(ruleObj);        
        Mockito.when(programRequirementRepository.findAll()).thenReturn(gradProgramRuleList);
		programService.getAllProgramRulesList();
    }
    
    @Test
    public void testGetAllProgramRulesList_emptyList() {
        Mockito.when(programRequirementRepository.findAll()).thenReturn(new ArrayList<>());
		programService.getAllProgramRulesList();
    }
    
    /*
    @Test
    public void testGetAllSpecialProgramRulesList() {
    	String requirementType="M";
    	List<GradSpecialProgramRulesEntity> gradProgramRuleList = new ArrayList<GradSpecialProgramRulesEntity>();
		GradSpecialProgramRulesEntity ruleObj = new GradSpecialProgramRulesEntity();
		ruleObj.setSpecialProgramID(new UUID(1, 1));
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		ruleObj.setRequirementType("M");
		gradProgramRuleList.add(ruleObj);
		ruleObj = new GradSpecialProgramRulesEntity();
		ruleObj.setSpecialProgramID(new UUID(1, 1));
		ruleObj.setRuleCode("200");
		ruleObj.setRequirementName("ABC");
		ruleObj.setRequirementType("M");
		gradProgramRuleList.add(ruleObj);
		
		GradSpecialProgramEntity obj = new GradSpecialProgramEntity();
		obj.setId(new UUID(1, 1));
		obj.setProgramCode("2018-EN");
		obj.setSpecialProgramName("2018 Graduation Program");
		obj.setSpecialProgramCode("FI");
		
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(gradSpecialProgramRulesRepository.findAll()).thenReturn(gradProgramRuleList);
        Mockito.when(gradSpecialProgramRepository.findById(new UUID(1, 1))).thenReturn(Optional.of(obj));
		programService.getAllSpecialProgramRulesList("accessToken");
    }
    
    @Test
    public void testGetAllSpecialProgramRulesList_emptyList() {
        Mockito.when(programRequirementRepository.findAll()).thenReturn(new ArrayList<>());
		programService.getAllSpecialProgramRulesList("accessToken");
    }
    */
}

