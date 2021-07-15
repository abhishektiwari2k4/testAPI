package com.test.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.dto.Content;
import com.framework.dto.Users;
import com.framework.utils.RestUtil;
import com.framework.utils.TestDataProvider;

import io.restassured.response.Response;

/**
 * This class contains test cases which verify scenarios for searching users by
 * their ids
 * 
 * @author abhishek.tewari
 *
 */
public class GetUserById extends BaseTest {

	private RestUtil rest = new RestUtil();

	@Test(groups = { "P0", "P1" })
	public void verifyGetUsersByUserId() {
		test = extent.createTest("Verify Get Users By UserId");

		// First Get all the users
		Map<String, String> params = new HashMap<String, String>();
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);

		// Then get users by their ids and compare values are same
		for (int i = 0; i < users.getContent().size(); i++) {
			response = rest.getUserById(users.getContent().get(i).getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
			Content userById = response.as(Content.class);
			verifyUsersResponse(userById, users.getContent().get(i).getId(), users.getContent().get(i));
		}
	}

	@Test(groups = { "P1" }, dataProviderClass = TestDataProvider.class, dataProvider = "invalidId")
	public void verifyGetUsersByUserIdInvalidId(int id) throws ParseException {
		test = extent.createTest("Verify Get Users By UserId Invalid Id");
		Response response = rest.getUserById(id);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
	}

	/**
	 * Verify the user response
	 * 
	 * @param userById
	 * @param id
	 * @param users
	 */
	private void verifyUsersResponse(Content userById, int id, Content users) {
		// Verify contents are equal
		Assert.assertEquals((int) userById.getId(), id);
		Assert.assertEquals(userById.getFirstName(), users.getFirstName());
		Assert.assertEquals(userById.getLastName(), users.getLastName());
		Assert.assertEquals(userById.getEmail(), users.getEmail());
		Assert.assertEquals(userById.getDayOfBirth(), users.getDayOfBirth());
		Assert.assertEquals(userById.getContent(), users.getContent());
		Assert.assertEquals(userById.getLinks(), users.getLinks());
	}
}
