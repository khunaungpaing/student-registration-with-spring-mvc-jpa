package com.khun.auth;


import com.khun.entity.User;
import com.khun.exception.AccountDisableException;
import com.khun.exception.AccountNotFoundException;
import com.khun.exception.PasswordNotMatchException;
import com.khun.model.dto.UserDto;
import com.khun.service.UserService;
import com.khun.utils.PasswordHasher;
import com.khun.utils.Status;

public class Authenticate {

	private UserService service;

	public Authenticate(UserService service) {
		this.service = service;
	}

	public UserDto checkAndGetUser(String email, String password) {
	    User user = service.findByEmail(email);

	    if (user == null) {
	        throw new AccountNotFoundException("Account not Found for " + email);
	    }

	    	    
	    // Verify the password
	    if (!PasswordHasher.checkPassword(password, user.getPassword())) {
	        throw new PasswordNotMatchException("Password is not match.");
	    }

		// throw error for account disable
		if (user.getStatus() == Status.DISABLE.getValue()) {
			throw new AccountDisableException("Your account is disable");
		}

		return user.mapToUserDto();
	}

	public boolean check(String email, String password) {

		User user = service.findByEmail(email);

		if (user == null) {
			throw new AccountNotFoundException("Account not Found for " + email);
		}

		// throw error for pass not match
		if (!PasswordHasher.checkPassword(password, user.getPassword())) {
			throw new PasswordNotMatchException("Password is not match.");
		}

		// throw error for account disable
		if (user.getStatus() == Status.DISABLE.getValue()) {
			throw new AccountDisableException("Your account is disable");
		}

		return true;
	}

}
