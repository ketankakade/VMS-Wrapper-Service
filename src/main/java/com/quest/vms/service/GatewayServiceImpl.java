package com.quest.vms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDTO;

@Service
public class GatewayServiceImpl implements GatewayService {

	@Value("${endpoint}")
	String endpoint;
	
	@Value("${getVisitorUrl}")
	String getVisitorUrl;
	
	@Value("${listVisitorsUrl}")
	String listVisitorsUrl;
	
	@Value("${deleteVisitorUrl}")
	String deleteVisitorUrl;

	private RestTemplate restTemplate;

	public GatewayServiceImpl() {
		restTemplate = new RestTemplate();
	}

	@Override
	public GenericResponse<VisitorDTO> addVisitor(final VisitorDTO visitorDto) {
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> createVisitorGenericRes = restTemplate.postForObject(endpoint, visitorDto,
				GenericResponse.class);
		return createVisitorGenericRes;
	}

	@Override
	public GenericResponse<VisitorDTO> getVisitorById(final Integer visitorId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", visitorId);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> getVisitorGenericRes = restTemplate.getForObject(getVisitorUrl, GenericResponse.class,
				params);
		return getVisitorGenericRes;
	}

	@Override
	public GenericResponse<VisitorDTO> listVisitors(final String index, final String size, final String sortBy, final String orderBy) {
		Map<String, String> params = new HashMap<>();
		params.put("index", index);
		params.put("size", size);
		params.put("sortBy", sortBy);
		params.put("orderBy", orderBy);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> listVisitorGenericRes = restTemplate.getForObject(listVisitorsUrl, GenericResponse.class,
				params);
		return listVisitorGenericRes;
	}

	@Override
	public GenericResponse<?> deleteVisitor(Integer visitorId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", visitorId);
		@SuppressWarnings("unchecked")
		GenericResponse<VisitorDTO> userToBeDeleted = restTemplate.getForObject(getVisitorUrl, GenericResponse.class, params);
		if (userToBeDeleted.getData() == null) {
			userToBeDeleted.setMessage("Delete visitor Fails");
		} else {
			restTemplate.delete(deleteVisitorUrl, params);
			userToBeDeleted.setMessage("Delete visitor success");
			userToBeDeleted.setStatusCode(HttpStatus.OK.value());
		}
		return userToBeDeleted;
	}

}
