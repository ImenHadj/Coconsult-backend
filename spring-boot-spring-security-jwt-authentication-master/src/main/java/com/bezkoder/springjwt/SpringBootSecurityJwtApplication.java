package com.bezkoder.springjwt;

import com.bezkoder.springjwt.Vuser.VUser;
import com.bezkoder.springjwt.Vuser.VUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication

@ComponentScan(basePackages = {"com.bezkoder.springjwt", "com.bezkoder.springjwt.Service"})
@CrossOrigin(origins = "http://localhost:4200")
@EnableScheduling
public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
    SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}



	@Bean
	public CommandLineRunner commandLineRunner(
			VUserService service
	) {
		return args -> {
			service.register(VUser.builder()
					.username("Ali")
					.email("ali@mail.com")
					.password("aaa")
					.build());

			service.register(VUser.builder()
					.username("John")
					.email("john@mail.com")
					.password("aaa")
					.build());

			service.register(VUser.builder()
					.username("Anny")
					.email("anna@mail.com")
					.password("aaa")
					.build());
		};
	}


}
