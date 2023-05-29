package com.komici.challenge.service.user;

import com.komici.challenge.rest.model.user.AddUser;
import com.komici.challenge.rest.model.user.UpdateUser;
import com.komici.challenge.rest.model.user.UserListResponse;
import com.komici.challenge.rest.model.user.UserModel;

public interface UserService {

    UserModel getUser(Long id);

    UserListResponse getAllUsers();

    UserModel saveUser(AddUser addUser);

    UserModel updateUser(UpdateUser updateUser);

    void deleteUser(Long id);
}
