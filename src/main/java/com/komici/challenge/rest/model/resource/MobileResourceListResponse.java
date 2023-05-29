package com.komici.challenge.rest.model.resource;


import java.io.Serializable;
import java.util.List;

public class MobileResourceListResponse implements Serializable {

    List<MobileResourceModel> mobileResources;

    public List<MobileResourceModel> getMobileResources() {
        return mobileResources;
    }

    public void setMobileResources(List<MobileResourceModel> mobileResources) {
        this.mobileResources = mobileResources;
    }
}
