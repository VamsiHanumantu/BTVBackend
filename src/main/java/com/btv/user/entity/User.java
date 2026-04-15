package com.btv.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.btv.article.entity.Article;
import com.btv.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
public class User extends BaseEntity{

	@Column( nullable = false, length = 50)
	private String username;
	
	@Column( nullable = false, length = 100)
	private String email;
	
	@Column( nullable = false)
	private String password;
	
	@Column ( nullable = false)
	private Boolean isActive = true;
	
	@Column     
	private LocalDateTime lastLoginAt;
	
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Article> articles = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserRole> userRoles = new ArrayList<>();
}
