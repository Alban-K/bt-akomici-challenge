package com.komici.challenge.rest.controller;

import com.komici.challenge.SpringBootBTKomiciChallengeApplication;
import com.komici.challenge.rest.api.ApiResponseError;
import com.komici.challenge.rest.model.user.AddUser;
import com.komici.challenge.rest.model.user.UserListResponse;
import com.komici.challenge.rest.model.user.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootBTKomiciChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT {

    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testEmptyDefault() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<UserListResponse> response = restTemplate.exchange(
                createURLWithPort("/api/users"), HttpMethod.GET, entity, UserListResponse.class);

        UserListResponse responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(0, responseBody.getUserList().size());

    }

    @Test
    public void testAddUser() {

        AddUser addUser = createAnUser(true);

        HttpEntity<AddUser> entity = new HttpEntity<>(addUser, headers);

        ResponseEntity<UserModel> response = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, entity, UserModel.class);

        UserModel userModel = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(userModel);

        assertEquals(addUser.getEmail(), userModel.getEmail());
        assertEquals(addUser.getFirstname(), userModel.getFirstname());
        assertEquals(addUser.getSurname(), userModel.getSurname());
        assertEquals(addUser.getUsername(), userModel.getUsername());
        assertTrue(userModel.isEnable());

        cleanUp(userModel);

        testEmptyDefault();
    }

    @Test
    public void testAddingUsersWithSameEmail() {

        AddUser addUser = createAnUser(true);

        HttpEntity<AddUser> entity = new HttpEntity<>(addUser, headers);

        ResponseEntity<UserModel> response = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, entity, UserModel.class);

        UserModel userModel = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(userModel);

        AddUser sameUser = addUser;
        entity = new HttpEntity<>(sameUser, headers);

        ResponseEntity<ApiResponseError> responseForSameUser = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, entity, ApiResponseError.class);

        ApiResponseError responseForSameUserBody = responseForSameUser.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseForSameUser.getStatusCode());
        assertNotNull(responseForSameUserBody);
        assertTrue(responseForSameUserBody.getErrorMsg().contains("Unique index or primary key violation:"));

        cleanUp(userModel);

        testEmptyDefault();
    }

    @Test
    public void testDeletingNotExistingUser() throws Exception {


        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ApiResponseError> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/user/delete/" + 123124),
                HttpMethod.DELETE, entity, ApiResponseError.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ApiResponseError responseEntityBody = responseEntity.getBody();
        assertNotNull(responseEntityBody);
        assertEquals("The requested user does not exists", responseEntityBody.getErrorMsg());

        testEmptyDefault();
    }

    @Test
    public void testGetExistingUser() {

        AddUser addUser = createAnUser(true);

        HttpEntity<AddUser> entity = new HttpEntity<>(addUser, headers);

        ResponseEntity<UserModel> addResponse = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, entity, UserModel.class);

        UserModel addResponseBody = addResponse.getBody();

        assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());
        assertNotNull(addResponseBody);

        ResponseEntity<UserModel> getResponse = restTemplate.exchange(
                createURLWithPort("/api/user/" + addResponseBody.getId()), HttpMethod.GET, entity, UserModel.class);

        UserModel getResponseBody = getResponse.getBody();

        assertNotNull(getResponseBody);
        assertEquals(addUser.getEmail(), getResponseBody.getEmail());
        assertEquals(addUser.getFirstname(), getResponseBody.getFirstname());
        assertEquals(addUser.getSurname(), getResponseBody.getSurname());
        assertEquals(addUser.getUsername(), getResponseBody.getUsername());
        assertTrue(getResponseBody.isEnable());

        cleanUp(addResponseBody);

        testEmptyDefault();
    }


    @Test
    public void testAddingBadUserRequestWithEmptyUsername() {

        AddUser addUser = createAnUser(true);
        addUser.setUsername("");
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("username must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWithNullUsername() {

        AddUser addUser = createAnUser(true);
        addUser.setUsername(null);
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("username must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWithLongUsername() {

        AddUser addUser = createAnUser(true);
        addUser.setUsername("moreThanTwentyCharacterBecause12345677899434534534");
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("username size must be between 0 and 20", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWitEmptyEmail() {

        AddUser addUser = createAnUser(true);
        addUser.setEmail("");
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("email must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWithNullEmail() {

        AddUser addUser = createAnUser(true);
        addUser.setEmail(null);
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("email must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }


    @Test
    public void testAddingBadUserRequestWithNullFirstname() {

        AddUser addUser = createAnUser(true);
        addUser.setFirstname(null);
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("firstname must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWithEmptyFirstname() {

        AddUser addUser = createAnUser(true);
        addUser.setFirstname("");
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("firstname must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWithNullSurname() {

        AddUser addUser = createAnUser(true);
        addUser.setSurname(null);
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("surname must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }

    @Test
    public void testAddingBadUserRequestWithEmptySurname() {

        AddUser addUser = createAnUser(true);
        addUser.setSurname("");
        List<String> validationErrors = validationErrorsForBadUserRequest(addUser);
        assertEquals("surname must not be blank", validationErrors.get(0));

        testEmptyDefault();
    }


    //TODO test update

    private List<String> validationErrorsForBadUserRequest(AddUser addUser) {
        HttpEntity<AddUser> entity = new HttpEntity<>(addUser, headers);


        ResponseEntity<ApiResponseError> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, entity, ApiResponseError.class);


        ApiResponseError apiResponseError = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(apiResponseError);
        List<String> validationErrors = apiResponseError.getValidationErrors();
        assertEquals(1, validationErrors.size());
        return validationErrors;
    }
    private void cleanUp(UserModel userModel) {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURLWithPort("/api/user/delete/" + userModel.getId()),
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertFalse(deleteResponse.hasBody());
    }


    private static AddUser createAnUser(boolean enable) {
        AddUser addUser = new AddUser();
        addUser.setEmail("email@email.com");
        addUser.setUsername("akomici");
        addUser.setSurname("Komici");
        addUser.setFirstname("Alban");
        addUser.setEnable(enable);
        return addUser;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
