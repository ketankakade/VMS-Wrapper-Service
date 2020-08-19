package com.quest.vms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.OtpDTO;
import com.quest.vms.dto.ValidateOtpDTO;
import com.quest.vms.dto.VisitorDTO;
import com.quest.vms.dto.VisitorsCountDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GatewayServiceImpl implements GatewayService {

	@Value("${addVisitorUrl}")
	String addVisitorUrl;

	@Value("${getVisitorUrl}")
	String getVisitorUrl;

	@Value("${listVisitorsUrl}")
	String listVisitorsUrl;

	@Value("${visitorsCountUrl}")
	String visitorsCountUrl;

	@Value("${deleteVisitorUrl}")
	String deleteVisitorUrl;

	@Value("${updateVisitorUrl}")
	String updateVisitorUrl;

	@Value("${filterListVisitor}")
	String filterListVisitor;
	
	@Value("${generateOtpUrl}")
	String generateOtpUrl;
	
	@Value("${generateEmailOtpUrl}")
	String generateEmailOtpUrl;
	
	@Value("${validateOtpUrl}")
	String validateOtpUrl;
	

	private RestTemplate restTemplate;

	public GatewayServiceImpl() {
		restTemplate = new RestTemplate();
	}

	@Override
	public GenericResponse<VisitorDTO> addVisitor(final VisitorDTO visitorDto) {
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> createVisitorGenericRes = restTemplate.postForObject(addVisitorUrl, visitorDto,
				GenericResponse.class);
		return createVisitorGenericRes;
	}

	@Override
	public GenericResponse<VisitorDTO> getVisitorById(final Integer visitorId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", visitorId);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> getVisitorGenericRes = restTemplate.getForObject(getVisitorUrl,
				GenericResponse.class, params);
		return getVisitorGenericRes;
	}

	@Override
	public GenericResponse<VisitorDTO> listVisitors(final String index, final String size, final String sortBy,
			final String orderBy) {
		Map<String, String> params = new HashMap<>();
		params.put("index", index);
		params.put("size", size);
		params.put("sortBy", sortBy);
		params.put("orderBy", orderBy);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> listVisitorGenericRes = restTemplate.getForObject(listVisitorsUrl,
				GenericResponse.class, params);
		return listVisitorGenericRes;
	}

	@Override
	public GenericResponse<VisitorsCountDTO> visitorsCount() {
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorsCountDTO> visitorCountGenericRes = restTemplate.getForObject(visitorsCountUrl,
				GenericResponse.class);
		return visitorCountGenericRes;
	}

	@Override
	public GenericResponse<?> deleteVisitor(Integer visitorId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", visitorId);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> userToBeDeleted = restTemplate.getForObject(getVisitorUrl, GenericResponse.class,
				params);
		if (userToBeDeleted.getData() == null) {
			userToBeDeleted.setMessage("Delete visitor Fails");
		} else {
			restTemplate.delete(deleteVisitorUrl, params);
			userToBeDeleted.setMessage("Delete visitor success");
			userToBeDeleted.setStatusCode(HttpStatus.OK.value());
		}
		return userToBeDeleted;
	}

	@Override
	public GenericResponse<VisitorDTO> updateVisitor(final VisitorDTO visitorDto) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", visitorDto.getVisitorId());
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> updatedVisitor = restTemplate.getForObject(getVisitorUrl, GenericResponse.class,
				params);
		if (updatedVisitor.getData() == null) {
			updatedVisitor.setMessage("visitor not found");
		} else {
			restTemplate.put(updateVisitorUrl, visitorDto);
		}
		return updatedVisitor;
	}

	@Override
	public GenericResponse<VisitorDTO> searchVisitor(String visitorType, String startDate, String endDate,
			String visitorName, String contactPersonName, String isActive) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<>();
		params.put("visitorType", visitorType);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("contactPersonName", contactPersonName);
		params.put("visitorName", visitorName);
		params.put("isActive", isActive);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> listVisitorGenericRes = restTemplate.getForObject(filterListVisitor,
				GenericResponse.class, params);
		return listVisitorGenericRes;
	}
	
	@Override
	public GenericResponse<OtpDTO> generateOtp(final OtpDTO otpDto) {
		OtpDTO otpGenericRes = restTemplate.postForObject(generateEmailOtpUrl, otpDto,
				OtpDTO.class);
		OtpDTO otpdtoObject = new OtpDTO();
		otpdtoObject.setEmail(otpGenericRes.getEmail());
		otpdtoObject.setOtpNumber(otpGenericRes.getOtpNumber());
		//log.info("Otp from code: " + otpGenericRes.getOTPNumber());
		@SuppressWarnings("unchecked")
		GenericResponse<OtpDTO> otpResult = restTemplate.postForObject(generateOtpUrl, otpdtoObject,
				GenericResponse.class);
		return otpResult;
	}	

	@Override
	public GenericResponse<Boolean> validateOtp(ValidateOtpDTO validateOtpDTO) {
		@SuppressWarnings("unchecked")
		GenericResponse<Boolean> validateOtpGenericRes = restTemplate.postForObject(validateOtpUrl, validateOtpDTO,
				GenericResponse.class);
		return validateOtpGenericRes;
	}
}
