package com.test.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.constants.Constants;
import com.framework.dto.Content;
import com.framework.dto.ErrorResponse;
import com.framework.dto.SubError;
import com.framework.exceptions.ErrorMessage;
import com.framework.pojo.UserRequest;
import com.framework.utils.CommonUtil;
import com.framework.utils.RestUtil;
import com.framework.utils.TestDataProvider;

import io.restassured.response.Response;

/**
 * This class contains test cases which validates different scenarios for user
 * creation
 * 
 * @author abhishek.tewari
 *
 */
public class CreateUser extends BaseTest {

	private RestUtil rest = new RestUtil();
	private CommonUtil util = new CommonUtil();

	@Test(groups = { "P0", "P1" })
	public void verifyCreateUser() {
		test = extent.createTest("verify create user");
		UserRequest request = rest.getUserRequest();
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
		Content users = response.as(Content.class);
		verifyUsersResponse(users, request);
	}

	@Test(groups = { "P1" })
	public void verifyTwoUsersCanHaveSameBirthDate() {
		test = extent.createTest("verify Two Users Can Have Same BirthDate");
		UserRequest request = rest.getUserRequest();
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
		Content users = response.as(Content.class);
		verifyUsersResponse(users, request);

		// Creating user with same request body but different email
		request.setEmail(util.getRandomEmail());
		response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
		users = response.as(Content.class);
		verifyUsersResponse(users, request);
	}

	@Test(groups = { "P1" })
	public void verifyTwoUsersCannotHaveSameEmail() throws ParseException {
		test = extent.createTest("verify Two Users Cannot Have Same Email");
		UserRequest request = rest.getUserRequest();
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
		Content users = response.as(Content.class);
		verifyUsersResponse(users, request);

		// Creating user with same request body
		response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CONFLICT);
		ErrorResponse error = response.as(ErrorResponse.class);
		verifyErrorResponseWrongValue(error, ErrorMessage.CONFLICT);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserBlankFirstName() throws ParseException {
		test = extent.createTest("verify create user blank first name");
		UserRequest request = rest.getUserRequest();
		request.setFirstName("");
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.FIRST_NAME);
		er.setRejectedValue("");
		er.setMessage(Constants.CANNOT_BE_BLANK);

		SubError er1 = new SubError();
		er1.setObject(Constants.USER);
		er1.setField(Constants.FIRST_NAME);
		er1.setRejectedValue("");
		er1.setMessage(Constants.SIZE_INCORRECT30);
		error.add(er);
		error.add(er1);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" }, dataProviderClass = TestDataProvider.class, dataProvider = "firstNameLength")
	public void verifyCreateUserInvalidLengthFirstName(int length) throws ParseException {
		test = extent.createTest("verify create user Invalid Length first name");
		UserRequest request = rest.getUserRequest();
		request.setFirstName(util.getRandomString(length));
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.FIRST_NAME);
		er.setRejectedValue(request.getFirstName());
		er.setMessage(Constants.SIZE_INCORRECT30);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserNullFirstName() throws ParseException {
		test = extent.createTest("verify create user null first name");
		UserRequest request = rest.getUserRequest();
		request.setFirstName(null);
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.FIRST_NAME);
		er.setRejectedValue(null);
		er.setMessage(Constants.CANNOT_BE_BLANK);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserBlankLastName() throws ParseException {
		test = extent.createTest("verify create user blank last name");
		UserRequest request = rest.getUserRequest();
		request.setLastName("");
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.LAST_NAME);
		er.setRejectedValue("");
		er.setMessage(Constants.CANNOT_BE_BLANK);

		SubError er1 = new SubError();
		er1.setObject(Constants.USER);
		er1.setField(Constants.LAST_NAME);
		er1.setRejectedValue("");
		er1.setMessage(Constants.SIZE_INCORRECT15);
		error.add(er);
		error.add(er1);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" }, dataProviderClass = TestDataProvider.class, dataProvider = "lastNameLength")
	public void verifyCreateUserInvalidLengthLastName(int length) throws ParseException {
		test = extent.createTest("verify create user Invalid Length last name");
		UserRequest request = rest.getUserRequest();
		request.setLastName(util.getRandomString(length));
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.LAST_NAME);
		er.setRejectedValue(request.getLastName());
		er.setMessage(Constants.SIZE_INCORRECT15);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserNullLastName() throws ParseException {
		test = extent.createTest("verify create user null last name");
		UserRequest request = rest.getUserRequest();
		request.setLastName(null);
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.LAST_NAME);
		er.setRejectedValue(null);
		er.setMessage(Constants.CANNOT_BE_BLANK);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserBlankEmail() throws ParseException {
		test = extent.createTest("verify create user blank email");
		UserRequest request = rest.getUserRequest();
		request.setEmail("");
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.EMAIL);
		er.setRejectedValue("");
		er.setMessage(Constants.CANNOT_BE_BLANK);

