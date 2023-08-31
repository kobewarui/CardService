package com.kelvin.cardsproject.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kelvin.cardsproject.repositories.UserRepository;
import com.kelvin.cardsproject.security.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthenticationService(UserRepository userRepository,AuthenticationManager authenticationManager,JwtService jwtService) {
		
		this.userRepository = userRepository;
		this.authenticationManager=authenticationManager;
		this.jwtService=jwtService;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
        } catch (AuthenticationException e) {
            // Handle authentication failure (wrong username or password)
            return AuthenticationResponse.builder()
                    .message("Wrong username or password")
                    .build();
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        
        System.out.println("User Token: "+jwtToken);
        // Handle successful authentication
        return AuthenticationResponse.builder()
                .message("User authenticated successfully")
                .token(jwtToken)
                .build();

}
}
