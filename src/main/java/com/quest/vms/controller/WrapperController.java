package com.quest.vms.controller;

import static com.quest.vms.common.utils.VmsConstants.CREATE_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.DELETE_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.GET_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.ID;
import static com.quest.vms.common.utils.VmsConstants.LIST_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.WRAPPER_URL_PATH;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.quest.vms.common.utils.ErrorCodes;
import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDto;
import com.quest.vms.service.WrapperService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/" + WRAPPER_URL_PATH)
@Api(value = "Visitor Management System", description = "Operations pertaining to Visitor Management System")
@Slf4j
public class WrapperController {

	@Autowired
	private WrapperService wrapperService;

	@Value("${endpoint}")
	String endpoint;

	private RestTemplate restTemplate;

	public WrapperController() {
		restTemplate = new RestTemplate();
	}

	@ApiOperation(value = "Add a Visitor to system")
	@PostMapping(CREATE_VISITOR)
	public ResponseEntity<GenericResponse<VisitorDto>> addVisitor(@Valid @RequestBody VisitorDto visitor) {
		try {
			@SuppressWarnings("unchecked")
			GenericResponse<VisitorDto> createVisitorGenericRes = restTemplate.postForObject(endpoint, visitor,
					GenericResponse.class);
			return ResponseEntity.status(createVisitorGenericRes.getMessageCode()).body(createVisitorGenericRes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get User by Id")
	@GetMapping(GET_VISITOR + "/{" + ID + "}")
	public ResponseEntity<GenericResponse<VisitorDto>> getVisitorById(@PathVariable(value = ID) Integer id) {
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("id", id);
			String url = "http://localhost:8083/visitor/get-visitor/{id}";
			@SuppressWarnings("unchecked")
			GenericResponse<VisitorDto> getVisitorGenericRes = restTemplate.getForObject(url, GenericResponse.class,
					params);
			return ResponseEntity.status(getVisitorGenericRes.getMessageCode()).body(getVisitorGenericRes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get All visistors from system")
	@GetMapping(LIST_VISITOR)
	public ResponseEntity<GenericResponse<VisitorDto>> listVisitors(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) String pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) String pageSize,
			@RequestParam(value = "sort", defaultValue = "firstName", required = false) String sort) {
		log.info("list visitor");
		Map<String, String> params = new HashMap<>();
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		String url = "http://localhost:8083/visitor/list-visitor?pageNo={pageNo}&pageSize={pageSize}&sort={sort}";
		try {
			@SuppressWarnings("unchecked")
			GenericResponse<VisitorDto> listVisitorGenericRes = restTemplate.getForObject(url, GenericResponse.class,
					params);
			return ResponseEntity.status(listVisitorGenericRes.getMessageCode()).body(listVisitorGenericRes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@SuppressWarnings("null")
	@ApiOperation(value = "Delete Visitor from system")
	@DeleteMapping(DELETE_VISITOR + "/{id}")
	public ResponseEntity<GenericResponse<?>> deleteVisitor(@PathVariable(value = "id") Integer visitorId) {
		GenericResponse<VisitorDto> genericResponse = new GenericResponse<>(ErrorCodes.BAD_REQUEST_STATUS_CODE,
				"BAD_REQUEST", null, null);
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("id", visitorId);
			String url = "http://localhost:8083/visitor/delete-visitor/{id}";
			final String getUrl = "http://localhost:8083/visitor/get-visitor/{id}";
			@SuppressWarnings("unchecked")
			GenericResponse<VisitorDto> userToBeDeleted = restTemplate.getForObject(getUrl, GenericResponse.class,
					params);
			if (userToBeDeleted.getData() == null) {
				genericResponse.setMessage("Delete visitor Fails");
				return ResponseEntity.status(genericResponse.getMessageCode()).body(genericResponse);
			} else {
				restTemplate.delete(url, params);
				genericResponse.setMessage("Delete visitor success");
				genericResponse.setMessageCode(HttpStatus.OK.value());
				return ResponseEntity.status(genericResponse.getMessageCode()).body(genericResponse);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

}
