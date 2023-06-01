package com.komici.challenge.rest.model.resource;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AddMobileResource implements Serializable {

    @ApiModelProperty(value = "Name of the mobile resource", required = true)
    @NotBlank
    @Size(max = 40)
    private String name;
    private String description;

    public AddMobileResource(String name) {
        this.name = name;
    }
    public AddMobileResource() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
