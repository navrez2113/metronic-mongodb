package com.gnsofttr.metronicmongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


import com.gnsofttr.metronicmongodb.model.Role;



@Repository
@EnableMongoRepositories(basePackageClasses =RoleRepository.class)
public interface RoleRepository  extends MongoRepository<Role, String>{
	
	Role findByRole(String role);

}
