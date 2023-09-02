package com.khun.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.khun.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 8915504654857823697L;
    private String id;
    private String name;
    private Timestamp createdAt;
    private boolean active;
    private List<Student> students;
}
