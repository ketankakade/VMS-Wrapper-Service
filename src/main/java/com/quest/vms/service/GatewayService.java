package com.quest.vms.service;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDTO;

public interface GatewayService {

	public GenericResponse<VisitorDTO> addVisitor(VisitorDTO visitor);

	public GenericResponse<VisitorDTO> getVisitorById(Integer id);

	public GenericResponse<?> deleteVisitor(Integer id);

	public GenericResponse<VisitorDTO> listVisitors(final String pageNo, final String pageSize, final String sortBy, final String orderBy);

	public GenericResponse<VisitorDTO> updateVisitor(VisitorDTO visitor);
}
