package com.btv.user.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.btv.user.dto.UserCreateRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.dto.UserUpdateRequest;
import com.btv.user.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	User toEntity (UserCreateRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(UserUpdateRequest request, @MappingTarget User user);
	
	@Mapping(target = "roles", expression = "java(toRoleNames(user))")
	UserResponse toResponse (User user);

	default List<String> toRoleNames(User user) {
		if (user.getUserRoles() == null) {
			return List.of();
		}
		return user.getUserRoles().stream()
				.map(userRole -> userRole.getRole().getName().name())
				.toList();
	}
}
