package com.btv.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.enums.ArticleStatus;
import com.btv.article.service.ArticleService;
import com.btv.category.entity.Category;
import com.btv.category.repository.CategoryRepository;
import com.btv.user.entity.Role;
import com.btv.user.entity.User;
import com.btv.user.entity.UserRole;
import com.btv.user.enums.RoleType;
import com.btv.user.repository.RoleRepository;
import com.btv.user.repository.UserRepository;
import com.btv.user.repository.UserRoleRepository;

@SpringBootTest
@Transactional
class ArticleWorkflowIntegrationTests {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@AfterEach
	void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void reporterSubmitsAndAdminApprovesArticle() {
		User reporter = createUserWithRole(RoleType.REPORTER);
		Category category = createCategory();
		authenticate(reporter.getUsername(), "ROLE_REPORTER");

		ArticleCreateRequest request = new ArticleCreateRequest();
		request.setTitle("Workflow story");
		request.setSummary("Workflow story summary");
		request.setContent("Workflow story content");
		request.setCategoryId(category.getId());

		ArticleResponse created = articleService.createArticle(request);
		ArticleResponse pending = articleService.submitForReview(created.getId());

		assertThat(created.getStatus()).isEqualTo(ArticleStatus.DRAFT);
		assertThat(pending.getStatus()).isEqualTo(ArticleStatus.PENDING);

		User admin = createUserWithRole(RoleType.ADMIN);
		authenticate(admin.getUsername(), "ROLE_ADMIN");

		ArticleResponse approved = articleService.approveArticle(created.getId());

		assertThat(approved.getStatus()).isEqualTo(ArticleStatus.APPROVED);
		assertThat(approved.getPublishedAt()).isNotNull();
	}

	private User createUserWithRole(RoleType roleType) {
		String suffix = UUID.randomUUID().toString();
		Role role = roleRepository.findByName(roleType)
				.orElseGet(() -> {
					Role newRole = new Role();
					newRole.setName(roleType);
					newRole.setDescription(roleType.name() + " role");
					return roleRepository.save(newRole);
				});

		User user = new User();
		user.setUsername(roleType.name().toLowerCase() + "-" + suffix);
		user.setEmail(roleType.name().toLowerCase() + "-" + suffix + "@example.com");
		user.setPassword(passwordEncoder.encode("password123"));
		User savedUser = userRepository.save(user);

		UserRole userRole = new UserRole();
		userRole.setUser(savedUser);
		userRole.setRole(role);
		userRoleRepository.save(userRole);

		return savedUser;
	}

	private Category createCategory() {
		String suffix = UUID.randomUUID().toString();
		Category category = new Category();
		category.setName("Category " + suffix.substring(0, 8));
		category.setSlug("category-" + suffix);
		category.setDescription("Workflow test category");
		return categoryRepository.save(category);
	}

	private void authenticate(String username, String authority) {
		var authentication = new UsernamePasswordAuthenticationToken(
				username,
				null,
				List.of(new SimpleGrantedAuthority(authority)));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
