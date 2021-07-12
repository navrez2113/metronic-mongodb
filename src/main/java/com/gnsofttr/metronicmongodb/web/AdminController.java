package com.gnsofttr.metronicmongodb.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnsofttr.metronicmongodb.dao.UserRepository;
import com.gnsofttr.metronicmongodb.model.User;
import com.gnsofttr.metronicmongodb.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private List<User> list;

	@GetMapping
	public String viewHomePage(Model model) {
		model.addAttribute("listUsers", userService.getAllUsers());

		return "admin";
	}

	@GetMapping("/showNewUserForm")
	public String newuser(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "newuser";
	}

	@PostMapping("/showNewUserForm")
	public String showNewUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "newuser";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user, Model model) {

		model.addAttribute("user", user);
		userService.save(user);
		return "smsSendForm";
	}

	@GetMapping("/saveUser")
	public String saveUsers(@ModelAttribute("user") User user) {

		userService.save(user);
		return "smsSendForm";

	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("user", user);
		return "updateuser";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") String id, @Valid User user, Model model, BindingResult result) {
		User user2 = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		if (result.hasErrors()) {
			user.setId(id);
			return "updateuser";
		}
		user2.setFirstName(user.getFirstName());
		user2.setLastName(user.getLastName());
		user2.setUsername(user.getUsername());

		userService.save(user2);

		return "redirect:/admin";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value = "id") String id) {

		this.userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@GetMapping("/activeUser/{id}") // kullanıcı aktifleştirme
	public String activeUser(@PathVariable String id) {
		User user = userService.getUserById(id);
		if (user.isUserActive() == false) {
			user.setUserActive(true);
			userRepository.save(user);
		}
		return "activeUser";
	}

	@GetMapping("/pasiveUser/{id}") // kullanıcı devre dışı bırakma
	public String pasiveUser(@PathVariable String id) {
		User user = userService.getUserById(id);
		if (user.isUserActive() == true) {
			user.setUserActive(false);
			userRepository.save(user);
		}
		return "pasiveUser";
	}

	@GetMapping("/activeAll") // girişten sonra sms aktifleştirme (bütün kullanıcılar için )
	public String activeAll() {

		list = userService.getAllUsers();

		for (User user : list) {

			user.setAdminOtpActive(true);
			userRepository.save(user);

		}
		return "activeAll";
	}

	@GetMapping("/pasiveAll") // girişten sonra sms devre dışı bırakma (bütün kullanıcılar için )
	public String pasiveAll() {
		list = userService.getAllUsers();

		for (User user : list) {

			user.setAdminOtpActive(false);
			userRepository.save(user);

		}
		return "pasiveAll";
	}

}
