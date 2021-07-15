package com.framework.rest;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class contains the HTTP methods post, get, put, and delete
 * 
 * @author abhishek.tewari
 *
 */
public class RestTemplate {

	public Response post(final RequestSpecification req) {
		return given().spec(req).when().log().all().post().then().extract().response();
	}

	public Response get(final RequestSpecification req) {
		return given().spec(req).when().log().all().get().then().extract().response();
	}

	public Response put(RequestSpecification req) {
		return given().spec(req).when().log().all().put().then().extract().response();
	}

	public Response delete(RequestSpecification req) {
		return given().spec(req).when().log().all().delete().then().extract().response();
	}
}
