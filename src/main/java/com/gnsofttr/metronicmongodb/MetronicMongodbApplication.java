package com.gnsofttr.metronicmongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.gnsofttr.metronicmongodb.dao.RoleRepository;
import com.gnsofttr.metronicmongodb.model.Role;


@SpringBootApplication
@ComponentScan
public class MetronicMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetronicMongodbApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(RoleRepository roleRepository) {
//
//	    return args -> {
//
//	        Role adminRole = roleRepository.findByRole("ADMIN");
//	        if (adminRole == null) {
//	        	Role newAdminRole = new Role();
//	            newAdminRole.setRole("ADMIN");
//	            roleRepository.save(newAdminRole);
//	        }
//
//	        Role userRole = roleRepository.findByRole("USER");
//	        if (userRole == null) {
//	        	Role newUserRole = new Role();
//	            newUserRole.setRole("USER");
//	            roleRepository.save(newUserRole);
//	        }
//	    };
//
//	}
}
