package com.kelvin.cardsproject.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Kelvin Warui Maina",
						email = "kobewarui@gmail.com"
						),
				description = "Restful web service to create cards for authenticated users",
				title = "OpenApi Documentation for Cards Restful Web Service",
				version = "1.0"
				
				),
		servers = {
				@Server(
						
						description = "LOCAL ENVIRONMENT",
						url = "http://localhost:8080"
						)
		},
		security = {
				@SecurityRequirement(name =  "bearer authentication")
				
		}
		)
@SecurityScheme(
		name = "bearer authentication",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		)
public class OpenApiConfig {

}
