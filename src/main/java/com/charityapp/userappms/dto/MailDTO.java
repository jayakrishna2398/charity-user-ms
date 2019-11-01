package com.charityapp.userappms.dto;

import lombok.Data;

@Data
public class MailDTO {
	private String name;
	private String email;
	private String applicationName = "charityapp";
	
	private String code;
	
	private String link;
}
