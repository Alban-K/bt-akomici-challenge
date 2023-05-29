package com.komici.challenge.rest.api;

import com.komici.challenge.rest.model.resource.MobileResource;
import com.komici.challenge.rest.model.user.AddUser;
import com.komici.challenge.rest.model.user.UpdateUser;
import com.komici.challenge.rest.model.user.UserListResponse;
import com.komici.challenge.rest.model.user.UserModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface UserApi {

    @ApiOperation(value = "", nickname = "user", notes = "get user resource info", response = UserModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Read", response = MobileResource.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    UserModel getUser(@ApiParam(value = "Id of the user to read", required = true) Long id);

    @ApiOperation(value = "", nickname = "userList", notes = "get all users", response = UserListResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Read", response = UserListResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    UserListResponse getAllUsers();


    @ApiOperation(value = "", nickname = "saveUser", notes = "add new user", response = UserModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Resource created", response = UserModel.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    UserModel addUser(@ApiParam(value = "User to be added", required = true) AddUser addUser);


    @ApiOperation(value = "", nickname = "updateUser", notes = "update an user", response = UserModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource Updated", response = UserModel.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    UserModel updateUser(@ApiParam(value = "User to be updated", required = true) UpdateUser updateUser);

    @ApiOperation(value = "", nickname = "deleteUser", notes = "delete a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource delete"),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiResponseError.class)})
    void deleteUser(@ApiParam(value = "id of the user to be deleted", required = true) Long id);
}
