package com.komici.challenge.rest.api;

import com.komici.challenge.rest.model.resource.MobileResource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
public interface MobileResourceApi {

    @ApiOperation(value = "", nickname = "mobileReource", notes = "get mobile resource info", response = MobileResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Read", response = MobileResource.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    MobileResource getMobileResource(Long id);
}
