package com.btv.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.btv.user.entity.Role;
import com.btv.user.entity.User;
import com.btv.user.entity.UserRole;
import com.btv.user.enums.RoleType;
import com.btv.user.repository.RoleRepository;
import com.btv.user.repository.UserRepository;
import com.btv.user.repository.UserRoleRepository;

@Configuration
public class AdminBootstrapConfig {

	@Bean
	public CommandLineRunner adminBootstrap(
			@Value("${app.bootstrap.admin.enabled}") boolean enabled,
			@Value("${app.bootstrap.admin.username}") String username,
			@Value("${app.bootstrap.admin.email}") String email,
			@Value("${app.bootstrap.admin.password}") String password,
			RoleRepository roleRepository,
			UserRepository userRepository,
			UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			ensureRoles(roleRepository);

			if (!enabled || userRepository.existsByUsername(username)) {
				return;
			}

			Role adminRole = roleRepository.findByName(RoleType.ADMIN)
					.orElseThrow();

			User admin = new User();
			admin.setUsername(username);
			admin.setEmail(email);
			admin.setPassword(passwordEncoder.encode(password));
			User savedAdmin = userRepository.save(admin);

			UserRole userRole = new UserRole();
			userRole.setUser(savedAdmin);
			userRole.setRole(adminRole);
			userRoleRepository.save(userRole);
		};
	}

	private void ensureRoles(RoleRepository roleRepository) {
		List.of(RoleType.ADMIN, RoleType.REPORTER, RoleType.USER).forEach(roleType ->
			roleRepository.findByName(roleType).orElseGet(() -> {
				Role role = new Role();
				role.setName(roleType);
				role.setDescription(roleType.name() + " role");
				return roleRepository.save(role);
			})
		);
	}
}
