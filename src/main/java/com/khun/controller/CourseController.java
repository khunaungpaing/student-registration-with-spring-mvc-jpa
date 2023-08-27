package com.khun.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khun.auth.Authenticate;
import com.khun.exception.AccountDisableException;
import com.khun.exception.AccountNotFoundException;
import com.khun.exception.PasswordNotMatchException;
import com.khun.model.dao.impl.CourseDaoImpl;
import com.khun.model.dao.impl.UserDaoImp;
import com.khun.model.dto.CourseDto;
import com.khun.service.CourseService;
import com.khun.service.UserService;
import com.khun.utils.CodeGenerator;
import com.khun.utils.Type;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@Controller
@RequestMapping
public class CourseController {

	private UserService userService;
	private CourseService courseService;

	@Autowired
	public CourseController(CourseService courseService, UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
	}

	@GetMapping("course/add")
	public String addCourse(HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail==null) {
			return "redirect:/login";
		}
		
		boolean isAdmin = (boolean) session.getAttribute("isAdmin");
		if(adminEmail!=null && !isAdmin) {
			return "redirect:/welcome";
		}
		
		// generate user code and set to session
		String code = new CodeGenerator(new CourseDaoImpl()).generate(Type.COURSE);
		session.setAttribute("courseId", code);

		// create status for toast
		session.setAttribute("isCreatedCourse", false);
		session.setAttribute("notCreatedCourse", false);
		return "course-register";
	}

	@PostMapping("course/add")
	public String addCourse(
			@RequestParam(name = "courseName") String courseName,
			@RequestParam(name = "adminPass") String adminPass,
			HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail==null) {
			return "redirect:/login";
		}
		
		boolean isAdmin = (boolean) session.getAttribute("isAdmin");
		if(adminEmail!=null && !isAdmin) {
			return "redirect:/welcome";
		}

		String error = "";
		boolean isCreated = false;
		boolean notCreated = false;

		boolean isAuth = false;
		// check Authentication
		if (adminPass != null && adminEmail != null) {

			System.out.println("admin data not null --------");
			try {
				isAuth = new Authenticate(userService).check(adminEmail, adminPass);
			} catch (AccountNotFoundException e) {
				notCreated = true;
				error = "Account Not Found!";
			} catch (PasswordNotMatchException e) {
				notCreated = true;
				error = "Invalid Password to verify ADMIN";
			} catch (AccountDisableException e) {
				notCreated = true;
				error = "This account is DISABLE.";
			}
		}
		System.out.println("isAuth ->" + isAuth);
		// add user data if isAuth
		if (isAuth && courseName != null) {
			CourseDto course = new CourseDto();
			course.setName(courseName);

			try {
				courseService.save(course);
				isCreated = true;
			} catch (SQLIntegrityConstraintViolationException e) {
				notCreated = true;
				error = "This Course is already used";
			} catch (MysqlDataTruncation e) {
				notCreated = true;
				error = "Courses have limit.";
			} catch (SQLException e) {
				notCreated = true;
				error = "Unknown Error";
			}
		}

		// generate user code and set to session
		String code = new CodeGenerator(new CourseDaoImpl()).generate(Type.COURSE);
		session.setAttribute("courseId", code);

		// create status for toast
		session.setAttribute("isCreatedCourse", isCreated);
		session.setAttribute("notCreatedCourse", notCreated);
		session.setAttribute("error", error);
		return "course-register";
	}

}