		SubError er1 = new SubError();
		er1.setObject(Constants.USER);
		er1.setField(Constants.EMAIL);
		er1.setRejectedValue("");
		er1.setMessage(Constants.WELL_FORMED_EMAIL);
		error.add(er);
		error.add(er1);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserNullEmail() throws ParseException {
		test = extent.createTest("verify create user null email");
		UserRequest request = rest.getUserRequest();
		request.setEmail(null);
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.EMAIL);
		er.setRejectedValue(null);
		er.setMessage(Constants.CANNOT_BE_BLANK);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" }, dataProviderClass = TestDataProvider.class, dataProvider = "invalidEmail")
	public void verifyCreateUserInvalidEmail(String email) throws ParseException {
		test = extent.createTest("verify create user invalid email");
		UserRequest request = rest.getUserRequest();
		request.setEmail(email);
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.EMAIL);
		er.setRejectedValue(email);
		er.setMessage(Constants.WELL_FORMED_EMAIL);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" }, dataProviderClass = TestDataProvider.class, dataProvider = "nullBlank")
	public void verifyCreateUserBlankBirthDate(String blank) throws ParseException {
		test = extent.createTest("verify create user Blank Birth Date");
		UserRequest request = rest.getUserRequest();
		request.setDayOfBirth(blank);
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.DAY_OF_BIRTH);
		er.setRejectedValue(null);
		er.setMessage(Constants.CANNOT_BE_NULL);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" })
	public void verifyCreateUserFutureBirthDate() throws ParseException {
		test = extent.createTest("verify create user future birth date");
		UserRequest request = rest.getUserRequest();
		request.setDayOfBirth(util.getDate(1, "yyyy-MM-dd"));
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);

		List<SubError> error = new ArrayList<SubError>();
		SubError er = new SubError();
		er.setObject(Constants.USER);
		er.setField(Constants.DAY_OF_BIRTH);
		er.setRejectedValue(request.getDayOfBirth());
		er.setMessage(Constants.PAST_DATE);
		error.add(er);
		verifyErrorResponse(users, error, ErrorMessage.VALIDATION_FAILED);
	}

	@Test(groups = { "P1" }, dataProviderClass = TestDataProvider.class, dataProvider = "invalidFormat")
	public void verifyCreateUserInvalidFormatBirthDate(String format) throws ParseException {
		test = extent.createTest("verify create user invalid format birth date");
		UserRequest request = rest.getUserRequest();
		request.setDayOfBirth(util.getDate(-1000, format));
		Response response = rest.createUser(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
		ErrorResponse users = response.as(ErrorResponse.class);
		verifyErrorResponseWrongValue(users, ErrorMessage.WRONG_FORMAT);
	}

	/**
	 * Verify success response
	 * 
	 * @param users
	 * @param request
	 */
	private void verifyUsersResponse(Content users, UserRequest request) {
		// Verify basic not null assertion for each object
		Assert.assertNotNull(users.getLinks());
		Assert.assertNotNull(users.getContent());

		Assert.assertNotNull(users.getId());
		Assert.assertEquals(users.getFirstName(), request.getFirstName());
		Assert.assertEquals(users.getLastName(), request.getLastName());
		Assert.assertEquals(users.getEmail(), request.getEmail());
		Assert.assertEquals(users.getDayOfBirth(), request.getDayOfBirth());
		verifyGetUsersByUserId(users.getId(), users);
	}

	/**
	 * Verify error response
	 * 
	 * @param response
	 * @param error
	 * @param message
	 * @throws ParseException
	 */
	private void verifyErrorResponse(ErrorResponse response, List<SubError> error, ErrorMessage message)
			throws ParseException {
		Assert.assertEquals(response.getStatus(), message.getStatus());
		Assert.assertEquals(util.convertToDateStringValue(response.getTimestamp(), "dd-MM-yyyy hh:mm"),
				util.getDate(0, "dd-MM-yyyy hh:mm"));
		Assert.assertEquals(response.getMessage(), message.getMessage());
		Assert.assertNull(response.getDebugMessage());
		Assert.assertTrue(error.containsAll(response.getSubErrors()));
	}

	/**
	 * Verify error response having debug message
	 * 
	 * @param response
	 * @param message
	 * @throws ParseException
	 */
	private void verifyErrorResponseWrongValue(ErrorResponse response, ErrorMessage message) throws ParseException {
		Assert.assertEquals(response.getStatus(), message.getStatus());
		Assert.assertEquals(util.convertToDateStringValue(response.getTimestamp(), "dd-MM-yyyy hh:mm"),
				util.getDate(0, "dd-MM-yyyy hh:mm"));
		Assert.assertEquals(response.getMessage(), message.getMessage());
		Assert.assertNotNull(response.getDebugMessage());
		Assert.assertNull(response.getSubErrors());
	}

	/**
	 * Verify the created user by searching them with id
	 * 
	 * @param id
	 * @param users
	 */
	private void verifyGetUsersByUserId(int id, Content users) {
		Response response = rest.getUserById(id);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		Content userById = response.as(Content.class);
		Assert.assertEquals((int) userById.getId(), id);
		Assert.assertEquals(userById.getFirstName(), users.getFirstName());
		Assert.assertEquals(userById.getLastName(), users.getLastName());
		Assert.assertEquals(userById.getEmail(), users.getEmail());
		Assert.assertEquals(userById.getDayOfBirth(), users.getDayOfBirth());
		Assert.assertEquals(userById.getContent(), users.getContent());
		Assert.assertEquals(userById.getLinks(), users.getLinks());
	}
}
