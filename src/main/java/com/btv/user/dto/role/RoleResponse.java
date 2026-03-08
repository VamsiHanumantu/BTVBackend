package com.btv.user.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleResponse {

private UUID id;
private String name;
private String description;

}