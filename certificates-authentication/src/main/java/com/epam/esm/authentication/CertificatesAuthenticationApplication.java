package com.epam.esm.authentication;

import com.epam.esm.authentication.config.AuthorizationServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AuthorizationServerConfig.class)
public class CertificatesAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificatesAuthenticationApplication.class, args);
	}

}
