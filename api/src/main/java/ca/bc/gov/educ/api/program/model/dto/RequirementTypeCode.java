package ca.bc.gov.educ.api.program.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class RequirementTypeCode extends BaseModel {

	private String reqTypeCode; 
	private String label; 
	private int displayOrder; 
	private String description;	
	private Date effectiveDate; 
	private Date expiryDate;
	
	
	@Override
	public String toString() {
		return "RequirementTypeCode [reqTypeCode=" + reqTypeCode + ", label=" + label + ", displayOrder=" + displayOrder
				+ ", description=" + description + ", effectiveDate=" + effectiveDate + ", expiryDate=" + expiryDate
				+ "]";
	}
	
	
	
}