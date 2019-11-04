package com.charityapp.userappms.dto;

import lombok.Data;

@Data
public class UserDTO {
	private Integer id;
	private String name;
	private String email;
	private Boolean active;
}
