package com.btv.user.service;

import com.btv.user.dto.role.RoleCreateRequest;
import com.btv.user.dto.role.RoleUpdateRequest;
import com.btv.user.dto.role.RoleResponse;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

RoleResponse createRole(RoleCreateRequest request);

RoleResponse updateRole(UUID roleId, RoleUpdateRequest request);

RoleResponse getRoleById(UUID roleId);

Page<RoleResponse> getAllRoles(Pageable pageable);

void deleteRole(UUID roleId);

}
