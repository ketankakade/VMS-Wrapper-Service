package com.quest.vms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ContactPersonDTO {
	
	@JsonIgnore
	private Integer contactPersonId;
	
	@Size(min = 3, max = 50, message = "firstName must be between 3 and 50 characters")
	@NotBlank(message = "firstName cannot be null or empty")
	private String firstName;
	
	@Size(min = 3, max = 50, message = "lastName must be between 3 and 50 characters")
	@NotBlank(message = "lastName cannot be null or empty")
	private String lastName;
	
	@NotBlank(message = "contactNo cannot be null or empty")
	@Size(min = 10, max = 20, message = "Please enter valid mobile number with country code")
	private String contactNo;
	
	@Email(message = "email should be valid")
	@NotBlank(message = "email cannot be null or empty")
	private String email;

}
