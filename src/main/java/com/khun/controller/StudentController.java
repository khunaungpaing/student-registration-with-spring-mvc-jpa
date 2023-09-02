package com.khun.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.khun.services.CourseService;
import com.khun.services.StudentService;
import com.khun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.khun.auth.Authenticate;
import com.khun.exception.AccountDisableException;
import com.khun.exception.AccountNotFoundException;
import com.khun.exception.PasswordNotMatchException;
import com.khun.dto.CourseDto;
import com.khun.dto.StudentDto;
import com.khun.utils.CodeGenerator;
import com.khun.utils.FileCreator;
import com.khun.utils.Type;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, UserService userService, CourseService courseService) {

        this.studentService = studentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("students/add")
    public String addStudent(HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        // get all courses
        List<CourseDto> courseList = courseService.getAllCourses().stream().filter(CourseDto::isActive).toList();
        session.setAttribute("courseList", courseList);

        // generate user code and set to session
        String code = new CodeGenerator(studentService).generate(Type.STUDENT);
        session.setAttribute("studentId", code);

        // create status for toast
        session.setAttribute("isCreatedStudent", false);
        session.setAttribute("notCreatedStudent", false);
        return "student-register";
    }

    @PostMapping("students/add")
    public String registerStudent(@RequestParam("name") String name, @RequestParam("dob") String dobStr,
                                  @RequestParam("gender") int gender, @RequestParam("phone") String phone,
                                  @RequestParam("education") String education, @RequestParam("courses") String[] courseIds,
                                  @RequestParam("imageFile") CommonsMultipartFile imageFile, @RequestParam("adminPass") String adminPass,
                                  HttpServletRequest request, HttpSession session) {

        String studentId = (String) session.getAttribute("studentId");
        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        String error = null;
        boolean isCreated = false;
        boolean notCreated = false;

        // upload image
        boolean createImageSuccess;
        String img_url = null;

        try {
            img_url = new FileCreator(imageFile, request.getServletContext()).create();
            createImageSuccess = true;
        } catch (IOException e) {
            createImageSuccess = false;
            error = "can't not upload image";
        }

        // check Authentication
        boolean isAuth = false;
        if (adminPass != null) {

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

        // Parse the date string
        boolean checkDate = true;
        java.util.Date utilDate = null;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            utilDate = inputFormat.parse(dobStr);
        } catch (ParseException e) {
            checkDate = false;
            session.setAttribute("error", "Date can't convert");
        }

        java.sql.Date dob = null;
        if (utilDate != null) {
            dob = new java.sql.Date(utilDate.getTime());
        }

        if (img_url != null && createImageSuccess && isAuth && checkDate) {
            StudentDto student = new StudentDto(studentId, name, dob, gender,
                    phone, education, img_url, null,
                    (String) session.getAttribute("username"));
            try {
                isCreated = studentService.addStudentWithEnrollments(student, List.of(courseIds));
            } catch (SQLIntegrityConstraintViolationException e) {
                notCreated = true;
                session.setAttribute("error", "Email is already used");
            } catch (MysqlDataTruncation e) {
                notCreated = true;
                session.setAttribute("error", "Student have limit.");
                e.printStackTrace();
            } catch (SQLException e) {
                notCreated = true;
                session.setAttribute("error", "Unknown Error");
                e.printStackTrace();
            }

        }

        // generate user code and set to session
        String code = new CodeGenerator(studentService).generate(Type.STUDENT);
        session.setAttribute("studentId", code);

        // create status for toast
        session.setAttribute("isCreatedStudent", isCreated);
        session.setAttribute("notCreatedStudent", notCreated);
        session.setAttribute("error", error);
        return "student-register";
    }

    @GetMapping("students")
    public String studentList(@RequestParam(name = "studentId", required = false) String studentId,
                              @RequestParam(name = "studentName", required = false) String studentName, HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        // get all students
        List<StudentDto> stuList = studentService.fetchAllStudents();

        // for search features
        if (studentId != null && studentName != null) {
            stuList = stuList.stream().filter(
                            user -> studentId.equalsIgnoreCase(user.getId()) && studentName.equalsIgnoreCase(user.getName()))
                    .toList();
            session.setAttribute("searchStudentId", studentId);
            session.setAttribute("searchStudentname", studentName);
        }

        // for reset search
        if (studentId == null && studentName == null) {
            session.setAttribute("searchUserId", "");
            session.setAttribute("searchUserName", "");
        }

        session.setAttribute("AllStudentLists", stuList);
        return "student-list";
    }

    @GetMapping("students/{id}")
    public String showDetails(@PathVariable("id") String studentId, HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        // get student info
        List<StudentDto> stuList = studentService.fetchAllStudents();
        StudentDto student = stuList.stream().filter(s -> studentId.equalsIgnoreCase(s.getId())).findFirst().get();
        List<CourseDto> courses = student.getEnrollment().stream().map(course -> new CourseDto(course.getId(), course.getName(), null, false, null)).toList();

        session.setAttribute("student", student);
        session.setAttribute("stuCourses", courses);

        return "student-details";
    }

    @GetMapping("students/{id}/update")
    public String directUpdateStudent(@PathVariable("id") String studentId, HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }

        // get all courses
        List<CourseDto> courseList = courseService.getAllCourses();
        session.setAttribute("courseList", courseList);

        session.setAttribute("notUpdatedStudent", false);

        return "student-update";
    }

	@PostMapping("students/{id}/update")
	public String updateStudent(@PathVariable("id") String studentId, @RequestParam("name") String name,
			@RequestParam("dob") String dobStr, @RequestParam("gender") String gender,
			@RequestParam("phone") String phone, @RequestParam("education") String education,
			@RequestParam("courses") String[] courses,
			@RequestParam(name = "imageFile", required = false) CommonsMultipartFile imageFile,
			@RequestParam("adminPass") String adminPass, HttpServletRequest request, HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail==null) {
			return "redirect:/login";
		}

		String error = null;
		boolean isUpdated = false;
		boolean notUpdated = false;
		StudentDto currentStudent = (StudentDto) session.getAttribute("student");

		// upload image
		boolean createImageSuccess = true;
		String img_url = null;
		if (imageFile != null && imageFile.getSize() > 0) {
			try {
                img_url = new FileCreator(imageFile, request.getServletContext()).create();
                createImageSuccess = true;
			} catch (IOException e) {
				createImageSuccess = false;
				error = "can't not upload image";
			}
		}

		if (img_url == null) {
			img_url = currentStudent.getImg_url();
		}

		// check Authentication
		boolean isAuth = false;
        if (adminPass != null) {

            try {
                isAuth = new Authenticate(userService).checkAndGetUser(adminEmail, adminPass) != null;
            } catch (AccountNotFoundException e) {
                notUpdated = true;
                error = "Account Not Found!";
            } catch (PasswordNotMatchException e) {
                notUpdated = true;
                error = "Invalid Password to verify ADMIN";
            } catch (AccountDisableException e) {
				notUpdated = true;
				error = "This account is DISABLE.";
			}
		}

		// Parse the date string
		boolean checkDate = true;
		java.util.Date utilDate = null;
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = inputFormat.parse(dobStr);
		} catch (ParseException e) {
			checkDate = false;
			error = "Date can't convert";
		}

		java.sql.Date dob = null;
		if (utilDate != null) {
			dob = new java.sql.Date(utilDate.getTime());
		}

		// if all ok, let's cook
		if (img_url != null && createImageSuccess && isAuth && checkDate) {

			// create student data
			StudentDto student = new StudentDto();
			student.setId(studentId);
			student.setName(name);
			student.setDob(dob);
			student.setGender(Integer.parseInt(gender));
			student.setPhone(phone);
			student.setEducation(education);
			student.setImg_url(img_url);

			try {
                isUpdated = studentService.update(student, List.of(courses));
			} catch (MysqlDataTruncation e) {
                notUpdated = true;
                error = "Error: can't update...";
			} catch (SQLException e) {
				notUpdated = true;
				error = "Unknown Error";
			}


		}

		System.out.println("error-> "+error);
		System.out.println("not update-> "+ notUpdated);
		// create status for toast
		session.setAttribute("notUpdatedStudent", notUpdated);
		session.setAttribute("error", error);
		return !notUpdated ? "redirect:/students/" + studentId : "student-update";
	}

}
