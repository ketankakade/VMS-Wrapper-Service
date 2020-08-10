package com.quest.vms.controller;

import static com.quest.vms.common.utils.VmsConstants.CREATE_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.DELETE_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.GET_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.ID;
import static com.quest.vms.common.utils.VmsConstants.LIST_VISITOR;
import static com.quest.vms.common.utils.VmsConstants.WRAPPER_URL_PATH;

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

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDTO;
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

	@ApiOperation(value = "Add a Visitor to system")
	@PostMapping(CREATE_VISITOR)
	public ResponseEntity<GenericResponse<VisitorDTO>> addVisitor(@Valid @RequestBody VisitorDTO visitor) {
		try {
			GenericResponse<VisitorDTO> createVisitorGenericRes = wrapperService.addVisitor(visitor);
			return ResponseEntity.status(createVisitorGenericRes.getMessageCode()).body(createVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get User by Id")
	@GetMapping(GET_VISITOR + "/{" + ID + "}")
	public ResponseEntity<GenericResponse<VisitorDTO>> getVisitorById(@PathVariable(value = ID) Integer id) {
		try {
			GenericResponse<VisitorDTO> getVisitorGenericRes = wrapperService.getVisitorById(id);
			return ResponseEntity.status(getVisitorGenericRes.getMessageCode()).body(getVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get All visistors from system")
	@GetMapping(LIST_VISITOR)
	public ResponseEntity<GenericResponse<VisitorDTO>> listVisitors(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) String pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) String pageSize,
			@RequestParam(value = "sort", defaultValue = "firstName", required = false) String sort) {
		log.info("list visitor");
		try {
			GenericResponse<VisitorDTO> listVisitorGenericRes = wrapperService.listVisitors(pageNo, pageSize, sort);
			return ResponseEntity.status(listVisitorGenericRes.getMessageCode()).body(listVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Delete Visitor from system")
	@DeleteMapping(DELETE_VISITOR + "/{id}")
	public ResponseEntity<GenericResponse<?>> deleteVisitor(@PathVariable(value = "id") Integer visitorId) {
		try {
			GenericResponse<?> userToBeDeleted = wrapperService.deleteVisitor(visitorId);
			return ResponseEntity.status(userToBeDeleted.getMessageCode()).body(userToBeDeleted);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
