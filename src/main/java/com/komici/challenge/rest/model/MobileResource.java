package com.komici.challenge.rest.model;


import java.io.Serializable;
import java.util.Objects;

public class MobileResource implements Serializable {

  private long id;
  private String name;
  private String description;

  private boolean booked;

  public MobileResource() {
  }

  public MobileResource(String name, String description, boolean booked) {
    this.name = name;
    this.description = description;
    this.booked = booked;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public boolean isBooked() {
    return booked;
  }

  public void setBooked(boolean booked) {
    this.booked = booked;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MobileResource resource = (MobileResource) o;
    return id == resource.id && booked == resource.booked && Objects.equals(name, resource.name) && Objects.equals(description, resource.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, booked);
  }
}
