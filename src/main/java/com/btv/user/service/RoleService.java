package com.btv.user.service;

import com.btv.user.dto.role.RoleCreateRequest;
import com.btv.user.dto.role.RoleUpdateRequest;
import com.btv.user.dto.role.RoleResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {

RoleResponse createRole(RoleCreateRequest request);

RoleResponse updateRole(UUID roleId, RoleUpdateRequest request);

RoleResponse getRoleById(UUID roleId);

List<RoleResponse> getAllRoles();

}