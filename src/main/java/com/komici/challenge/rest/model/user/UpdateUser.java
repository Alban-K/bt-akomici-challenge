package com.komici.challenge.rest.model.user;


import java.io.Serializable;

public class UpdateUser extends AddUser implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
