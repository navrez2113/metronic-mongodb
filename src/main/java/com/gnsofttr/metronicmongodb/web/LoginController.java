package com.gnsofttr.metronicmongodb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnsofttr.metronicmongodb.dao.UserRepository;
import com.gnsofttr.metronicmongodb.model.User;
import com.gnsofttr.metronicmongodb.service.SmsService;


@Controller
public class LoginController {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SmsService smsService;

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/index")
	public String homePage() {

		return "index";
	}

	@GetMapping("/sms")
	public String loginSmsPage() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.isLoginSmsActive() == false && user.isAdminOtpActive()==true) {
			return "loginSms";
		} else {
			
			return "redirect:/index";
		}

	}

	@RequestMapping(value = "/loginSms", method = RequestMethod.GET)
	public String loginSmsVerify() throws Exception {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.isLoginSmsActive() == false && user.isAdminOtpActive()==true) {
			
			String loginCode = smsService.smsSender(user.getPhoneNumber());
			user.setLoginSmsCode(loginCode);

			userRepository.save(user);

			return "loginSms";
		} else {
			
			
			return "redirect:/index";
		}
	}

	@RequestMapping(value = "/loginSms", method = RequestMethod.POST)
	public String loginSms(@ModelAttribute("loginCode") String loginCode) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getLoginSmsCode().equals(loginCode)) {
			user.setLoginSmsActive(true);
			user.setLoginSmsCode(null);
			userRepository.save(user);
		} else {
			return "redirect:/index";
		}

		return "redirect:/index";
	}

}