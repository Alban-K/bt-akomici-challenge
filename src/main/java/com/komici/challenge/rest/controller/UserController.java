package com.komici.challenge.rest.controller;

import com.komici.challenge.rest.api.UserApi;
import com.komici.challenge.rest.model.user.AddUser;
import com.komici.challenge.rest.model.user.UpdateUser;
import com.komici.challenge.rest.model.user.UserListResponse;
import com.komici.challenge.rest.model.user.UserModel;
import com.komici.challenge.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class UserController implements UserApi {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public UserListResponse getAllUsers() {

        log.info("getAllUsers:");

        return userService.getAllUsers();
    }

    @Override
    @GetMapping(value = "/user/{id}", produces = APPLICATION_JSON_VALUE)
    public UserModel getUser(@PathVariable @NotNull @Min(0) final Long id) {

        log.info("getUser: id={}", id);


        return userService.getUser(id);
    }

    @Override
    @PostMapping(value = "/user/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel addUser(@NotNull @Valid @RequestBody AddUser addUser) {

        log.info("addUser {}", addUser);

        return userService.saveUser(addUser);
    }

    @Override
    @PutMapping(value = "/user/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserModel updateUser(@RequestBody @NotNull @Valid UpdateUser updateUser) {

        log.info("updateUser {}", updateUser);

        return userService.updateUser(updateUser);
    }


    @Override
    @DeleteMapping(value = "/user/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {

        log.info("deleteUser with id={}", userId);

        userService.deleteUser(userId);
    }

}
