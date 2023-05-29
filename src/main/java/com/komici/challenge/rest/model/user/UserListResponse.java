package com.komici.challenge.rest.model.user;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserListResponse {


    @JsonProperty("users")
    List<UserModel> userList;

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }
}
