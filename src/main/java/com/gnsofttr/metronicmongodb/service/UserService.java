package com.gnsofttr.metronicmongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.gnsofttr.metronicmongodb.model.User;

@Service
public interface UserService extends UserDetailsService {

	User save(User user); // register için kullanıcı kaydı

	List<User> getAllUsers(); // bütün kullanıcıları listeleme

	void saveUser(User user); // parola sıfırlama için kullanıcı kaydı

	User getUserById(String id);

	void deleteUserById(String id); // kullanıcı silme

	boolean findByRegisterVerifyKey(String registerVerifyKey); // kullanıcı kaydı yaparaken maile gelen email doğrulama kodu														

	Optional<User> findUserByPasswordResetToken(String passwordResetToken);

}
