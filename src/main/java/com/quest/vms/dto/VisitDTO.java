package com.quest.vms.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {

	@JsonIgnore
	private Integer visitId;
	private Date visitDate;
	private String approvalStatus;
	private Boolean isVisitCompleted;
	private String placeOfVisit;
	private String reasonForVisit;
	private String cameFrom;
	private String passNumber;
	
	private ContactPersonDTO contactPerson;
	private TimeSlotDTO timeSlot;
	private List<DeviceDTO> devices;

}
