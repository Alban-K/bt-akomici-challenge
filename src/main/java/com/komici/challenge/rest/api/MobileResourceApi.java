package com.komici.challenge.rest.api;

import com.komici.challenge.rest.model.resource.AddMobileResource;
import com.komici.challenge.rest.model.resource.MobileResourceModel;
import com.komici.challenge.rest.model.resource.UpdateMobileResource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

public interface MobileResourceApi {

    @ApiOperation(value = "", nickname = "mobileResource", notes = "get mobile resource info", response = MobileResourceModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Read", response = MobileResourceModel.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    MobileResourceModel getMobileResource(Long id);

    @ApiOperation(value = "", nickname = "saveUser", notes = "add new mobile resource ", response = MobileResourceModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Resource created", response = MobileResourceModel.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    MobileResourceModel addMobileResource(@ApiParam(value = "Mobile Resource to be added", required = true) AddMobileResource addMobileResource);

    @ApiOperation(value = "", nickname = "updateResource", notes = "update a resource", response = MobileResourceModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Updated", response = MobileResourceModel.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    MobileResourceModel updateMobileResource(@ApiParam(value = "User to be updated", required = true) UpdateMobileResource mobileResource);

    @ApiOperation(value = "", nickname = "deleteUser", notes = "delete a resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource delete"),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    void deleteMobileResource(@ApiParam(value = "id of the user to be deleted", required = true) Long id);

    @ApiOperation(value = "", nickname = "bookResource", notes = "booking of a resource", response = MobileResourceModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Booked", response = MobileResourceModel.class),
            @ApiResponse(code = 409, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    MobileResourceModel bookResource(Long resourceId, Long userId);

    @ApiOperation(value = "", nickname = "returnResource", notes = "return a booked resource", response = MobileResourceModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Booked", response = MobileResourceModel.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    MobileResourceModel checkoutResource(Long resourceId);

}
