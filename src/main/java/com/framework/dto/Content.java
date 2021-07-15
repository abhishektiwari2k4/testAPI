package com.framework.dto;

import java.util.List;

import lombok.Data;

/**
 * These DTO classes map the response objects into class variables which can
 * then further be used for validation
 * 
 * @author abhishek.tewari
 *
 */

@Data
public class Content {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String dayOfBirth;
	private List<Object> content;
	private List<Links> links;
}
