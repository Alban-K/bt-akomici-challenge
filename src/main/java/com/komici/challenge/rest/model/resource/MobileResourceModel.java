package com.komici.challenge.rest.model.resource;


import java.io.Serializable;

public class MobileResourceModel extends UpdateMobileResource implements Serializable {


    private BookingInfo bookingInfo;

    public BookingInfo getBookingInfo() {
        return bookingInfo;
    }

    public void setBookingInfo(BookingInfo bookingInfo) {
        this.bookingInfo = bookingInfo;
    }
}
