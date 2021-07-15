package com.framework.rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class calls the Rest Template methods and returns response for different
 * HTTP methods defined
 * 
 * @author abhishek.tewari
 *
 */
public class RestClient {
	private RestTemplate restTemplate;

	public RestClient() {
		restTemplate = new RestTemplate();
	}

	public Response post(final RequestSpecification request) {
		request.contentType("application/json");
		return restTemplate.post(request);
	}

	public Response get(final RequestSpecification request) {
		return restTemplate.get(request);
	}

	public Response put(final RequestSpecification request) {
		request.contentType("application/json");
		return restTemplate.put(request);
	}

	public Response delete(final RequestSpecification request) {
		return restTemplate.delete(request);
	}
}