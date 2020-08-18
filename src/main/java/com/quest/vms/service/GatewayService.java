package com.quest.vms.service;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.OtpDTO;
import com.quest.vms.dto.ValidateOtpDTO;
import com.quest.vms.dto.VisitorDTO;
import com.quest.vms.dto.VisitorsCountDTO;

public interface GatewayService {

	public GenericResponse<VisitorDTO> addVisitor(VisitorDTO visitor);

	public GenericResponse<VisitorDTO> getVisitorById(Integer id);

	public GenericResponse<?> deleteVisitor(Integer id);

	public GenericResponse<VisitorDTO> listVisitors(final String pageNo, final String pageSize, final String sortBy, final String orderBy);
	
	public GenericResponse<VisitorsCountDTO> visitorsCount();

	public GenericResponse<VisitorDTO> updateVisitor(VisitorDTO visitor);
	
	public GenericResponse<VisitorDTO> searchVisitor(String visitorType, String startDate, String endDate,
			String visitorName, String contactPersonName, String isActive);
	
	public GenericResponse<OtpDTO> generateOtp(final OtpDTO otpDto);
	
	public GenericResponse<Boolean> validateOtp(ValidateOtpDTO validateOtpDTO);
}
