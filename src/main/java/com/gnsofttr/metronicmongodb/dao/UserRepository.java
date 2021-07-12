package com.gnsofttr.metronicmongodb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.gnsofttr.metronicmongodb.model.User;

@Repository
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);

	List<User> findByPassword(String password);

	User findByRegisterVerifyKey(String registerVerifyKey);

	Optional<User> findByPasswordResetToken(String passwordResetToken);

	User findByPhoneNumber(String phoneNumber);

	User findByRegisterSmsCode(String registerSmsCode);

	User findByLoginSmsCode(String loginSmsCode);

	Optional<User> findById(String id); 

	void deleteUserById(String id); // admin de kullanıcı silmek için (id ye göre sorgulama)

	Optional<User> getByUsername(String username);

}
