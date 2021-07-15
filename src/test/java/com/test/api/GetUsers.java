package com.test.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.dto.Users;
import com.framework.utils.CommonUtil;
import com.framework.utils.RestUtil;

import io.restassured.response.Response;

/**
 * This class contains test cases which verify scenarios for getting users using
 * different param values
 * 
 * @author abhishek.tewari
 *
 */
public class GetUsers extends BaseTest {

	private RestUtil rest = new RestUtil();
	private CommonUtil util = new CommonUtil();

	private Map<String, String> params;

	@Test(groups = { "P0", "P1" })
	public void verifyGetUsersWithoutAnyparams() throws ParseException {
		test = extent.createTest("Verify Get Users Without Any params");
		params = new HashMap<String, String>();
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithSizeParams() throws ParseException {
		test = extent.createTest("verify Get Users With Size Params");
		params = new HashMap<String, String>();
		params.put("size", "1");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithPageParams() throws ParseException {
		test = extent.createTest("verify Get Users With Page Params");
		params = new HashMap<String, String>();
		params.put("page", "0");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithSortParamsOnId() throws ParseException {
		test = extent.createTest("verify Get Users With Sort Params");
		params = new HashMap<String, String>();
		params.put("sort", "id,desc");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
		verifyIdsAreInDescOrder(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithSortParamsOnFirstName() throws ParseException {
		test = extent.createTest("verify Get Users With Sort Params");
		params = new HashMap<String, String>();
		params.put("sort", "firstName,desc");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
		verifyFirstNameAreInDescOrder(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithSizeAndPageParams() throws ParseException {
		test = extent.createTest("verify Get Users With Size And Page Params");
		params = new HashMap<String, String>();
		params.put("page", "0");
		params.put("size", "3");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithSizeAndSortParams() throws ParseException {
		test = extent.createTest("verify Get Users With Size And Sort Params");
		params = new HashMap<String, String>();
		params.put("sort", "id,desc");
		params.put("size", "5");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
		verifyIdsAreInDescOrder(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithPageAndSortParams() throws ParseException {
		test = extent.createTest("verify Get Users With All Params");
		params = new HashMap<String, String>();
		params.put("sort", "id,desc");
		params.put("page", "0");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
		verifyIdsAreInDescOrder(users);
	}

	@Test(groups = { "P1" })
	public void verifyGetUsersWithAllParams() throws ParseException {
		test = extent.createTest("verify Get Users With Page And Page Params");
		params = new HashMap<String, String>();
		params.put("sort", "id,desc");
		params.put("page", "0");
		params.put("size", "15");
		Response response = rest.getUsers(params);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Users users = response.as(Users.class);
		verifyUsersResponse(users);
		verifyIdsAreInDescOrder(users);
	}

	/**
	 * Verify user response
	 * 
	 * @param users
	 * @throws ParseException
	 */
	private void verifyUsersResponse(Users users) throws ParseException {
		// Verify basic not null assertion for each object
		Assert.assertNotNull(users.getLinks());
		Assert.assertNotNull(users.getContent());
		Assert.assertNotNull(users.getPage());

		// Verify size of content is equal to page size and element
		Assert.assertEquals((int) users.getContent().size(), (int) users.getPage().getSize());
		Assert.assertNotNull(users.getPage().getTotalElements());
		Assert.assertNotNull(users.getPage().getTotalPages());
		Assert.assertNotNull(users.getPage().getNumber());

		for (int i = 0; i < users.getContent().size(); i++) {
			Assert.assertNotNull(users.getContent().get(i).getId());
			Assert.assertNotNull(users.getContent().get(i).getFirstName());
			Assert.assertNotNull(users.getContent().get(i).getLastName());
			Assert.assertNotNull(users.getContent().get(i).getEmail());
			Assert.assertNotNull(users.getContent().get(i).getDayOfBirth());
			Date birth = util.convertToDate(users.getContent().get(i).getDayOfBirth(), "yyyy-MM-dd");
			Date current = new Date();
			if (birth.getTime() > current.getTime()) {
				Assert.fail(
						"Birth date " + users.getContent().get(i).getDayOfBirth() + " is greater than today's date");
			}
		}
	}

	/**
	 * Verify that the ids are in descending order
	 * 
	 * @param users
	 */
	private void verifyIdsAreInDescOrder(Users users) {
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < users.getContent().size(); i++) {
			ids.add(users.getContent().get(i).getId());
		}
		Iterator<Integer> iter = ids.iterator();
		Integer current, previous = iter.next();
		boolean listSorted = true;
		while (iter.hasNext()) {
			current = iter.next();
			if (previous.compareTo(current) < 0) {
				listSorted = false;
			}
			previous = current;
		}
		Assert.assertTrue(listSorted);
	}

	/**
	 * Verify that the first name values are in descending order
	 * 
	 * @param users
	 */
	private void verifyFirstNameAreInDescOrder(Users users) {
		List<String> firstNames = new ArrayList<String>();
		for (int i = 0; i < users.getContent().size(); i++) {
			firstNames.add(users.getContent().get(i).getFirstName());
		}
		Iterator<String> iter = firstNames.iterator();
		String current, previous = iter.next();
		boolean listSorted = true;
		while (iter.hasNext()) {
			current = iter.next();
			if (previous.compareTo(current) < 0) {
				listSorted = false;
			}
			previous = current;
		}
		Assert.assertTrue(listSorted);
	}
}
