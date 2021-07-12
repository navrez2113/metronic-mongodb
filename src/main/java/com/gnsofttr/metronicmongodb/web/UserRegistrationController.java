package com.gnsofttr.metronicmongodb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnsofttr.metronicmongodb.dao.UserRepository;
import com.gnsofttr.metronicmongodb.model.User;
import com.gnsofttr.metronicmongodb.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	public static String url = "http://localhost:8080/registration/verify"; // kaydı yapılan kullanıcıya gönderilen
																			// email doğrulama linki

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public User userRegistrationDto() { // yeni bir kullanıcı oluşturma
		return new User();
	}

	@GetMapping
	public String showRegistrationForm() { // kullanıcı kaydı formu

		return "registration";
	}

	@PostMapping
	public String registrationUserAccount(@ModelAttribute("user") User user) { // bilgileri girilen kullanıcıyı kayıt
																				// eder

		userService.save(user);

		return "redirect:/registration/smsSendForm?success";
	}

	@RequestMapping(value = "/verify/reg/{verifykey}", method = RequestMethod.GET)
	public String registrationSuccess(@PathVariable("verifykey") String verifykey) { // kaydı yapılan kullanıcıya
																						// gönderilen email doğrulama
																						// linki oluşturma
		User user = userRepository.findByRegisterVerifyKey(verifykey);
		if (userService.findByRegisterVerifyKey(verifykey)) {
			user.setRegisterEmailActive(true);
			user.setRegisterVerifyKey(null);
			user.setUserActive(true);
			userRepository.save(user);

		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/smsSendForm", method = RequestMethod.POST) // kaydı yapılan kullanıcıya gönderilen sms
																			// doğrulama kodu oluşturma
	public String smsVerifyPage(@ModelAttribute("code") String code) {
		User user = userRepository.findByRegisterSmsCode(code);

		if (user.getRegisterSmsCode().equals(code)) { // gönderilen kodu equals ile d.base deki kod ile karşılaştırır
			user.setRegisterSmsActive(true);
			user.setRegisterSmsCode(null);
			user.setUserActive(true);
			userRepository.save(user);

		}

		return "redirect:/login";

	}

	@RequestMapping(value = "/smsSendForm", method = RequestMethod.GET) // sms doğrulama kodu oluşturmak için
																		// kullanılması gereken get metodu
	public String smsVerify() {

		return "smsSendForm";

	}

}