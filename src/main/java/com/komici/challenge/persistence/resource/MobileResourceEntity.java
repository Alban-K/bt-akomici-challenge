package com.komici.challenge.persistence.resource;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class MobileResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "booked")
    private boolean booked;

    public MobileResourceEntity() {
    }

    public MobileResourceEntity(String name, String description, boolean booked) {
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
        MobileResourceEntity that = (MobileResourceEntity) o;
        return id == that.id && booked == that.booked && name.equals(that.name) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, booked);
    }
}
