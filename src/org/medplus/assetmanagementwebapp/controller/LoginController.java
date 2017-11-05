package org.medplus.assetmanagementwebapp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.medplus.assetmanagementcore.exceptions.AssetException;
import org.medplus.assetmanagementcore.exceptions.AuthenticationException;
import org.medplus.assetmanagementcore.exceptions.EmployeeException;
import org.medplus.assetmanagementcore.model.Employee;
import org.medplus.assetmanagementcore.service.AssetService;
import org.medplus.assetmanagementcore.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AssetService assetService;


	Employee employee;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Login");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String username,
			@RequestParam("password") String password) throws IOException {
		ModelAndView mav = new ModelAndView("Home");
		boolean login = false;
				try {
					
					login = employeeService.authenticateEmployee(username, password);
					
				} catch (DataAccessException | EmployeeException
						| AuthenticationException e) {
					mav.addObject("message", e.getMessage());
					mav.setViewName("Login");
				}
		if (!login) {
			mav.setViewName("Login");
			mav.addObject("message", login);
			return mav;
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			mav.addObject("username", username);
			String role=employeeService.getDesignation(username);
			session.setAttribute("role", role);
			if (role!=null&&role.equals("admin")) {
				mav.addObject("isAdmin", "true");
			}
			else {
				mav.addObject("isAdmin", "false");
				 }
		
		return mav;
	}
	}

	
	@RequestMapping(value = "/invalidate", method = RequestMethod.GET)
	public String invalidate(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(false);
		session.invalidate();
		mav.addObject("status", null);
		mav.setViewName("Login");
		return "redirect:login";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ChangePassword");
		return mav;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ModelAndView ChangePassword(
			@RequestParam("employeeID") String username,
			@RequestParam("oldPassword") String oldpassword,
			@RequestParam("newPassword") String newpassword
			) {
		ModelAndView mav = new ModelAndView();

		String msg = "";
		try {
			try {
				msg = employeeService.changePassword(username, oldpassword,
						newpassword, username, new Date());
			} catch (AuthenticationException e) {
				msg= e.getMessage();
			}
		} catch (EmployeeException e) {
			msg= e.getMessage();
		}
		if(msg.equals("SUCCESS"))
		{
		mav.addObject("message", msg);
		mav.setViewName("Login");
		return mav;
		}
		else
		{
			mav.addObject("message", msg);
			mav.setViewName("EmpHome");
			return mav;	
		}
		
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public ModelAndView resetPassword() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ResetPassword");
		return mav;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView ResetPassword(
			@RequestParam("employeeID") String username,
			@RequestParam("newpassword") String newpassword,
			@RequestParam("resetby") String resetby) {
		ModelAndView mav = new ModelAndView();

		String msg = "";
		try {
			try {
				msg = employeeService.resetPassword(username, resetby,
						newpassword, new Date());
			} catch (EmployeeException e) {
				msg += e.getMessage();
			}
		} catch (AuthenticationException e) {
			msg += e.getMessage();
		}
		mav.addObject("message", msg);
		mav.setViewName("AdminHome");
		return mav;
	}

	@RequestMapping(value = "/adminhome", method = RequestMethod.GET)
	public ModelAndView emphome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("AdminHome");
		return mav;

	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ModelAndView EmployeeHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
//		mav.setViewName("EmployeeHome");
		 try {
			List types =assetService.getAllAssetTypes();
			mav.addObject("types",types);
		} catch (AssetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.setViewName("EmpHome");

		return mav;
	}

	@RequestMapping(value = "/EDPHome", method = RequestMethod.GET)
	public ModelAndView EDPHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("EDPHome");
		return mav;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView Home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Home");
		return mav;
	}

}