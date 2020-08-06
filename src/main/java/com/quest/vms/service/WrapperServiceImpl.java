package com.quest.vms.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quest.vms.common.utils.ErrorCodes;
import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDto;

@Service
public class WrapperServiceImpl implements WrapperService {

	@Value("${endpoint}")
	String endpoint;
 
	 private RestTemplate restTemplate;
	 
	 public WrapperServiceImpl() {
			restTemplate = new RestTemplate();
		}
	
	@Override
	public GenericResponse<VisitorDto> addVisitor(final VisitorDto visitorDto) {
		GenericResponse<VisitorDto> genericResponse = new GenericResponse<>(ErrorCodes.BAD_REQUEST_STATUS_CODE,
				"BAD_REQUEST", null, null);
		VisitorDto dto = restTemplate.postForObject(endpoint, visitorDto, VisitorDto.class);
		if(dto!=null) {
			genericResponse.setMessageCode(HttpStatus.OK.value());
			genericResponse.setMessage("Success");
			genericResponse.setData(Collections.singletonList(dto));
		}
		return genericResponse;
	}

	@Override
	public GenericResponse<VisitorDto> getVisitorById(final Integer visitorId) {
		return null;
	}

	@Override
	public GenericResponse<VisitorDto> listVisitors(final Integer pageNo, final Integer pageSize) {

		return null;
	}

	@Override
	public GenericResponse<?> deleteVisitor(Integer visitorId) {

		return null;
	}

}
