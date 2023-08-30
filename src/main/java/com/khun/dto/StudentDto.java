package com.khun.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.khun.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDto implements Serializable {
    private String id;
    private String name;
    private Date dob;
    private int gender;
    private String phone;
    private String education;
    private String img_url;
    private List<Course> enrollment;

}