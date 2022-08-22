package ca.bc.gov.educ.api.program.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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

import ca.bc.gov.educ.api.program.model.dto.OptionalProgram;
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
import ca.bc.gov.educ.api.program.repository.RequirementTypeCodeRepository;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
import ca.bc.gov.educ.api.program.util.GradValidation;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings({"rawtypes"})
public class WebClientTest {

    @MockBean
    WebClient webClient;

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
    
    @MockBean
    private RequirementTypeCodeRepository requirementTypeCodeRepository;
	
    @Autowired
	GradValidation validation;
	
    @Before
    public void setUp() {
        openMocks(this);
        programRequirementCodeRepository.save(createProgramRequirementCode());
        optionalProgramRequirementCodeRepository.save(createOptionalProgramRequirementCode());
        requirementTypeCodeRepository.save(createRequirementTypeCode());
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
		ProgramRequirementCodeEntity codeEntity = new ProgramRequirementCodeEntity();
		codeEntity.setProReqCode("100");

		ruleEntity.setProgramRequirementCode(codeEntity);
        
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(null);
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
		Mockito.when(programRequirementCodeRepository.findById(gradProgramRule.getProgramRequirementCode().getProReqCode())).thenReturn(Optional.of(codeEntity));

		ProgramRequirement res = programService.createGradProgramRules(gradProgramRule);
		assertThat(res).isNotNull();
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
        Mockito.when(programRequirementRepository.findById(gradProgramRule.getProgramRequirementID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(null);
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	ProgramRequirement res = programService.updateGradProgramRules(gradProgramRule);
		assertThat(res).isNotNull();
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
        Mockito.when(programRequirementRepository.findById(gradProgramRule.getProgramRequirementID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode())).thenReturn(new UUID(1, 1));
    	Mockito.when(programRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	ProgramRequirement res = programService.updateGradProgramRules(gradProgramRule);
		assertThat(res).isNotNull();
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
	public void testCreateGradOptionalProgramRules_existingRecordCheck() {
    	OptionalProgramRequirement gradOptionalProgramRule = new OptionalProgramRequirement();
    	OptionalProgram op = new OptionalProgram();
		op.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramRule.setOptionalProgramID(op);
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradOptionalProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
    	OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(2, 2));
		ruleEntity.setOptionalProgramID(opE2);
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");    	
    	ruleEntity.setOptionalProgramRequirementCode(code2);
		
		OptionalProgramEntity gradOptionalProgramEntity = new OptionalProgramEntity();
		gradOptionalProgramEntity.setGraduationProgramCode("ABCD");
		gradOptionalProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramEntity.setOptProgramCode("FI");
		gradOptionalProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.findById(gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(Optional.of(gradOptionalProgramEntity));
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradOptionalProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(new UUID(1, 1));
    	programService.createGradOptionalProgramRules(gradOptionalProgramRule);
	}
    
    @Test
	public void testCreateGradOptionalProgramRules() {
    	OptionalProgramRequirement gradOptionalProgramRule = new OptionalProgramRequirement();
    	OptionalProgram op = new OptionalProgram();
		op.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramRule.setOptionalProgramID(op);
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradOptionalProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
    	OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(1, 1));
		ruleEntity.setOptionalProgramID(opE2);

		OptionalProgramRequirementCodeEntity codeEntity = new OptionalProgramRequirementCodeEntity();
		codeEntity.setOptProReqCode("100");

		ruleEntity.setOptionalProgramRequirementCode(codeEntity);
    	OptionalProgramEntity gradOptionalProgramEntity = new OptionalProgramEntity();
		gradOptionalProgramEntity.setGraduationProgramCode("ABCD");
		gradOptionalProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramEntity.setOptProgramCode("FI");
		gradOptionalProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.findById(gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(Optional.of(gradOptionalProgramEntity));
        Mockito.when(optionalProgramRequirementCodeRepository.findById(gradOptionalProgramRule.getOptionalProgramRequirementCode().getOptProReqCode())).thenReturn(Optional.of(codeEntity));
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradOptionalProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(null);
    	Mockito.when(optionalProgramRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	OptionalProgramRequirement res = programService.createGradOptionalProgramRules(gradOptionalProgramRule);
		assertThat(res).isNotNull();
	}
    
    @Test
	public void testUpdateGradOptionalProgramRules() {
    	OptionalProgramRequirement gradOptionalProgramRule = new OptionalProgramRequirement();
    	OptionalProgram op = new OptionalProgram();
		op.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramRule.setOptionalProgramID(op);
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradOptionalProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
    	OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(2, 2));
		ruleEntity.setOptionalProgramID(opE2);
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");    	
    	ruleEntity.setOptionalProgramRequirementCode(code2);
        
        Mockito.when(optionalProgramRequirementRepository.findById(gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradOptionalProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(null);
    	Mockito.when(optionalProgramRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	OptionalProgramRequirement res = programService.updateGradOptionalProgramRules(gradOptionalProgramRule);
		assertThat(res).isNotNull();
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradOptionalProgramRules_ruleCodeChanged() {
    	OptionalProgramRequirement gradOptionalProgramRule = new OptionalProgramRequirement();
    	OptionalProgram op = new OptionalProgram();
		op.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramRule.setOptionalProgramID(op);
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradOptionalProgramRule.setOptionalProgramRequirementCode(code);
    	OptionalProgramRequirementEntity ruleEntity = new OptionalProgramRequirementEntity();
    	OptionalProgramEntity opE2 = new OptionalProgramEntity();
		opE2.setOptionalProgramID(new UUID(2, 2));
		ruleEntity.setOptionalProgramID(opE2);
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("800");    	
    	ruleEntity.setOptionalProgramRequirementCode(code2);
        
    	OptionalProgramEntity gradOptionalProgramEntity = new OptionalProgramEntity();
		gradOptionalProgramEntity.setGraduationProgramCode("ABCD");
		gradOptionalProgramEntity.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramEntity.setOptProgramCode("FI");
		gradOptionalProgramEntity.setOptionalProgramName("EFGH");
		Mockito.when(optionalProgramRepository.findById(gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(Optional.of(gradOptionalProgramEntity));
        
        Mockito.when(optionalProgramRequirementRepository.findById(gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(optionalProgramRequirementRepository.findIdByRuleCode(gradOptionalProgramRule.getOptionalProgramRequirementCode().getOptProReqCode(),gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(new UUID(1, 1));
    	Mockito.when(optionalProgramRequirementRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	OptionalProgramRequirement res = programService.updateGradOptionalProgramRules(gradOptionalProgramRule);
		assertThat(res).isNotNull();
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradOptionalProgramRules_wrongRecord() {
    	OptionalProgramRequirement gradOptionalProgramRule = new OptionalProgramRequirement();
    	OptionalProgram op = new OptionalProgram();
		op.setOptionalProgramID(new UUID(1, 1));
		gradOptionalProgramRule.setOptionalProgramID(op);
    	OptionalProgramRequirementCode code = new OptionalProgramRequirementCode();
    	code.setOptProReqCode("100");    	
    	gradOptionalProgramRule.setOptionalProgramRequirementCode(code);
        
        Mockito.when(optionalProgramRequirementRepository.findById(gradOptionalProgramRule.getOptionalProgramID().getOptionalProgramID())).thenReturn(Optional.empty());    
    	OptionalProgramRequirement res = programService.updateGradOptionalProgramRules(gradOptionalProgramRule);
		assertThat(res).isNotNull();
	}
    
    @Test
	public void testGetOptionalProgramRulesByProgramCodeAndOptionalProgramCode_exception() {
		String programCode = "2018-EN";
		String optionalProgramCode = "FI";
		validation.clear();
		Mockito.when(optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, optionalProgramCode)).thenReturn(Optional.empty());
		
		try {
			programService.getOptionalProgramRulesByProgramCodeAndOptionalProgramCode(programCode,optionalProgramCode);
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
		List<ProgramRequirement> res = programService.getAllProgramRulesList();
		assertThat(res).isNotNull().hasSize(2);
    }
    
    @Test
    public void testGetAllProgramRulesList_emptyList() {
        Mockito.when(programRequirementRepository.findAll()).thenReturn(new ArrayList<>());
		List<ProgramRequirement> res= programService.getAllProgramRulesList();
		assertThat(res).isNotNull().isEmpty();
    }
    
    
    @Test
    public void testGetAllOptionalProgramRulesList() {    	
    	RequirementTypeCodeEntity reqType = new RequirementTypeCodeEntity();
    	reqType.setReqTypeCode("M");
    	reqType.setDescription("Match");
    	
    	List<OptionalProgramRequirementEntity> gradProgramRuleList = new ArrayList<OptionalProgramRequirementEntity>();
		OptionalProgramRequirementEntity ruleObj = new OptionalProgramRequirementEntity();
		OptionalProgramEntity op = new OptionalProgramEntity();
		op.setOptionalProgramID(new UUID(1, 1));
		ruleObj.setOptionalProgramID(op);
		OptionalProgramRequirementCodeEntity code2 = new OptionalProgramRequirementCodeEntity();
		code2.setOptProReqCode("100");
		code2.setRequirementTypeCode(reqType);
    	ruleObj.setOptionalProgramRequirementCode(code2);
    	
		gradProgramRuleList.add(ruleObj);
		ruleObj = new OptionalProgramRequirementEntity();
		op = new OptionalProgramEntity();
		op.setOptionalProgramID(new UUID(2, 2));
		ruleObj.setOptionalProgramID(op);
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
		List<OptionalProgramRequirement> res =programService.getAllOptionalProgramRulesList();
		assertThat(res).isNotNull().hasSize(2);
    }
    
    @Test
    public void testGetAllOptionalProgramRulesList_emptyList() {
        Mockito.when(programRequirementRepository.findAll()).thenReturn(new ArrayList<>());
		List<OptionalProgramRequirement> res = programService.getAllOptionalProgramRulesList();
		assertThat(res).isNotNull().isEmpty();
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
    	List<ProgramRequirement> res = programService.getAllProgramRuleList(programCode);
		assertThat(res).isNotNull().hasSize(2);
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
    
    private RequirementTypeCodeEntity createRequirementTypeCode() {
    	RequirementTypeCodeEntity obj = new RequirementTypeCodeEntity();
		obj.setReqTypeCode("M");
		obj.setLabel("SASD");
		return obj;
	}
    
    @Test
    public void testdeleteRequirementType_with_APICallReturnsFalse() {
    	String requirementType = "M";
    	Mockito.when(programRequirementRepository.existsByRequirementTypeCode(requirementType)).thenReturn(new ArrayList<ProgramRequirementEntity>());
        int success = programService.deleteRequirementTypeCode(requirementType);
        assertEquals(1,success);
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testdeleteRequirementType_with_APICallReturnsTrue() {
    	String requirementType = "M";
    	List<ProgramRequirementEntity> list = new ArrayList<ProgramRequirementEntity>();
    	ProgramRequirementEntity code = new ProgramRequirementEntity();
    	code.setGraduationProgramCode("2018-EN");
    	ProgramRequirementCodeEntity prcode = new ProgramRequirementCodeEntity();
    	prcode.setProReqCode("100");
    	prcode.setLabel("asdasd");
    	RequirementTypeCodeEntity typeCode = new RequirementTypeCodeEntity();
    	typeCode.setReqTypeCode("M");
    	prcode.setRequirementTypeCode(typeCode);
    	code.setProgramRequirementCode(prcode);
    	list.add(code);
    	Mockito.when(programRequirementRepository.existsByRequirementTypeCode(requirementType)).thenReturn(list);
        int success = programService.deleteRequirementTypeCode(requirementType);
        assertEquals(0,success);
        
    }
}

