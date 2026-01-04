package com.btv.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.btv.common.entity.BaseEntity;
import com.btv.user.enums.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "roles", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name")
})
@Getter
@Setter
public class Role extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private RoleType name;
	
	@Column(length = 100)
	private String description;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<UserRole> userRoles = new ArrayList<>();
}
