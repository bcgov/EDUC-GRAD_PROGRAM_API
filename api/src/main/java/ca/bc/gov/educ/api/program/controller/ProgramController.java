package ca.bc.gov.educ.api.program.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.service.ProgramService;
import ca.bc.gov.educ.api.program.util.ApiResponseModel;
import ca.bc.gov.educ.api.program.util.EducGradProgramApiConstants;
import ca.bc.gov.educ.api.program.util.GradValidation;
import ca.bc.gov.educ.api.program.util.PermissionsContants;
import ca.bc.gov.educ.api.program.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(EducGradProgramApiConstants.GRAD_PROGRAM_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Program Management Data.", description = "This API contains endpoints for Program Management Functionalities.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_PROGRAM_SETS_DATA","READ_GRAD_PROGRAM_RULES_DATA","READ_GRAD_PROGRAM_CODE_DATA","READ_GRAD_SPECIAL_CASE_DATA","READ_GRAD_LETTER_GRADE_DATA"})})
public class ProgramController {

    private static Logger logger = LoggerFactory.getLogger(ProgramController.class);

    @Autowired
    ProgramService programService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;
    
    private static final String PROGRAM_CODE="Program Code";
    private static final String PROGRAM_NAME="Program Name";
    private static final String SPECIAL_PROGRAM_ID="Special Program ID";
    private static final String REQUIREMENT_TYPE = "Requirement Type";
    private static final String RULE_CODE = "Rule Code";
    private static final String REQUIREMENT_NAME = "Requirement Name";

    @GetMapping(value=EducGradProgramApiConstants.GET_ALL_PROGRAM_MAPPING, produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    @Operation(summary = "Find All Programs", description = "Get All Programs", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK",content = @Content(array = @ArraySchema(schema = @Schema(implementation = GraduationProgramCode.class)))), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GraduationProgramCode>> getAllPrograms() { 
    	logger.debug("getAllPrograms : ");
    	List<GraduationProgramCode> programList = programService.getAllProgramList();
    	if(!programList.isEmpty()) {
    		return response.GET(programList,new TypeToken<List<GraduationProgramCode>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(value=EducGradProgramApiConstants.GET_PROGRAM_MAPPING, produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    @Operation(summary = "Find Specific Program", description = "Get a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK",content = @Content(array = @ArraySchema(schema = @Schema(implementation = GraduationProgramCode.class)))), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<GraduationProgramCode> getSpecificProgram(@PathVariable String programCode) { 
    	logger.debug("getSpecificProgram : ");
    	GraduationProgramCode gradProgram = programService.getSpecificProgram(programCode);
    	if(gradProgram != null) {
    		return response.GET(gradProgram);
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(value=EducGradProgramApiConstants.GET_ALL_PROGRAM_MAPPING,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM)
    @Operation(summary = "Create a Program", description = "Create a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<ApiResponseModel<GraduationProgramCode>> createGradPrograms(@Valid @RequestBody GraduationProgramCode gradProgram) { 
    	logger.debug("createGradPrograms : ");
    	validation.requiredField(gradProgram.getProgramCode(), PROGRAM_CODE);
       	validation.requiredField(gradProgram.getProgramName(), PROGRAM_NAME);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.CREATED(programService.createGradProgram(gradProgram));
    }
    
    @PutMapping(value=EducGradProgramApiConstants.GET_ALL_PROGRAM_MAPPING ,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM)
    @Operation(summary = "Update a Program", description = "Update a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<ApiResponseModel<GraduationProgramCode>> updateGradPrograms(@Valid @RequestBody GraduationProgramCode gradProgram) { 
    	logger.info("updateGradProgramsss : ");
    	validation.requiredField(gradProgram.getProgramCode(), PROGRAM_CODE);
      	validation.requiredField(gradProgram.getProgramName(), PROGRAM_NAME);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.UPDATED(programService.updateGradProgram(gradProgram));
    }
    
    @DeleteMapping(EducGradProgramApiConstants.DELETE_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_PROGRAM)
    @Operation(summary = "Delete a Program", description = "Delete a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND.")})
    public ResponseEntity<Void> deleteGradPrograms(@Valid @PathVariable String programCode) { 
    	logger.debug("deleteGradPrograms : ");
    	validation.requiredField(programCode, PROGRAM_CODE);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.DELETE(programService.deleteGradPrograms(programCode));
    }
    
    @GetMapping(value=EducGradProgramApiConstants.GET_ALL_PROGRAM_RULES,produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Get Program Rules", description = "Get Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<ProgramRequirement>> getAllProgramsRules(
    		@RequestParam(value = "programCode", required = true) String programCode) { 
    	logger.debug("get All Program Rules : ");
    	List<ProgramRequirement> programRuleList = programService.getAllProgramRuleList(programCode);
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<ProgramRequirement>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(value=EducGradProgramApiConstants.GET_ALL_PROGRAM_RULES,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM_RULES)
    @Operation(summary = "Create Program Rules", description = "Create Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<ProgramRequirement>> createGradProgramRules(@Valid @RequestBody ProgramRequirement gradProgramRule) { 
    	logger.debug("createGradProgramRules : ");
    	validation.requiredField(gradProgramRule.getGraduationProgramCode(), PROGRAM_CODE);
    	validation.requiredField(gradProgramRule.getProgramRequirementCode().getProReqCode(), RULE_CODE);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	return response.CREATED(programService.createGradProgramRules(gradProgramRule));
    }
    
    @PutMapping(value=EducGradProgramApiConstants.GET_ALL_PROGRAM_RULES,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM_RULES)
    @Operation(summary = "Update a Program Rules", description = "Update a Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<ApiResponseModel<ProgramRequirement>> updateGradProgramRules(@Valid @RequestBody ProgramRequirement gradProgramRule) { 
    	logger.debug("updateGradProgramRules : ");
    	validation.requiredField(gradProgramRule.getGraduationProgramCode(), PROGRAM_CODE);
    	validation.requiredField(gradProgramRule.getProgramRequirementCode().getProReqCode(), RULE_CODE);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	return response.UPDATED(programService.updateGradProgramRules(gradProgramRule));
    }
    
    @DeleteMapping(EducGradProgramApiConstants.DELETE_PROGRAM_RULES_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_PROGRAM_RULES)
    @Operation(summary = "Delete a Program Rule", description = "Delete a Program Rule", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND.")})
    public ResponseEntity<Void> deleteGradProgramRules(@PathVariable(value = "programRuleID", required = true) String programRuleID) { 
    	logger.debug("deleteGradProgramRules : ");    	
        return response.DELETE(programService.deleteGradProgramRules(UUID.fromString(programRuleID)));
    }
    
    /*
    @GetMapping(EducGradProgramApiConstants.GET_ALL_SPECIFIC_PROGRAM_RULES_BY_RULE)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Find Specific Rule Details", description = "Get a Specific Rule Detail", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<GradRuleDetails> getSpecificRuleDetails(@PathVariable String ruleCode) { 
    	logger.debug("getSpecificRuleDetails : ");
        return programService.getSpecificRuleDetails(ruleCode);
    }
    
    @GetMapping(EducGradProgramApiConstants.GET_REQUIREMENT_BY_REQUIREMENT_TYPE)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Check for Requirement Type", description = "Check if Requirement Type is valid", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public Boolean getRequirementByRequirementType(@PathVariable String typeCode) { 
    	logger.debug("getRequirementByRequirementType : ");
        return programService.getRequirementByRequirementType(typeCode);
    } 
    
    
    
    @GetMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING_BY_ID)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find Special Program", description = "Get Special Program By ID", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<GradSpecialProgram> getAllSpecialProgramsByID(@PathVariable String specialProgramID) { 
    	logger.debug("getAllSpecialProgramsByID : ");
        return response.GET(programService.getSpecialProgramByID(UUID.fromString(specialProgramID)));
    }
    
    @GetMapping(value=EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING, produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find All Special Programs", description = "Get All Special Programs", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradSpecialProgram>> getAllSpecialPrograms() { 
    	logger.debug("getAllSpecialPrograms : ");
    	List<GradSpecialProgram> specialProgramList = programService.getAllSpecialProgramList();
    	if(!specialProgramList.isEmpty()) {
    		return response.GET(specialProgramList,new TypeToken<List<GradSpecialProgram>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_BY_PROGRAM_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find All Special Programs by Program", description = "Get All Special Programs by Program Code", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradSpecialProgram>> getAllSpecialPrograms(@PathVariable String programCode) { 
    	logger.debug("getAllSpecialPrograms : ");
    	List<GradSpecialProgram> specialProgramList = programService.getAllSpecialProgramList(programCode);
    	if(!specialProgramList.isEmpty()) {
    		return response.GET(specialProgramList,new TypeToken<List<GradSpecialProgram>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    
    
    @GetMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find a Special Programs by Program and special program", description = "Get a Special Programs by Program Code and special program code", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<GradSpecialProgram> getSpecialPrograms(@PathVariable String programCode,@PathVariable String specialProgramCode) { 
    	logger.debug("getSpecialPrograms : ");
        return response.GET(programService.getSpecialProgram(programCode,specialProgramCode));
    }
    
    @PostMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Create Special Program", description = "Create Special Program", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradSpecialProgram>> createGradSpecialPrograms(@Valid @RequestBody GradSpecialProgram gradSpecialProgram) { 
    	logger.debug("createGradSpecailPrograms : ");
    	validation.requiredField(gradSpecialProgram.getProgramCode(), PROGRAM_CODE);
       	validation.requiredField(gradSpecialProgram.getSpecialProgramCode(), "Special Program Code");
       	validation.requiredField(gradSpecialProgram.getSpecialProgramName(), "Special Program Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.CREATED(programService.createGradSpecialProgram(gradSpecialProgram));
    }
    
    @PutMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Update Special Program", description = "Update Special Program", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradSpecialProgram>> updateGradSpecialPrograms(@Valid @RequestBody GradSpecialProgram gradSpecialProgram) { 
    	logger.info("updateGradProgramsss : ");
    	validation.requiredField(gradSpecialProgram.getProgramCode(), PROGRAM_CODE);
       	validation.requiredField(gradSpecialProgram.getSpecialProgramCode(), "Special Program Code");
       	validation.requiredField(gradSpecialProgram.getSpecialProgramName(), "Special Program Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.UPDATED(programService.updateGradSpecialPrograms(gradSpecialProgram));
    }
    
    @DeleteMapping(EducGradProgramApiConstants.DELETE_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Delete Special Program", description = "Delete Special Program", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND")})
    public ResponseEntity<Void> deleteGradSpecialPrograms(@PathVariable(value = "specialProgramID", required = true) String specialProgramID) { 
    	logger.debug("deleteGradPrograms : ");
    	validation.requiredField(specialProgramID, SPECIAL_PROGRAM_ID);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.DELETE(programService.deleteGradSpecialPrograms(UUID.fromString(specialProgramID)));
    }
    
    @GetMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Get Special Program Rules", description = "Get Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradSpecialProgramRule>> getAllSpecialProgramRules(
    		@RequestParam(value = "specialProgramID", required = true) String specialProgramID, 
            @RequestParam(value = "requirementType", required = false) String requirementType) { 
    	logger.debug("get All Special Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programService.getAllSpecialProgramRuleList(UUID.fromString(specialProgramID),requirementType,accessToken);
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Create Special Program Rules", description = "Create Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradSpecialProgramRule>> createGradSpecialProgramRules(@Valid @RequestBody GradSpecialProgramRule gradSpecialProgramRule) { 
    	logger.debug("createGradSpecialProgramRules : ");
    	validation.requiredField(gradSpecialProgramRule.getSpecialProgramID(), SPECIAL_PROGRAM_ID);
    	validation.requiredField(gradSpecialProgramRule.getRequirementType(), REQUIREMENT_TYPE);
    	validation.requiredField(gradSpecialProgramRule.getRuleCode(), RULE_CODE);
    	validation.requiredField(gradSpecialProgramRule.getRequirementName(), REQUIREMENT_NAME);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	return response.CREATED(programService.createGradSpecialProgramRules(gradSpecialProgramRule,accessToken));
    }
    
    @PutMapping(EducGradProgramApiConstants.GET_ALL_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Update Special Program Rules", description = "Update Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradSpecialProgramRule>> updateGradSpecialProgramRules(@Valid @RequestBody GradSpecialProgramRule gradSpecialProgramRule) { 
    	logger.debug("updateGradProgramRules : ");
    	validation.requiredField(gradSpecialProgramRule.getSpecialProgramID(), SPECIAL_PROGRAM_ID);
    	validation.requiredField(gradSpecialProgramRule.getRequirementType(), REQUIREMENT_TYPE);
    	validation.requiredField(gradSpecialProgramRule.getRuleCode(), RULE_CODE);
    	validation.requiredField(gradSpecialProgramRule.getRequirementName(), REQUIREMENT_NAME);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	return response.UPDATED(programService.updateGradSpecialProgramRules(gradSpecialProgramRule,accessToken));
    }
    
    @DeleteMapping(EducGradProgramApiConstants.DELETE_SPECIAL_PROGRAM_RULES_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Delete Special Program Rule", description = "Delete Special Program Rule", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND")})
    public ResponseEntity<Void> deleteGradSpecialProgramRules(@PathVariable(value = "programRuleID", required = true) String programRuleID) { 
    	logger.debug("deleteGradProgramRules : ");    	
        return response.DELETE(programService.deleteGradSpecialProgramRules(UUID.fromString(programRuleID)));
    }
    
    @GetMapping(EducGradProgramApiConstants.GET_SPECIAL_PROGRAM_RULES_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_CODE)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Find Special Program Rules by Program Code and Special Program Code and Requirement Type", description = "Get Special Program Rules by Program Code and Special Program Code and Requirement Type", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradSpecialProgramRule>> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(
    		@PathVariable(value = "programCode", required = true) String programCode, 
    		@PathVariable(value = "specialProgramCode", required = true) String specialProgramCode,
    		@PathVariable(value = "requirementType", required = true) String requirementType) { 
    	logger.debug("get Special Program Rules By Program Code And Special Program Code : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,accessToken);
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramApiConstants.GET_SPECIAL_PROGRAM_RULES_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_CODE_ONLY)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Find Special Program Rules by Program Code and Special Program Code", description = "Get Special Program Rules by Program Code and Special Program Code", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradSpecialProgramRule>> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(
    		@PathVariable(value = "programCode", required = true) String programCode, 
    		@PathVariable(value = "specialProgramCode", required = true) String specialProgramCode) { 
    	logger.debug("get Special Program Rules By Program Code And Special Program Code : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,null,accessToken);
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    */
    
    @GetMapping(value=EducGradProgramApiConstants.GET_PROGRAM_RULES,produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Get all Program Rules", description = "Get all Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<ProgramRequirement>> getAllProgramsRules() { 
    	logger.debug("get All Program Rules : ");
    	List<ProgramRequirement> programRuleList = programService.getAllProgramRulesList();
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<ProgramRequirement>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    /*
    @GetMapping(EducGradProgramApiConstants.GET_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Get all Special Program Rules", description = "Get all Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradSpecialProgramRule>> getAllSpecialProgramRules() { 
    	logger.debug("get All Special Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programService.getAllSpecialProgramRulesList(accessToken);
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    */
    
    
}