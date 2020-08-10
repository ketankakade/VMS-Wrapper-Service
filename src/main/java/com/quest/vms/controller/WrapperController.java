package com.quest.vms.controller;

import static com.quest.vms.common.utils.VmsConstants.VISITOR;
import static com.quest.vms.common.utils.VmsConstants.ID;
import static com.quest.vms.common.utils.VmsConstants.GATEWAY_URL_PATH;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDTO;
import com.quest.vms.service.WrapperService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/" + GATEWAY_URL_PATH)
@Api(value = "Visitor Management System", description = "Operations pertaining to Visitor Management System")
@Slf4j
public class WrapperController {

	@Autowired
	private WrapperService wrapperService;

	@ApiOperation(value = "Add a Visitor to system")
	@PostMapping(VISITOR)
	public ResponseEntity<GenericResponse<VisitorDTO>> addVisitor(@Valid @RequestBody VisitorDTO visitor) {
		try {
			GenericResponse<VisitorDTO> createVisitorGenericRes = wrapperService.addVisitor(visitor);
			return ResponseEntity.status(createVisitorGenericRes.getStatusCode()).body(createVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get User by Id")
	@GetMapping(VISITOR + "/{" + ID + "}")
	public ResponseEntity<GenericResponse<VisitorDTO>> getVisitorById(@PathVariable(value = ID) Integer id) {
		try {
			GenericResponse<VisitorDTO> getVisitorGenericRes = wrapperService.getVisitorById(id);
			return ResponseEntity.status(getVisitorGenericRes.getStatusCode()).body(getVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get All visistors from system")
	@GetMapping(VISITOR)
	public ResponseEntity<GenericResponse<VisitorDTO>> listVisitors(
			@RequestParam(value = "index", defaultValue = "0", required = false) String index,
			@RequestParam(value = "size", defaultValue = "10", required = false) String size,
			@RequestParam(value = "sortBy", defaultValue = "firstName", required = false) String sort) {
		log.info("list visitor");
		try {
			GenericResponse<VisitorDTO> listVisitorGenericRes = wrapperService.listVisitors(index, size, sort);
			return ResponseEntity.status(listVisitorGenericRes.getStatusCode()).body(listVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Delete Visitor from system")
	@DeleteMapping(VISITOR + "/{id}")
	public ResponseEntity<GenericResponse<?>> deleteVisitor(@PathVariable(value = "id") Integer visitorId) {
		try {
			GenericResponse<?> userToBeDeleted = wrapperService.deleteVisitor(visitorId);
			return ResponseEntity.status(userToBeDeleted.getStatusCode()).body(userToBeDeleted);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
