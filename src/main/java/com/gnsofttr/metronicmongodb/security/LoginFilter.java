package com.gnsofttr.metronicmongodb.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gnsofttr.metronicmongodb.model.User;



@Component
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getRequestURI().contains("login")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("registration")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("login?logout")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("logout")) {
			chain.doFilter(request, response);
			return;
		}
		if (req.getRequestURI().contains("dologout")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("redirect:")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("loginSms")) {

			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("assest")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("css")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("js")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("media")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("images")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("plugins")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("forgotPassword")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("smsSendForm")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("resetPassword")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("sms")) {
			chain.doFilter(request, response);
			return;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.isAdminOtpActive() == true && user.isLoginSmsActive() == false) {

			res.sendRedirect("/sms");

			return;

		} else {

			chain.doFilter(request, response);
			res.sendRedirect("/index");

		}

		if (req.getRequestURI().contains("index")) {
			chain.doFilter(request, response);
			return;
		}

		
		if (req.getRequestURI().contains("admin")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("pasiveAll")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("activeAll")) {
			chain.doFilter(request, response);
			return;
		}

		if (req.getRequestURI().contains("edit/{id}")) {
			chain.doFilter(request, response);
			return;
		}
		if (req.getRequestURI().contains("/update/{id}")) {
			chain.doFilter(request, response);
			return;
		}
		if (req.getRequestURI().contains("save")) {
			chain.doFilter(request, response);
			return;
		}
	
		

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}