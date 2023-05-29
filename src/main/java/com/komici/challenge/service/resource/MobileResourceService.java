package com.komici.challenge.service.resource;

import com.komici.challenge.rest.model.resource.AddMobileResource;
import com.komici.challenge.rest.model.resource.MobileResourceModel;
import com.komici.challenge.rest.model.resource.MobileResourceListResponse;
import com.komici.challenge.rest.model.resource.UpdateMobileResource;


public interface MobileResourceService {

    MobileResourceModel getMobileResource(Long id);

    MobileResourceListResponse getAllMobileResources();

    MobileResourceModel saveMobileResource(AddMobileResource addMobileResource);

    MobileResourceModel updateMobileResource(UpdateMobileResource updateMobileResource);

    void deleteMobileResource(Long id);

    MobileResourceModel bookResource(Long resourceId, Long userId);

    MobileResourceModel checkoutResource(Long resourceId);
}
