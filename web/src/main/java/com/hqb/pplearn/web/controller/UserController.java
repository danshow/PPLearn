package com.hqb.pplearn.web.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hqb.pplearn.biz.form.UserForm;
import com.hqb.pplearn.biz.service.UserService;
import com.hqb.pplearn.biz.validate.UserValidator;
import com.hqb.pplearn.web.helper.WebHelper;

@Controller
// @RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${imageAppRootPath}")
	private String imageAppRootPath;

	private String headImagesPath = "upload" + "/" + "user";

	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.setValidator(new UserValidator());
	}

	@RequestMapping(value = "/registerPage.jhtm", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String registerPage() {
		logger.info("request registerPage.jhtm");
		return "register";
	}

	@RequestMapping(value = "/register.jhtm", method = RequestMethod.POST)
	public String register(@Validated UserForm form, Model model, HttpServletRequest request, BindingResult result) throws IllegalAccessException, InvocationTargetException {
		logger.info("request register.jhtm");
		if (result.hasErrors()) {
			String errMsg = WebHelper.buildErrorMsg(result);
			throw new RuntimeException(errMsg);
		} else {
			userService.createUser(form);
			return "redirect:index.htm";
		}
	}
}