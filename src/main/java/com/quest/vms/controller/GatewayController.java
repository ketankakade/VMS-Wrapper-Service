package com.quest.vms.controller;

import static com.quest.vms.common.utils.VmsConstants.GATEWAY_URL_PATH;
import static com.quest.vms.common.utils.VmsConstants.ID;
import static com.quest.vms.common.utils.VmsConstants.VISITOR;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDTO;
import com.quest.vms.dto.VisitorsCountDTO;
import com.quest.vms.service.GatewayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/" + GATEWAY_URL_PATH)
@Api(value = "Visitor Management System", description = "Operations pertaining to Visitor Management System")
@Slf4j
public class GatewayController {

	private static final String VISITORCOUNT = "/visitorscount";
	@Autowired
	private GatewayService gatewayService;

	@ApiOperation(value = "Add a Visitor to system")
	@PostMapping(VISITOR)
	public ResponseEntity<GenericResponse<VisitorDTO>> addVisitor(@Valid @RequestBody VisitorDTO visitor) {
		try {
			GenericResponse<VisitorDTO> createVisitorGenericRes = gatewayService.addVisitor(visitor);
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
			GenericResponse<VisitorDTO> getVisitorGenericRes = gatewayService.getVisitorById(id);
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
			@RequestParam(value = "sortBy", defaultValue = "firstName", required = false) String sort,
			@RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy) {

		log.info("list visitor");
		try {
			GenericResponse<VisitorDTO> listVisitorGenericRes = gatewayService.listVisitors(index, size, sort, orderBy);
			return ResponseEntity.status(listVisitorGenericRes.getStatusCode()).body(listVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get visitors count to display on dashboard")
	@GetMapping(VISITORCOUNT)
	public ResponseEntity<GenericResponse<VisitorsCountDTO>> getVisitorsCount()
			{
		log.info("list visitor count");
		try {
			GenericResponse<VisitorsCountDTO> listVisitorGenericRes = gatewayService.visitorsCount();
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
			GenericResponse<?> userToBeDeleted = gatewayService.deleteVisitor(visitorId);
			return ResponseEntity.status(userToBeDeleted.getStatusCode()).body(userToBeDeleted);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Update Visitor details")
	@PutMapping(VISITOR)
	public ResponseEntity<GenericResponse<VisitorDTO>> updateVisitor(@RequestBody VisitorDTO visitor) {
		try {
			GenericResponse<VisitorDTO> updateVisitorGenericResponse = gatewayService.updateVisitor(visitor);
			return ResponseEntity.status(updateVisitorGenericResponse.getStatusCode())
					.body(updateVisitorGenericResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@ApiOperation(value = "Get All visistors from system")
	@GetMapping("/listVisitor")
	public ResponseEntity<GenericResponse<VisitorDTO>> searchVisitor(
			// approved or not
			@RequestParam(value = "visitorType", required = false) String visitorType,
			// if not specified, default is today's date
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			// visitor name
			@RequestParam(value = "visitorName", required = false) String visitorName,
			// get all visitor by contact person name
			@RequestParam(value = "contactPersonName", required = false) String contactPersonName,
			@RequestParam(value = "isActive", required = false) String isActive) {
		log.info("list visitor");
		try {
			GenericResponse<VisitorDTO> listVisitorGenericRes = gatewayService.searchVisitor(visitorType, startDate, endDate, visitorName, contactPersonName, isActive);
			return ResponseEntity.status(listVisitorGenericRes.getStatusCode()).body(listVisitorGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
