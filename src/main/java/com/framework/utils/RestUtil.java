package com.framework.utils;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.framework.base.BaseTest;
import com.framework.constants.Constants;
import com.framework.constants.RestUri;
import com.framework.pojo.UserRequest;
import com.framework.rest.RestClient;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class contains methods for rest api execution
 * 
 * @author abhishek.tewari
 *
 */
public class RestUtil extends BaseTest {

	private RestClient restClient = new RestClient();
	private CommonUtil util = new CommonUtil();

	/**
	 * Get Users with params
	 * 
	 * @return Response
	 */
	public Response getUsers(Map<String, String> params) {
		RequestSpecification request;
		requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(RestUri.BASE_URI);
		request = requestSpecBuilder.build();
		request.basePath(RestUri.USERS);
		if (params != null) {
			request.queryParams(params);
		}
		Response response = restClient.get(request);
		System.out.println("Response for get users is " + response.prettyPrint());
		return response;
	}

	/**
	 * Get User by id
	 * 
	 * @return Response
	 */
	public Response getUserById(int id) {
		RequestSpecification request;
		requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(RestUri.BASE_URI);
		request = requestSpecBuilder.build();
		request.basePath(RestUri.USERS + "/" + id);
		Response response = restClient.get(request);
		System.out.println("Response for get user by id is " + response.prettyPrint());
		return response;
	}

	/**
	 * Get user request
	 * 
	 * @return UserRequest
	 */
	public UserRequest getUserRequest() {
		UserRequest user = new UserRequest();
		user.setFirstName(Constants.FIRST_NAME_VALUE);
		user.setLastName(Constants.LAST_NAME_VALUE);
		user.setEmail(util.getRandomEmail());
		user.setDayOfBirth(util.getDate(-Integer.parseInt(RandomStringUtils.randomNumeric(4)), "yyyy-MM-dd"));
		return user;
	}

	/**
	 * Create User
	 * 
	 * @return Response
	 */
	public Response createUser(UserRequest user) {
		RequestSpecification request;
		requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(RestUri.BASE_URI);
		request = requestSpecBuilder.build();
		request.basePath(RestUri.USERS);
		request.body(user);
		Response response = restClient.post(request);
		System.out.println("Response for create user is " + response.prettyPrint());
		return response;
	}

}