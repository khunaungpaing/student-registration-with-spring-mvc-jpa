package com.khun.utils;

import java.io.Serializable;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher implements Serializable {

	private static final long serialVersionUID = -2235525666906205349L;

	public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(12);
        
        if (salt == null) {
            throw new RuntimeException("Failed to generate salt for password hashing.");
        }
        
        // Hash the password
        String hashedPassword = BCrypt.hashpw(plainTextPassword, salt);

        return hashedPassword;
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        System.out.println("in check Password");
        return BCrypt.checkpw(plainTextPassword.trim(), hashedPassword);
    }


    public static void main(String[] args) {
        System.out.println(hashPassword("khunkhun"));
    }

}