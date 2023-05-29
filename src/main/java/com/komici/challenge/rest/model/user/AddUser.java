package com.komici.challenge.rest.model.user;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AddUser implements Serializable {


  @ApiModelProperty(value = "The username", required = true)
  @NotBlank
  @Size(max = 20)
  private String username;

  @ApiModelProperty(value = "Surname of the user", required = true)
  @NotBlank
  @Size(max = 70)
  private String surname;

  @ApiModelProperty(value = "Name of the user", required = true)
  @NotBlank
  @Size(max = 70)
  private String firstname;

  @ApiModelProperty(value = "Email of the user", required = true)
  @NotBlank
  @Size(max = 70)
  @Email
  private String email;

  @ApiModelProperty(value = "Indicate if the user is enabled", required = true)
  private boolean enable;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }
}
