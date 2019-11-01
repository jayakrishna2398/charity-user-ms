package com.charityapp.userappms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.charityapp.userappms.dto.ForgotPasswordDTO;
import com.charityapp.userappms.dto.MailDTO;

@Service
public class MailClient {

	@Value("${mail_enabled}")
	private Boolean mailEnabled;


	private String apiUrl = "https://charity-notification.herokuapp.com";
	
	@Autowired
	RestTemplate restTemplate;

	public void sendMail(final MailDTO mailDTO) {

		if (mailEnabled) {
			ResponseEntity<Void> postForEntity = restTemplate.postForEntity(apiUrl + "/mail/registeruser", mailDTO,
					Void.class);
			System.out.println(postForEntity);
		}
	}

	// Forgot password
	public void sendMailToUser(final ForgotPasswordDTO mailDTO) {
		if (mailEnabled) {
			ResponseEntity<Void> postForEntity = restTemplate.postForEntity(apiUrl + "/mail/send", mailDTO, Void.class);
			System.out.println(postForEntity);
		}
	}
}
