package com.khun.controller;

import com.khun.auth.Authenticate;
import com.khun.dto.UserDto;
import com.khun.dto.UserReqDto;
import com.khun.entity.User;
import com.khun.exception.AccountDisableException;
import com.khun.exception.AccountNotFoundException;
import com.khun.exception.PasswordNotMatchException;
import com.khun.services.UserService;
import com.khun.utils.*;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

    @GetMapping("/")
    public String showLoginForm(HttpSession session) {

        String adminEmail = (String) session.getAttribute("user-email");
        if (adminEmail == null) {
            return "redirect:/login";
        }
        return "redirect:/welcome";
    }

	@GetMapping("/login")
	public String loginForm(HttpSession session) {

		System.out.println("in get login()");
		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail!=null) {
			return "redirect:/welcome";
		}
		return "login";
	}


	private static void setSessionData(HttpSession session, UserDto user) {
		session.setAttribute("isAdmin", user.getRole() == Role.ADMIN.getValue());
		session.setAttribute("userId", user.getId());
		session.setAttribute("username", user.getName());
		session.setAttribute("user-email", user.getEmail());
		session.setAttribute("isFirstLogin", user.isFirstLogin());
		session.setAttribute("userImg", user.getImgUrl());
	}

	@PostMapping("/login")
	public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("pass") String password,
            HttpSession session, Model model) {

		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail!=null) {
			return "redirect:/welcome";
		}
		UserDto user = null;
		String accountDisableException = null;
		String accountNotFoundException = null;
		String passwordNotMatchException = null;

		if(email!=null && password!=null) {
			try {
				user = new Authenticate(userService).checkAndGetUser(email, password);
			} catch (AccountNotFoundException e) {
				accountNotFoundException = "Account Not Found!";
			} catch (PasswordNotMatchException e) {
				passwordNotMatchException = "Invalid Password";
			} catch (AccountDisableException e) {
				accountDisableException = "This account is DISABLE.";
			}
		}

		if (user != null) {
			setSessionData(session, user);
			return "redirect:/welcome";
		} else {
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			model.addAttribute("accountNotFoundException", accountNotFoundException);
			model.addAttribute("passwordNotMatchException", passwordNotMatchException);
			model.addAttribute("accountDisableException", accountDisableException);
			return "login";
		}
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("welcome")
	public String home(HttpSession session) {
		String adminEmail = (String) session.getAttribute("user-email");
		if (adminEmail == null) {
			return "redirect:/login";
		}

		return "redirect:/students";
	}

	@PostMapping("change-default-password")
	public String changeDefaultPassword(
			@RequestParam(name = "pass") String password,
			HttpSession session) {
		String adminEmail = (String) session.getAttribute("user-email");
		if (adminEmail == null) {
			return "redirect:/login";
		}

		Optional<User> user = userService.getOneById((String) session.getAttribute("userId"));
		UserReqDto userReqDto;
		if (user.isPresent()) {
			User curUser = user.get();
			userReqDto = new UserReqDto(curUser.getId(), curUser.getName(), curUser.getEmail(),
					PasswordHasher.hashPassword(password), curUser.getRole(), curUser.getStatus(), false, curUser.getImgUrl());
			try {
				userService.update(userReqDto, false);
			} catch (SQLException e) {
				System.out.println("error at update");
			}
		}

		Optional<User> user1 = userService.getOneById((String) session.getAttribute("userId"));
		setSessionData(session, user1.get().mapToUserDto());

		return "redirect:/students";
	}


	@GetMapping("/users")
	public String getUserList(@RequestParam(name = "userId", required = false) String userId,
							  @RequestParam(name = "username", required = false) String username,
							  @RequestParam(name = "userStatus", required = false) String status, HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if (adminEmail == null) {
			return "redirect:/login";
		}

		boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

		// for disable user account
		if (status != null && userId != null) {
			int userStatus = 0;
			System.out.println("status->" + status);
			if ("0".equals(status)) {
				userStatus = 1;
			}
				userService.disableOrActiveUser(userId, userStatus);

		}

        List<UserDto> users = userService.getAllUsers();

		// for search features
		if (userId != null && username != null) {
			users = users.stream()
					.filter(user -> userId.equalsIgnoreCase(user.getId()) && username.equalsIgnoreCase(user.getName()))
					.toList();
			session.setAttribute("searchUserId", userId);
			session.setAttribute("searchUserName", username);
		}

		// for reset search
		if (userId == null && username == null) {
			session.setAttribute("searchUserId", "");
			session.setAttribute("searchUserName", "");
		}

		// set user list to session
		session.setAttribute("AllUserLists", users);
		return "user-list";
	}

	@GetMapping("users/register")
	public String addUser(HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail==null) {
			return "redirect:/login";
		}

		boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

		// generate user code and set to session
        String code = new CodeGenerator(userService).generate(Type.USER);
		session.setAttribute("userId", code);

		// create status for toast
		session.setAttribute("isCreated", false);
		session.setAttribute("notCreated", false);
		return "user-register";
	}

	@PostMapping("users/register")
	public String addUserProcess(
			@RequestParam(name = "email") String email,
			@RequestParam(name = "name") String username,
			@RequestParam(name = "pass") String password,
			@RequestParam(name = "adminPass") String adminPass,
			@RequestParam(name = "imageFile") CommonsMultipartFile imageFile,
			HttpServletRequest request, HttpSession session) {

		String error = "";
		boolean isCreated = false;
		boolean notCreated = false;

		String adminEmail = (String) session.getAttribute("user-email");
		String userId = (String) session.getAttribute("userId");

		if (adminEmail == null) {
			return "redirect:/login";
        }

        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

		boolean isAuth = false;
		// check Authentication
			try {
                isAuth = new Authenticate(userService).checkAndGetUser(adminEmail, adminPass) != null;
			} catch (AccountNotFoundException e) {
				notCreated = true;
				error = "Account Not Found!";
			} catch (PasswordNotMatchException e) {
				notCreated = true;
				error = "Invalid Password";
			} catch (AccountDisableException e) {
				notCreated = true;
				error = "This account is DISABLE.";
			}

        // add user data if isAuth
		if (isAuth && username != null && email != null && password != null) {
			String imgUrl = "";
			try {
				imgUrl = new FileCreator(imageFile, request.getServletContext()).create();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			UserReqDto userReqDto = new UserReqDto();
			userReqDto.setId(userId);
			userReqDto.setName(username);
			userReqDto.setEmail(email);
			userReqDto.setPassword(PasswordHasher.hashPassword(password));
			userReqDto.setRole(Role.USER.getValue());
			userReqDto.setStatus(Status.ACTIVE.getValue());
			userReqDto.setFirstLogin(true);
			userReqDto.setImgUrl(imgUrl);

			try {
				userService.save(userReqDto);
				isCreated = true;
			} catch (SQLIntegrityConstraintViolationException e) {
				notCreated = true;
				error = "Email is already used";
			} catch (MysqlDataTruncation e) {
				notCreated = true;
				error = "Students have limit.";
			} catch (SQLException e) {
				notCreated = true;
				error = "Unknown Error";
			}
		}

		// generate user code and set to session
        String code = new CodeGenerator(userService).generate(Type.USER);
		session.setAttribute("userId", code);

		// create status for toast
		session.setAttribute("isCreated", isCreated);
		session.setAttribute("notCreated", notCreated);
		session.setAttribute("error", error);
		return "user-register";
	}

	@GetMapping("/users/update")
	public String editUser(@RequestParam(name = "userId") String userId, HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if(adminEmail==null) {
			return "redirect:/login";
		}

        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return "redirect:/welcome";
        }

        if (userId != null) {

            Optional<User> user = userService.getOneById(userId);
            user.ifPresent(value -> session.setAttribute("updateUser", value));

            session.setAttribute("isUpdated", false);
            session.setAttribute("notUpdated", false);

        }
		return "user-update";
	}

	@PostMapping("/users/update")
	public String updateUser(
			@RequestParam(name = "email") String email,
			@RequestParam(name = "name") String username,
			@RequestParam(name = "pass") String password,
			@RequestParam(name = "role") String role,
			@RequestParam(name = "status") String status,
			@RequestParam(name = "adminPass") String adminPass, HttpSession session) {

		String error = "";
		boolean isUpdated = false;
        boolean notUpdated = false;
        boolean isOldPass;
        User currentUser = (User) session.getAttribute("updateUser");
		String adminEmail = (String) session.getAttribute("user-email");

        if(adminEmail==null) {
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

            UserReqDto user = new UserReqDto();
            user.setId(currentUser.getId());
            user.setEmail(email);
            user.setName(username);
            user.setPassword(password);
            user.setRole(Integer.parseInt(role));
            user.setStatus(Integer.parseInt(status));

            String oldPass = currentUser.getPassword();
            isOldPass = oldPass.equals(user.getPassword());

            try {
				isUpdated = userService.update(user, isOldPass);
            } catch (SQLException e) {
                System.out.println("error at update");
                notUpdated = true;
                error = "Something went wrong to update";
            }

        }
		session.setAttribute("error", error);
		session.setAttribute("isUpdated", isUpdated);
		session.setAttribute("notUpdated", notUpdated);

		if (isUpdated) {
			System.out.println("isUpdate is true");
			return "redirect:/users";
		}
		return "user-update";
	}

	@GetMapping("/user/profile")
	public String showUserProfile(HttpSession session) {

		String adminEmail = (String) session.getAttribute("user-email");
		if (adminEmail == null) {
			return "redirect:/login";
		}
		return "user-profile";
	}
}
