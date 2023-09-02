package com.khun.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.khun.dto.UserDto;
import com.khun.dto.UserReqDto;
import com.khun.entity.Course;
import com.khun.entity.User;
import com.khun.services.CourseService;
import com.khun.services.UserService;
import com.khun.utils.Status;
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
import com.khun.dto.CourseDto;
import com.khun.utils.CodeGenerator;
import com.khun.utils.Type;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@Controller
@RequestMapping
public class CourseController {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("courses")
    public String showCourses(@RequestParam(name = "courseId", required = false) String courseId,
                              @RequestParam(name = "courseName", required = false) String courseName,
                              @RequestParam(name = "active", required = false) boolean active, HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

        // for disable
        if (courseId != null) {
            courseService.toggleActive(courseId, active);
        }


        // get all courses
        List<CourseDto> courseList = courseService.getAllCourses();

        // for search features
        if (courseId != null && courseName != null) {
            courseList = courseList.stream()
                    .filter(course -> courseId.equalsIgnoreCase(course.getId()) && courseName.equalsIgnoreCase(course.getName()))
                    .toList();
            session.setAttribute("searchCourseId", courseId);
            session.setAttribute("searchCourseName", courseName);
        }

        // for reset search
        if (courseId == null && courseName == null) {
            session.setAttribute("searchCourseId", "");
            session.setAttribute("searchCourseName", "");
        }

        session.setAttribute("courseList", courseList);
        return "course-list";
    }

    @GetMapping("courses/add")
    public String addCourse(HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

		// generate user code and set to session
		String code = new CodeGenerator(courseService).generate(Type.COURSE);
		session.setAttribute("courseId", code);

		// create status for toast
		session.setAttribute("isCreatedCourse", false);
		session.setAttribute("notCreatedCourse", false);
		return "course-register";
	}

    @PostMapping("courses/add")
	public String addCourse(
			@RequestParam(name = "courseName") String courseName,
			@RequestParam(name = "adminPass") String adminPass,
			HttpSession session) {

		String courseId = (String) session.getAttribute("courseId");
		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail==null) {
			return "redirect:/login";
		}

		boolean isAdmin = (boolean) session.getAttribute("isAdmin");
		if (!isAdmin) {
			return "redirect:/welcome";
		}

		String error = "";
		boolean isCreated = false;
		boolean notCreated = false;

		boolean isAuth = false;
		// check Authentication
		if (adminPass != null) {

			System.out.println("admin data not null --------");
			try {
				isAuth = new Authenticate(userService).checkAndGetUser(adminEmail, adminPass) != null;
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
			course.setId(courseId);
            course.setName(courseName);
            course.setActive(true);

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
        String code = new CodeGenerator(courseService).generate(Type.COURSE);
        session.setAttribute("courseId", code);

        // create status for toast
        session.setAttribute("isCreatedCourse", isCreated);
        session.setAttribute("notCreatedCourse", notCreated);
        session.setAttribute("error", error);
        return "course-register";
    }

    @GetMapping("courses/update")
    public String editUser(@RequestParam(name = "courseId") String courseId, HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

        if (courseId != null) {

            Optional<Course> course = courseService.getOneById(courseId);
            course.ifPresent(value -> session.setAttribute("updateCourse", value));

            session.setAttribute("isCourseUpdated", false);
            session.setAttribute("notCourseUpdated", false);

        }
        return "course-update";
    }

    @PostMapping("/courses/update")
    public String updateUser(
            @RequestParam(name = "courseName") String courseName,
            @RequestParam(name = "adminPass") String adminPass, HttpSession session) {

        String error = "";
        boolean notUpdated = false;
        Course updateCourse = (Course) session.getAttribute("updateCourse");
        String adminEmail = (String) session.getAttribute("user-email");

        if (adminEmail == null) {
            return "redirect:/login";
        }

        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

        boolean isAuth = false;
        // check Authentication
        if (adminPass != null) {

            try {
                isAuth = new Authenticate(userService).checkAndGetUser(adminEmail, adminPass) != null;
            } catch (AccountNotFoundException e) {
                notUpdated = true;
                error = "Account Not Found!";
            } catch (PasswordNotMatchException e) {
                notUpdated = true;
                error = "Invalid Password to verify admin";
            } catch (AccountDisableException e) {
                notUpdated = true;
                error = "This account is DISABLE.";
            }
        }

        if (isAuth) {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(updateCourse.getId());
            courseDto.setName(courseName);
            courseDto.setCreatedAt(updateCourse.getCreatedAt());
            courseDto.setActive(updateCourse.isActive());

            try {
                courseService.update(courseDto);
            } catch (Exception e) {
                notUpdated = true;
                error = "Something went wrong to update";
            }

        }
        session.setAttribute("error", error);
        session.setAttribute("notUpdated", notUpdated);

        if (!notUpdated) {
            return "redirect:/courses";
        }
        return "course-update";
    }
}
