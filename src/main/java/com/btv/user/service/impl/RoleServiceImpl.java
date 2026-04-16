package com.btv.user.service.impl;

import com.btv.common.exception.BadRequestException;
import com.btv.common.exception.ResourceNotFoundException;
import com.btv.user.dto.role.RoleCreateRequest;
import com.btv.user.dto.role.RoleUpdateRequest;
import com.btv.user.dto.role.RoleResponse;
import com.btv.user.entity.Role;
import com.btv.user.enums.RoleType;
import com.btv.user.mapper.RoleMapper;
import com.btv.user.repository.RoleRepository;
import com.btv.user.repository.UserRoleRepository;
import com.btv.user.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

private final RoleRepository roleRepository;
private final RoleMapper roleMapper;
private final UserRoleRepository userRoleRepository;

@Override
public RoleResponse createRole(RoleCreateRequest request) {

    Role role = roleMapper.toEntity(request);
    role.setName(parseRoleType(request.getName()));
    Role savedRole = roleRepository.save(role);

    return roleMapper.toResponse(savedRole);
}

@Override
public RoleResponse updateRole(UUID roleId, RoleUpdateRequest request) {

    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

    roleMapper.updateEntity(request, role);
    if (request.getName() != null) {
        role.setName(parseRoleType(request.getName()));
    }

    return roleMapper.toResponse(role);
}

@Override
public RoleResponse getRoleById(UUID roleId) {

    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

    return roleMapper.toResponse(role);
}

@Override
public Page<RoleResponse> getAllRoles(Pageable pageable) {

    return roleRepository.findAll(pageable)
            .map(roleMapper::toResponse);
}

@Override
public void deleteRole(UUID roleId) {
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    if (userRoleRepository.existsByRoleId(roleId)) {
        throw new BadRequestException("Role is assigned to one or more users");
    }
    roleRepository.delete(role);
}

private RoleType parseRoleType(String name) {
    try {
        return RoleType.valueOf(name.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
        throw new BadRequestException("Invalid role name");
    }
}

}
