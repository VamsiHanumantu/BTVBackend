package com.btv.user.controller;

import com.btv.user.dto.role.RoleCreateRequest;
import com.btv.user.dto.role.RoleUpdateRequest;
import com.btv.user.dto.role.RoleResponse;
import com.btv.user.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

	private final RoleService roleService;

	@PostMapping
	public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleCreateRequest request) {
		RoleResponse response = roleService.createRole(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RoleResponse> updateRole(@PathVariable UUID id,
			@Valid @RequestBody RoleUpdateRequest request) {
		RoleResponse response = roleService.updateRole(id, request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleResponse> getRoleById(@PathVariable UUID id) {
		RoleResponse response = roleService.getRoleById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Page<RoleResponse>> getAllRoles(Pageable pageable) {
		Page<RoleResponse> response = roleService.getAllRoles(pageable);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
		roleService.deleteRole(id);
		return ResponseEntity.noContent().build();
	}

}
