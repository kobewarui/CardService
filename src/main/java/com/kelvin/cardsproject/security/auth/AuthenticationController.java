package com.kelvin.cardsproject.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationService authenticationService) {
		
		this.authenticationService= authenticationService;
		
	}
	
	@PostMapping("/authenticate-user")
	public ResponseEntity<AuthenticationResponse>authenticate(
			@RequestBody AuthenticationRequest request){
		
		return ResponseEntity.ok(authenticationService.authenticate(request));
		
	}

}
