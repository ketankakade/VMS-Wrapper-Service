package com.quest.vms.service;

import org.springframework.stereotype.Service;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.VisitorDto;

@Service
public class WrapperServiceImpl implements WrapperService {

	@Override
	public GenericResponse<VisitorDto> addVisitor(final VisitorDto visitorDto) {
		return null;
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
