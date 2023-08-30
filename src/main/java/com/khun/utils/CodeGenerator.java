package com.khun.utils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


public class CodeGenerator {

    private final Predictable service;

    public CodeGenerator(Predictable service) {
        this.service = service;
    }

    public String generate(Type type) {
        return switch (type) {
            case STUDENT -> _generateKey("STU");
            case COURSE -> _generateKey("COU");
            case USER -> _generateKey("USR");
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}

	private  String _generateKey(String key) {
		System.out.printf("%s count ->%d",key,service.next());
		return String.format("%s%04d",key, service.next());

    }

}