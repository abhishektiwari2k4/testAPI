package com.framework.pojo;

import lombok.Data;

/**
 * This class contains request objects to create request bodies
 * 
 * @author abhishek.tewari
 *
 */

@Data
public class UserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String dayOfBirth;
}
