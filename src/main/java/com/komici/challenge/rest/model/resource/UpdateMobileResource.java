package com.komici.challenge.rest.model.resource;


import java.io.Serializable;

public class UpdateMobileResource extends AddMobileResource implements Serializable {

  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
