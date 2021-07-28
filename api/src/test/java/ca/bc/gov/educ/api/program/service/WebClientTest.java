package ca.bc.gov.educ.api.program.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.entity.RequirementTypeCodeEntity;
import ca.bc.gov.educ.api.program.repository.GraduationProgramCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementRepository;
import ca.bc.gov.educ.api.program.util.EducGradProgramApiConstants;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
import ca.bc.gov.educ.api.program.util.GradValidation;

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
	
    @MockBean
	private GraduationProgramCodeRepository graduationProgramCodeRepository;
	
    @MockBean
	private ProgramRequirementRepository programRequirementRepository;
    
    @MockBean
	private ProgramRequirementCodeRepository programRequirementCodeRepository;
    
    @MockBean
   	private OptionalProgramRequirementRepository optionalProgramRequirementRepository;
       
    @MockBean
   	private OptionalProgramRequirementCodeRepository optionalProgramRequirementCodeRepository;
    
    @MockBean
    private OptionalProgramRepository optionalProgramRepository;
	
    @Autowired
	GradValidation validation;
	
    @Before
    public void setUp() {
        openMocks(this);
        programRequirementCodeRepository.save(createProgramRequirementCode());
        optionalProgramRequirementCodeRepository.save(createOptionalProgramRequirementCode());
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
		ruleEntity.setProgramRequirementCode(programRequirementCodeRepository.getOne(gradProgramRule.getProgramRequirementCode().getProReqCode()));
        
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
		codeE.setProReqCode("101");
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
    
    
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgramRules_existingRecordCheck() {
    	OptionalProgramRequirement gradSpecialProgramRule = new OptionalProgramRequirement();
    	gradSpecialProgramRule.setOptionalProgramID(new UUID(1, 1));
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradSpecialProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
		ruleEntity.setOptionalProgramID(new UUID(1,1));
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");    	
    	ruleEntity.setOptionalProgramRequirementCode(code2);
		
		OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.getOne(gradSpecialProgramRule.getOptionalProgramID())).thenReturn(gradSpecialProgramEntity);
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradSpecialProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradSpecialProgramRule.getOptionalProgramID())).thenReturn(new UUID(1, 1));
    	programService.createGradSpecialProgramRules(gradSpecialProgramRule);
	}
    
    @Test
	public void testCreateGradSpecialProgramRules() {
    	OptionalProgramRequirement gradSpecialProgramRule = new OptionalProgramRequirement();
    	gradSpecialProgramRule.setOptionalProgramID(new UUID(1, 1));
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradSpecialProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
		ruleEntity.setOptionalProgramID(new UUID(1,1));
    	ruleEntity.setOptionalProgramRequirementCode(optionalProgramRequirementCodeRepository.getOne(gradSpecialProgramRule.getOptionalProgramRequirementCode().getOptProReqCode()));
    	OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.getOne(gradSpecialProgramRule.getOptionalProgramID())).thenReturn(gradSpecialProgramEntity);
        
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradSpecialProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradSpecialProgramRule.getOptionalProgramID())).thenReturn(null);
    	Mockito.when(optionalProgramRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.createGradSpecialProgramRules(gradSpecialProgramRule);
	}
    
    @Test
	public void testUpdateGradSpecialProgramRules() {
    	OptionalProgramRequirement gradSpecialProgramRule = new OptionalProgramRequirement();
    	gradSpecialProgramRule.setOptionalProgramID(new UUID(1, 1));
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradSpecialProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
		ruleEntity.setOptionalProgramID(new UUID(1,1));
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");    	
    	ruleEntity.setOptionalProgramRequirementCode(code2);
        
        Mockito.when(optionalProgramRequirementRepository.findById(gradSpecialProgramRule.getOptionalProgramID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradSpecialProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradSpecialProgramRule.getOptionalProgramID())).thenReturn(null);
    	Mockito.when(optionalProgramRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule);
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_ruleCodeChanged() {
    	OptionalProgramRequirement gradSpecialProgramRule = new OptionalProgramRequirement();
    	gradSpecialProgramRule.setOptionalProgramID(new UUID(1, 1));
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradSpecialProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
		ruleEntity.setOptionalProgramID(new UUID(1,1));
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("800");    	
    	ruleEntity.setOptionalProgramRequirementCode(code2);
        
    	OptionalProgramEntity gradSpecialProgramEntity = new OptionalProgramEntity();
		gradSpecialProgramEntity.setGraduationProgramCode("ABCD");
		gradSpecialProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradSpecialProgramEntity.setOptProgramCode("FI");
		gradSpecialProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.getOne(gradSpecialProgramRule.getOptionalProgramID())).thenReturn(gradSpecialProgramEntity);
        
        Mockito.when(optionalProgramRequirementRepository.findById(gradSpecialProgramRule.getOptionalProgramID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradSpecialProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradSpecialProgramRule.getOptionalProgramID())).thenReturn(new UUID(1, 1));
    	Mockito.when(optionalProgramRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule);
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_wrongRecord() {
    	OptionalProgramRequirement gradSpecialProgramRule = new OptionalProgramRequirement();
    	gradSpecialProgramRule.setOptionalProgramID(new UUID(1, 1));
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradSpecialProgramRule.setOptionalProgramRequirementCode(code);
        
        Mockito.when(optionalProgramRequirementRepository.findById(gradSpecialProgramRule.getOptionalProgramID())).thenReturn(Optional.empty());    
    	programService.updateGradSpecialProgramRules(gradSpecialProgramRule);
	}
    
    @Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_exception() {
		String programCode = "2018-EN";
		String specialProgramCode = "FI";
		validation.clear();
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, specialProgramCode)).thenReturn(Optional.empty());
		
		try {
			programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode);
		} catch (GradBusinessRuleException e) {
			List<String> errors = validation.getErrors();
			assertEquals(1, errors.size());
			return;
		}
		
	}
    
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
    
    
    @Test
    public void testGetAllSpecialProgramRulesList() {
    	String requirementType="M";
    	
    	RequirementTypeCodeEntity reqType = new RequirementTypeCodeEntity();
    	reqType.setReqTypeCode("M");
    	reqType.setDescription("Match");
    	
    	List<OptionalProgramRequirementEntity> gradProgramRuleList = new ArrayList<OptionalProgramRequirementEntity>();
		OptionalProgramRequirementEntity ruleObj = new OptionalProgramRequirementEntity();
		ruleObj.setOptionalProgramID(new UUID(1, 1));
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");
		code2.setRequirementTypeCode(reqType);
    	ruleObj.setOptionalProgramRequirementCode(code2);
    	
		gradProgramRuleList.add(ruleObj);
		ruleObj = new OptionalProgramRequirementEntity();
		ruleObj.setOptionalProgramID(new UUID(1, 1));
		OptionalProgramRequirementCodeEntity code3 = new OptionalProgramRequirementCodeEntity();
		code3.setOptProReqCode("200");
		code3.setRequirementTypeCode(reqType);
		gradProgramRuleList.add(ruleObj);
		
		OptionalProgramEntity obj = new OptionalProgramEntity();
		obj.setOptionalProgramID(new UUID(1, 1));
		obj.setGraduationProgramCode("2018-EN");
		obj.setOptionalProgramName("2018 Graduation Program");
		obj.setOptProgramCode("FI");
        
        Mockito.when(optionalProgramRequirementRepository.findAll()).thenReturn(gradProgramRuleList);
        Mockito.when(optionalProgramRepository.findById(new UUID(1, 1))).thenReturn(Optional.of(obj));
		programService.getAllSpecialProgramRulesList();
    }
    
    @Test
    public void testGetAllSpecialProgramRulesList_emptyList() {
        Mockito.when(programRequirementRepository.findAll()).thenReturn(new ArrayList<>());
		programService.getAllSpecialProgramRulesList();
    }
    
    @Test
    public void testGetAllProgramRuleList() {
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
    	programService.getAllProgramRuleList(programCode);
    }
    
    
    private ProgramRequirementCodeEntity createProgramRequirementCode() {
    	ProgramRequirementCodeEntity obj = new ProgramRequirementCodeEntity();
		obj.setProReqCode("100");
		obj.setLabel("SASD");
		obj.setRequirementCategory("C");
		return obj;
	}
    
    private OptionalProgramRequirementCodeEntity createOptionalProgramRequirementCode() {
    	OptionalProgramRequirementCodeEntity obj = new OptionalProgramRequirementCodeEntity();
		obj.setOptProReqCode("100");
		obj.setLabel("SASD");
		obj.setRequirementCategory("C");
		return obj;
	}
}

