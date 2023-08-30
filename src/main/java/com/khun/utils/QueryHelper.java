package com.khun.utils;

public class QueryHelper {
	// For user table

	public final static String INSERT_USER = "INSERT INTO users values(?,?,?,?,?,?)";

	public final static String FIND_BY_USER_ID = "SELECT * FROM users WHERE user_id = ?";
	public final static String FIND_BY_USER_EMAIL = "SELECT * FROM users WHERE email = ?";
	public final static String FETCH_USER_COUNT = "SELECT count(*) FROM users";
	public final static String FETCH_ALL_USER = "SELECT * FROM users WHERE role = ?";

	public final static String UPDATE_USER = "UPDATE users SET name = ?, email = ?, password = ?, role = ?, status = ? WHERE user_id = ?";
	public final static String DISABLE_OR_ENABLE_USER = "UPDATE users SET status = ? WHERE user_id = ?";

	// For Course table
	public final static String INSERT_COURSE = "INSERT INTO course values(?,?)";

	public final static String FETCH_COURSE_COUNT = "SELECT count(*) FROM course";
	public final static String FETCH_ALL_COURSE = "SELECT * FROM course ORDER BY course_id";
	
	public final static String UPDATE_COURSE = "UPDATE course SET course_name = ? WHERE course_id = ?";


	// For Student Table
	public final static String INSERT_STUDENT = "INSERT INTO student values(?,?,?,?,?,?,?)";
	public final static String DELETE_STUDENT = "DELETE FROM student WHERE student_id=?";
	public final static String FIND_BY_STUDENT_ID = "SELECT * FROM student WHERE student_id = ?";
	public final static String FETCH_STUDENT_COUNT = "SELECT count(*) FROM student";
	public final static String FETCH_ALL_STUDENT = "SELECT * FROM student";

	public final static String UPDATE_STUDENT = "UPDATE student SET name = ?, dob = ?, gender = ?, phone = ?, education = ?, img_url = ? WHERE student_id = ?";

	// For Details Table
	public final static String INSERT_DETAILS = "INSERT INTO details (student_id, course_id) VALUES (?, ?)";
	public final static String DELETE_DETAILS = "DELETE FROM details WHERE student_id=?";

	public final static String FETCH_ALL_STUDENT_INFO = """
            SELECT s.*, c.*
            	FROM student as s
            	JOIN details as cd ON s.id = cd.student_id
            	JOIN course as c ON cd.course_id = c.id;
            """;
	
	public final static String FIND_STUDENT_INFO_BY_ID = """
			SELECT s.*, c.*
				FROM (SELECT * FROM student WHERE student_id=?) as s
				JOIN details as cd ON s.student_id = cd.student_id
				JOIN course as c ON cd.course_id = c.course_id;
			""";
}
