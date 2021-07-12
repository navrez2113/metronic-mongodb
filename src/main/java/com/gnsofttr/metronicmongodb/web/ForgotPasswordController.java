package com.gnsofttr.metronicmongodb.web;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gnsofttr.metronicmongodb.dao.UserRepository;
import com.gnsofttr.metronicmongodb.model.User;
import com.gnsofttr.metronicmongodb.service.EmailService;
import com.gnsofttr.metronicmongodb.service.UserService;


@Controller
public class ForgotPasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Display forgotPassword page
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgotPassword");
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("username") String username,
			HttpServletRequest request) {

		Optional<User> optional = userRepository.getByUsername(username);

		if (!optional.isPresent()) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address."); // kaydı olmayan kullanıcı için hata mesajı
		} else {

			User user = optional.get();
			user.setPasswordResetToken(UUID.randomUUID().toString()); // maile gönderilecek link için rastgele kod üretir

			userService.saveUser(user);

			String appUrl = request.getScheme() +  "://localhost:8080" ; // şifre sıfırlamak için maile gönderilen istek

			SimpleMailMessage passwordResetUsername = new SimpleMailMessage();
			passwordResetUsername.setFrom("gnsofttr@gmail.com");
			passwordResetUsername.setTo(user.getUsername());
			passwordResetUsername.setSubject("Password Reset Request");
			passwordResetUsername.setText("To reset your password, click the link below:\n" + appUrl + "/resetPassword/" //şifre sıfırlamak için maile gönderilen istek
					+ user.getPasswordResetToken());

			emailService.sendEmail(passwordResetUsername);

			modelAndView.addObject("successMessage", "A password reset link has been sent to " + username);
		}

		modelAndView.setViewName("forgotPassword");
		return modelAndView;

	}

	// Display form to reset password
	@RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.GET) // şifre değiştirme formu
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @PathVariable("token") String token) {

		Optional<User> user = userService.findUserByPasswordResetToken(token);

		if (user.isPresent()) {
			modelAndView.addObject("resetToken", token);
		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST) // şifre değiştirme post formu
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams,
			RedirectAttributes redir) {

		Optional<User> user = userService.findUserByPasswordResetToken(requestParams.get("token"));

		if (user.isPresent()) {

			User resetUser = user.get();

			resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

			resetUser.setPasswordResetToken(null);

			userRepository.save(resetUser);

			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:/login");
			return modelAndView;

		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");
		}

		return new ModelAndView("redirect:/login");
	}

}
