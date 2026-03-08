package com.btv.user.mapper;

import com.btv.user.dto.role.RoleCreateRequest;
import com.btv.user.dto.role.RoleUpdateRequest;
import com.btv.user.dto.role.RoleResponse;
import com.btv.user.entity.Role;

import org.mapstruct.*;

@Mapper(
componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {

Role toEntity(RoleCreateRequest request);

@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
void updateEntity(
        RoleUpdateRequest request,
        @MappingTarget Role role
);

RoleResponse toResponse(Role role);

}