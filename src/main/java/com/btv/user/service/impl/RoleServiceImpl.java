package com.btv.user.service.impl;

import com.btv.common.exception.ResourceNotFoundException;
import com.btv.user.dto.role.RoleCreateRequest;
import com.btv.user.dto.role.RoleUpdateRequest;
import com.btv.user.dto.role.RoleResponse;
import com.btv.user.entity.Role;
import com.btv.user.mapper.RoleMapper;
import com.btv.user.repository.RoleRepository;
import com.btv.user.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

private final RoleRepository roleRepository;
private final RoleMapper roleMapper;

@Override
public RoleResponse createRole(RoleCreateRequest request) {

    Role role = roleMapper.toEntity(request);
    Role savedRole = roleRepository.save(role);

    return roleMapper.toResponse(savedRole);
}

@Override
public RoleResponse updateRole(UUID roleId, RoleUpdateRequest request) {

    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

    roleMapper.updateEntity(request, role);

    return roleMapper.toResponse(role);
}

@Override
public RoleResponse getRoleById(UUID roleId) {

    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

    return roleMapper.toResponse(role);
}

@Override
public List<RoleResponse> getAllRoles() {

    return roleRepository.findAll()
            .stream()
            .map(roleMapper::toResponse)
            .toList();
}

}