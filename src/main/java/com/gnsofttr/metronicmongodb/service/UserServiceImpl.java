package com.gnsofttr.metronicmongodb.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gnsofttr.metronicmongodb.dao.UserRepository;
import com.gnsofttr.metronicmongodb.model.User;
import com.gnsofttr.metronicmongodb.model.Role;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private EmailService mailService;

	@Autowired
	private SmsService smsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // kullanıcını aktifliğini sorgular

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		if (!user.isRegisterEmailActive()) {
			throw new DisabledException("User is not active");
		}

		if (!user.isRegisterSmsActive()) {
			throw new DisabledException("User is not active");

		}

		if (!user.isEnabled()) {
			throw new DisabledException("User is not active");

		}

		if (!user.isUserActive()) {
			throw new DisabledException("User is not active");

		}

		return user;
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(Role -> new SimpleGrantedAuthority(Role.getRole())).collect(Collectors.toList());
	}
	
	
	
	@Override
	public User save(User user) { // yeni bir kullanıcı kaydetmek için (  telefon ve mail doğrulama yapılması gereken durumlar için)

		User user1 = new User(user.getFirstName(), user.getLastName(), user.getUsername(),
				passwordEncoder.encode(user.getPassword()), user.getPhoneNumber(), Arrays.asList(new Role("USER")));  // Kullanıcının Rolü "ADMIN" olarak girilirse admin olarak "USER" olarak girilirse user olarak kaydeder
		String uuid = UUID.randomUUID().toString();
		user.setRegisterVerifyKey(uuid);
		user1.setRegisterVerifyKey(user.getRegisterVerifyKey());
		mailService.mailSenderFunction(user1.getUsername(), user1.getRegisterVerifyKey());

		try {

			String code = smsService.smsSender(user1.getPhoneNumber());
			user.setRegisterSmsCode(code);
			user1.setRegisterSmsCode(user.getRegisterSmsCode());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return userRepository.save(user1);

	}


	@Secured(value = "ADMIN") // sadece admin rolü olan erişebilir
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Secured(value = "ADMIN") // sadece admin rolü olan erişebilir
	public void saveUser(User user) { //kullanıcı kaydetmek için (  telefon ve mail doğrulama yapılması gerekmeye durumlar için)

		this.userRepository.save(user);

	}

	@Secured(value = "ADMIN") // sadece admin rolü olan erişebilir
	public User getUserById(String id) {
		Optional<User> optional = userRepository.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		}

		else {
			throw new RuntimeException(" User not found for id :: " + id);
		}
		return user;
	}

	@Secured(value = "ADMIN") // sadece admin rolü olan erişebilir
	public void deleteUserById(String id) {

		this.userRepository.deleteById(id);

	}

	@Override
	public boolean findByRegisterVerifyKey(String registerVerifyKey) {

		User user = userRepository.findByRegisterVerifyKey(registerVerifyKey);
		if (user != null) {

			return true;
		} else {
			return false;
		}
	}


	@Override
	public Optional<User> findUserByPasswordResetToken(String passwordResetToken) {

		return userRepository.findByPasswordResetToken(passwordResetToken);
	}

}
