package com.komici.challenge.rest.controller;

import com.komici.challenge.FileUtility;
import com.komici.challenge.SpringBootBTKomiciChallengeApplication;
import com.komici.challenge.rest.api.ApiResponseError;
import com.komici.challenge.rest.model.resource.AddMobileResource;
import com.komici.challenge.rest.model.resource.BookingInfo;
import com.komici.challenge.rest.model.resource.MobileResourceModel;
import com.komici.challenge.rest.model.resource.UpdateMobileResource;
import com.komici.challenge.rest.model.user.AddUser;
import com.komici.challenge.rest.model.user.LiteUser;
import com.komici.challenge.rest.model.user.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootBTKomiciChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MobileResourceControllerITTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final HttpHeaders headers = new HttpHeaders();


    @Test
    public void testAllDefault() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/resources"), HttpMethod.GET, entity, String.class);

        String expected = FileUtility.readFileAsString("default-resources.json");
        String body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, body, false);
    }


    @Test
    public void testAddingNewMobileResourceDefault() throws Exception {

        HttpEntity<AddMobileResource> entity = new HttpEntity<>(new AddMobileResource("New Mobile"), headers);

        ResponseEntity<MobileResourceModel> response = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, entity, MobileResourceModel.class);

        MobileResourceModel mobileResourceModel = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(mobileResourceModel);
        assertEquals("New Mobile", mobileResourceModel.getName());
        assertFalse(mobileResourceModel.getBookingInfo().isBooked());

        cleanUp(mobileResourceModel.getId());

        testAllDefault();
    }

    @Test
    public void testGetExistingMobileResource() throws Exception {

        AddMobileResource addMobileResource = new AddMobileResource("aResourceMoBile");

        HttpEntity<AddMobileResource> entity = new HttpEntity<>(addMobileResource, headers);

        ResponseEntity<MobileResourceModel> addResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, entity, MobileResourceModel.class);

        MobileResourceModel actualAddedMobileResource = addResponse.getBody();

        assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());
        assertNotNull(actualAddedMobileResource);

        HttpEntity<Void> getEntity = new HttpEntity<>(null, headers);

        ResponseEntity<MobileResourceModel> getResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/" + actualAddedMobileResource.getId()), HttpMethod.GET, getEntity, MobileResourceModel.class);


        MobileResourceModel actualMobileResource = getResponse.getBody();

        assertNotNull(actualMobileResource);
        assertEquals("aResourceMoBile", actualMobileResource.getName());
        assertFalse(actualMobileResource.getBookingInfo().isBooked());

        cleanUp(actualAddedMobileResource.getId());

        testAllDefault();
    }

    @Test
    public void testGetNotExistingMobileResource() throws Exception {


        HttpEntity<Void> getEntity = new HttpEntity<>(null, headers);

        ResponseEntity<ApiResponseError> getResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/" + 40444L), HttpMethod.GET, getEntity, ApiResponseError.class);

        ApiResponseError actualResponseError = getResponse.getBody();

        assertNotNull(actualResponseError);
        assertEquals("No entity found with id=40444", actualResponseError.getErrorMsg());

        testAllDefault();
    }

    @Test
    public void testDeletingNotExistingMobileResource() throws Exception {

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ApiResponseError> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/delete/" + 404040404),
                HttpMethod.DELETE, entity, ApiResponseError.class);

        ApiResponseError responseEntityBody = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntityBody);
        assertEquals("The requested resource does not exists", responseEntityBody.getErrorMsg());

        testAllDefault();
    }

    @Test
    public void testDeletingExistingMobileResource() throws Exception {

        AddMobileResource addMobileResource = new AddMobileResource("aResourceMoBile");

        HttpEntity<AddMobileResource> entity = new HttpEntity<>(addMobileResource, headers);

        ResponseEntity<MobileResourceModel> addResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, entity, MobileResourceModel.class);

        MobileResourceModel actualAddedMobileResource = addResponse.getBody();

        assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());
        assertNotNull(actualAddedMobileResource);

        ResponseEntity<Void> deleteResponseEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/delete/" + actualAddedMobileResource.getId()),
                HttpMethod.DELETE, entity, Void.class);

        assertEquals(HttpStatus.OK, deleteResponseEntity.getStatusCode());
        assertFalse(deleteResponseEntity.hasBody());

        testAllDefault();
    }


    @Test
    public void testUpdateExistingMobileResource() throws Exception {

        AddMobileResource addMobileResource = new AddMobileResource("aResourceMoBile");

        HttpEntity<AddMobileResource> entity = new HttpEntity<>(addMobileResource, headers);

        ResponseEntity<MobileResourceModel> actualAddedResponseEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, entity, MobileResourceModel.class);

        MobileResourceModel actualAddedMobileResource = actualAddedResponseEntity.getBody();

        assertEquals(HttpStatus.CREATED, actualAddedResponseEntity.getStatusCode());
        assertNotNull(actualAddedMobileResource);

        UpdateMobileResource updateUser = new UpdateMobileResource("newChangedName", actualAddedMobileResource.getId());

        HttpEntity<UpdateMobileResource> actualUpdatedEntity = new HttpEntity<>(updateUser, headers);

        ResponseEntity<MobileResourceModel> responseUpdate = restTemplate.exchange(
                createURLWithPort("/api/resource/update"), HttpMethod.PUT, actualUpdatedEntity, MobileResourceModel.class);

        MobileResourceModel actualUpdatedMobileResource = responseUpdate.getBody();

        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
        assertNotNull(actualUpdatedMobileResource);

        assertEquals("newChangedName", actualUpdatedMobileResource.getName());
        assertEquals(actualAddedMobileResource.getId(), actualUpdatedMobileResource.getId());

        cleanUp(actualAddedMobileResource.getId());

        testAllDefault();
    }


    @Test
    public void testUpdateNotExistingMobileResource() {

        UpdateMobileResource anUpdateUser = new UpdateMobileResource("notExisting", 404232412L);

        HttpEntity<UpdateMobileResource> updateEntity = new HttpEntity<>(anUpdateUser, headers);

        ResponseEntity<ApiResponseError> actualUpdateResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/update"), HttpMethod.PUT, updateEntity, ApiResponseError.class);

        ApiResponseError apiResponseError = actualUpdateResponse.getBody();

        assertEquals(HttpStatus.NOT_FOUND, actualUpdateResponse.getStatusCode());
        assertNotNull(apiResponseError);

        assertEquals("The requested resource does not exists", apiResponseError.getErrorMsg());
    }

    @Test
    public void testBookingMobileResource() throws Exception {

        //GIVEN
        AddMobileResource addMobileResource = new AddMobileResource("aResourceMoBile");

        HttpEntity<AddMobileResource> addedMobileEntity = new HttpEntity<>(addMobileResource, headers);

        ResponseEntity<MobileResourceModel> addedMobileResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, addedMobileEntity, MobileResourceModel.class);

        MobileResourceModel actualAddedMobileResource = addedMobileResponse.getBody();

        assertEquals(HttpStatus.CREATED, addedMobileResponse.getStatusCode());
        assertNotNull(actualAddedMobileResource);

        AddUser addUser = createAnUser(true);

        HttpEntity<AddUser> addedUserEntity = new HttpEntity<>(addUser, headers);

        ResponseEntity<UserModel> addedUserResponseEntity = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, addedUserEntity, UserModel.class);

        UserModel actualAddedUserModel = addedUserResponseEntity.getBody();

        assertEquals(HttpStatus.CREATED, addedUserResponseEntity.getStatusCode());
        assertNotNull(actualAddedUserModel);

        Date actualDateRequest = new Date();

        //WHEN
        ResponseEntity<MobileResourceModel> actualBookedEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/book/" + actualAddedMobileResource.getId() + "/user/" + actualAddedUserModel.getId()),
                HttpMethod.PUT, null, MobileResourceModel.class);

        //THEN

        MobileResourceModel actualBookedResource = actualBookedEntity.getBody();

        assertEquals(HttpStatus.OK, actualBookedEntity.getStatusCode());
        assertNotNull(actualBookedResource);
        BookingInfo bookingInfo = actualBookedResource.getBookingInfo();
        LiteUser user = bookingInfo.getUser();

        assertTrue(bookingInfo.isBooked());
        assertEquals(addUser.getUsername(), user.getUsername());
        assertEquals(actualAddedUserModel.getId(), user.getId());
        assertEquals(addUser.getEmail(), user.getEmail());

        Date bookingDate = bookingInfo.getBookingDate();
        assertTrue(bookingDate.after(actualDateRequest));
        assertTrue(bookingDate.before(new Date()));

        cleanUp(actualAddedMobileResource, actualAddedUserModel);

        testAllDefault();
    }

    @Test
    public void testAlreadyBookedMobileResource() throws Exception {

        //GIVEN
        AddMobileResource addMobileResource = new AddMobileResource("aResourceMoBile");

        HttpEntity<AddMobileResource> addedMobileEntity = new HttpEntity<>(addMobileResource, headers);

        ResponseEntity<MobileResourceModel> addedMobileResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, addedMobileEntity, MobileResourceModel.class);

        MobileResourceModel actualAddedMobileResource = addedMobileResponse.getBody();

        assertEquals(HttpStatus.CREATED, addedMobileResponse.getStatusCode());
        assertNotNull(actualAddedMobileResource);

        AddUser addFirstUser = createAnUser(true);

        HttpEntity<AddUser> addedFirstUserEntity = new HttpEntity<>(addFirstUser, headers);

        ResponseEntity<UserModel> addedFirstUserResponseEntity = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, addedFirstUserEntity, UserModel.class);

        UserModel actualFirstAddedUserModel = addedFirstUserResponseEntity.getBody();

        assertEquals(HttpStatus.CREATED, addedFirstUserResponseEntity.getStatusCode());
        assertNotNull(actualFirstAddedUserModel);

        Date actualDateRequest = new Date();

        ResponseEntity<MobileResourceModel> actualBookedEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/book/" + actualAddedMobileResource.getId() + "/user/" + actualFirstAddedUserModel.getId()),
                HttpMethod.PUT, null, MobileResourceModel.class);


        MobileResourceModel firstBookedResource = actualBookedEntity.getBody();

        assertEquals(HttpStatus.OK, actualBookedEntity.getStatusCode());
        assertNotNull(firstBookedResource);
        BookingInfo firstBookingInfo = firstBookedResource.getBookingInfo();
        assertTrue(firstBookingInfo.isBooked());
        Date bookingDate = firstBookingInfo.getBookingDate();
        assertTrue(bookingDate.after(actualDateRequest));
        assertTrue(bookingDate.before(new Date()));

        //create another actualUser
        AddUser addSecondUser = createAnUser(true);
        addSecondUser.setEmail("another@email.com");
        addSecondUser.setUsername("AnotherName");

        HttpEntity<AddUser> addedSecondUserEntity = new HttpEntity<>(addSecondUser, headers);

        ResponseEntity<UserModel> addedAnotherUserResponseEntity = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, addedSecondUserEntity, UserModel.class);

        UserModel actualAddedSecondUserModel = addedAnotherUserResponseEntity.getBody();

        assertEquals(HttpStatus.CREATED, addedFirstUserResponseEntity.getStatusCode());
        assertNotNull(actualAddedSecondUserModel);

        //WHEN
        ResponseEntity<ApiResponseError> anotherBookingEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/book/" + actualAddedMobileResource.getId() + "/user/" + actualAddedSecondUserModel.getId()),
                HttpMethod.PUT, null, ApiResponseError.class);

        //THEN
        assertEquals(HttpStatus.CONFLICT, anotherBookingEntity.getStatusCode());
        ApiResponseError apiResponseError = anotherBookingEntity.getBody();

        assertNotNull(apiResponseError);
        assertEquals("The requested resource is already booked", apiResponseError.getErrorMsg());

        //check if the booking info didn't change
        ResponseEntity<MobileResourceModel> getResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/" + actualAddedMobileResource.getId()), HttpMethod.GET, null, MobileResourceModel.class);

        MobileResourceModel actualGetMobileResource = getResponse.getBody();

        assertNotNull(actualGetMobileResource);
        assertEquals(addMobileResource.getName(), actualGetMobileResource.getName());
        BookingInfo actualGetBookingInfo = actualGetMobileResource.getBookingInfo();
        assertTrue(actualGetBookingInfo.isBooked());
        assertEquals(firstBookingInfo.getBookingDate(), actualGetBookingInfo.getBookingDate());

        LiteUser actualUser = actualGetMobileResource.getBookingInfo().getUser();
        LiteUser firstBookingInfoUser = firstBookingInfo.getUser();
        assertEquals(firstBookingInfoUser.getId(), actualUser.getId());
        assertEquals(firstBookingInfoUser.getEmail(), actualUser.getEmail());
        assertEquals(firstBookingInfoUser.getUsername(), actualUser.getUsername());

        cleanUp(actualAddedMobileResource.getId());
        cleanUpUser(actualFirstAddedUserModel);
        cleanUpUser(actualAddedSecondUserModel);

        testAllDefault();
    }

    @Test
    public void testCheckoutMobileResource() throws Exception {

        //GIVEN
        AddMobileResource addMobileResource = new AddMobileResource("aResourceMoBile");

        HttpEntity<AddMobileResource> addedMobileEntity = new HttpEntity<>(addMobileResource, headers);

        ResponseEntity<MobileResourceModel> addedMobileResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, addedMobileEntity, MobileResourceModel.class);

        MobileResourceModel actualAddedMobileResource = addedMobileResponse.getBody();

        assertEquals(HttpStatus.CREATED, addedMobileResponse.getStatusCode());
        assertNotNull(actualAddedMobileResource);

        AddUser addUser = createAnUser(true);

        HttpEntity<AddUser> addedUserEntity = new HttpEntity<>(addUser, headers);

        ResponseEntity<UserModel> addedUserResponseEntity = restTemplate.exchange(
                createURLWithPort("/api/user/add"), HttpMethod.POST, addedUserEntity, UserModel.class);

        UserModel actualAddedUserModel = addedUserResponseEntity.getBody();

        assertEquals(HttpStatus.CREATED, addedUserResponseEntity.getStatusCode());
        assertNotNull(actualAddedUserModel);

        ResponseEntity<MobileResourceModel> actualBookedEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/book/" + actualAddedMobileResource.getId() + "/user/" + actualAddedUserModel.getId()),
                HttpMethod.PUT, null, MobileResourceModel.class);

        MobileResourceModel actualBookedResource = actualBookedEntity.getBody();
        assertEquals(HttpStatus.OK, actualBookedEntity.getStatusCode());
        assertNotNull(actualBookedResource);
        BookingInfo bookingInfo = actualBookedResource.getBookingInfo();
        assertTrue(bookingInfo.isBooked());

        //WHEN

        ResponseEntity<MobileResourceModel> actualCheckoutEntity = restTemplate.exchange(
                createURLWithPort("/api/resource/checkout/" + actualAddedMobileResource.getId()),
                        HttpMethod.PUT, null, MobileResourceModel.class);

        MobileResourceModel actualCheckoutResource = actualCheckoutEntity.getBody();

        //THEN
        assertEquals(HttpStatus.OK, actualCheckoutEntity.getStatusCode());
        assertNotNull(actualCheckoutResource);
        BookingInfo checkoutBookingInfo = actualCheckoutResource.getBookingInfo();
        assertFalse(checkoutBookingInfo.isBooked());
        assertNull(checkoutBookingInfo.getBookingDate());
        assertNull(checkoutBookingInfo.getUser());

        cleanUp(actualAddedMobileResource, actualAddedUserModel);

        testAllDefault();
    }

    private void cleanUp(MobileResourceModel actualAddedMobileResource, UserModel userModel) {
        cleanUp(actualAddedMobileResource.getId());

        cleanUpUser(userModel);
    }

    private void cleanUpUser(UserModel userModel) {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURLWithPort("/api/user/delete/" + userModel.getId()),
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertFalse(deleteResponse.hasBody());
    }


    private void cleanUp(Long id) {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURLWithPort("/api/resource/delete/" + id),
                HttpMethod.DELETE, null, Void.class);

        assertEquals(deleteResponse.getStatusCode(), HttpStatus.OK);
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
