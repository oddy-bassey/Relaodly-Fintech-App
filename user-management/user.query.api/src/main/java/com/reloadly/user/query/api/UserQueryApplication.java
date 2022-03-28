package com.reloadly.user.query.api;

import com.reloadly.user.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({AxonConfig.class})
@SpringBootApplication
public class UserQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserQueryApplication.class, args);
	}

}
