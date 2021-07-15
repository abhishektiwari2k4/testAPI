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
public class Users {
	private List<Links> links;
	private List<Content> content;
	private Page page;
}
