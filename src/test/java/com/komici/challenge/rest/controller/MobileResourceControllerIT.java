package com.komici.challenge.rest.controller;

import com.komici.challenge.FileUtility;
import com.komici.challenge.SpringBootBTKomiciChallengeApplication;
import com.komici.challenge.rest.api.ApiResponseError;
import com.komici.challenge.rest.model.resource.AddMobileResource;
import com.komici.challenge.rest.model.resource.MobileResourceModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootBTKomiciChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MobileResourceControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();


    @Test
    public void testAllDefault() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/resources"), HttpMethod.GET, entity, String.class);

        String expected = FileUtility.readFileAsString("default-resources.json");

        String body = response.getBody();
        assertEquals(response.getStatusCode() , HttpStatus.OK);
        JSONAssert.assertEquals(expected, body, false);
    }


    @Test
    public void testAddingNewMobileResourceDefault() throws Exception {


        HttpEntity<AddMobileResource> entity = new HttpEntity<>(new AddMobileResource("New Mobile"), headers);

        ResponseEntity<MobileResourceModel> response = restTemplate.exchange(
                createURLWithPort("/api/resource/add"), HttpMethod.POST, entity, MobileResourceModel.class);


        MobileResourceModel mobileResourceModel = response.getBody();
        assertEquals(response.getStatusCode() , HttpStatus.CREATED);
        assertNotNull(mobileResourceModel);
        assertEquals("New Mobile", mobileResourceModel.getName());
        assertFalse(mobileResourceModel.getBookingInfo().isBooked());

        ResponseEntity<Void> deleteResponse =  restTemplate.exchange(
                createURLWithPort("/api/resource/delete/" + mobileResourceModel.getId()),
                HttpMethod.DELETE, null, Void.class);

        assertEquals(deleteResponse.getStatusCode() , HttpStatus.OK);
        assertFalse(deleteResponse.hasBody());

        testAllDefault();
    }

    @Test
    public void testDeletingNotExistingMobileResource() throws Exception {


        HttpEntity<Void> entity = new HttpEntity<>(null, headers);


        ResponseEntity<ApiResponseError> responseEntity =  restTemplate.exchange(
                createURLWithPort("/api/resource/delete/" + 404040404),
                HttpMethod.DELETE, entity, ApiResponseError.class);

        assertEquals(responseEntity.getStatusCode() , HttpStatus.NOT_FOUND);
        ApiResponseError responseEntityBody = responseEntity.getBody();
        assertNotNull(responseEntityBody);
        assertEquals("The requested resource does not exists", responseEntityBody.getErrorMsg());

        testAllDefault();
    }



    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
