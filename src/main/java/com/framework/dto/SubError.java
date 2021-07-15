package com.framework.dto;

import lombok.Data;

/**
 * These DTO classes map the response objects into class variables which can
 * then further be used for validation
 * 
 * @author abhishek.tewari
 *
 */

@Data
public class SubError {
	private String object;
	private String field;
	private String rejectedValue;
	private String message;
}
