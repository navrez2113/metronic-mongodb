package com.gnsofttr.metronicmongodb.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Document
public class User implements UserDetails {

	@Id
	@Field("_id")
	private String id;

	private String firstName;

	private String lastName;

	@Indexed(unique = true)
	private String username;

	private String password;

	private String phoneNumber;

	private String registerVerifyKey;

	private String passwordResetToken;

	private boolean registerEmailActive = false;

	private String loginSmsCode;

	private boolean loginSmsActive = false;

	private String registerSmsCode;

	private boolean registerSmsActive = false;

	private boolean userActive = true;

	private boolean adminOtpActive = false;

	@DBRef
	private Collection<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return roles.stream().map(Role -> new SimpleGrantedAuthority(Role.getRole())).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRegisterVerifyKey() {
		return registerVerifyKey;
	}

	public void setRegisterVerifyKey(String registerVerifyKey) {
		this.registerVerifyKey = registerVerifyKey;
	}

	public String getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public boolean isRegisterEmailActive() {
		return registerEmailActive;
	}

	public void setRegisterEmailActive(boolean registerEmailActive) {
		this.registerEmailActive = registerEmailActive;
	}

	public String getLoginSmsCode() {
		return loginSmsCode;
	}

	public void setLoginSmsCode(String loginSmsCode) {
		this.loginSmsCode = loginSmsCode;
	}

	public boolean isLoginSmsActive() {
		return loginSmsActive;
	}

	public void setLoginSmsActive(boolean loginSmsActive) {
		this.loginSmsActive = loginSmsActive;
	}

	public String getRegisterSmsCode() {
		return registerSmsCode;
	}

	public void setRegisterSmsCode(String registerSmsCode) {
		this.registerSmsCode = registerSmsCode;
	}

	public boolean isRegisterSmsActive() {
		return registerSmsActive;
	}

	public void setRegisterSmsActive(boolean registerSmsActive) {
		this.registerSmsActive = registerSmsActive;
	}

	public boolean isUserActive() {
		return userActive;
	}

	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}

	public boolean isAdminOtpActive() {
		return adminOtpActive;
	}

	public void setAdminOtpActive(boolean adminOtpActive) {
		this.adminOtpActive = adminOtpActive;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public User(String firstName, String lastName, String username, String password, String phoneNumber,
			Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.roles = roles;
	}

	public User() {
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", phoneNumber=" + phoneNumber + ", registerVerifyKey=" + registerVerifyKey
				+ ", passwordResetToken=" + passwordResetToken + ", registerEmailActive=" + registerEmailActive
				+ ", loginSmsCode=" + loginSmsCode + ", loginSmsActive=" + loginSmsActive + ", registerSmsCode="
				+ registerSmsCode + ", registerSmsActive=" + registerSmsActive + ", userActive=" + userActive
				+ ", adminOtpActive=" + adminOtpActive + ", roles=" + roles + "]";
	}

}
