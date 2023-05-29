package com.komici.challenge.rest.model.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.komici.challenge.rest.model.user.LiteUser;
import com.komici.challenge.rest.model.user.UserModel;

import java.io.Serializable;
import java.util.Date;

public class BookingInfo implements Serializable {

    private boolean isBooked;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Date bookingDate;
    private LiteUser user;

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LiteUser getUser() {
        return user;
    }

    public void setUser(LiteUser user) {
        this.user = user;
    }
}
