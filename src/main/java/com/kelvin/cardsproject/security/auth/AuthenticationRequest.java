package com.kelvin.cardsproject.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	String email;
	String password;
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	
	
	
}
