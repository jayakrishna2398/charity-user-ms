package com.charityapp.userappms.dto;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
	private String subject="ForgotPassword";
	private String text;
	private String to;
}
