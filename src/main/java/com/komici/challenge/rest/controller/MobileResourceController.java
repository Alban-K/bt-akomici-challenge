package com.komici.challenge.rest.controller;

import com.komici.challenge.rest.api.MobileResourceApi;
import com.komici.challenge.rest.model.resource.AddMobileResource;
import com.komici.challenge.rest.model.resource.MobileResourceModel;
import com.komici.challenge.rest.model.resource.MobileResourceListResponse;

import com.komici.challenge.rest.model.resource.UpdateMobileResource;
import com.komici.challenge.service.resource.MobileResourceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class MobileResourceController implements MobileResourceApi {

    private final Logger log = LoggerFactory.getLogger(MobileResourceController.class);

    final MobileResourceService mobileResourceService;

    public MobileResourceController(MobileResourceService mobileResourceService) {
        this.mobileResourceService = mobileResourceService;
    }

    @GetMapping(value = "/resources")
    public MobileResourceListResponse getAllMobileResources() {

        log.info("getAllMobileResources:");

        return mobileResourceService.getAllMobileResources();
    }

    @Override
    @GetMapping(value = "/resource/{id}", produces = APPLICATION_JSON_VALUE)
    public MobileResourceModel getMobileResource(@PathVariable @NotNull @Min(0) final Long id) {

        log.info("getMobileResource: id={}", id);


        return mobileResourceService.getMobileResource(id);
    }

    @Override
    @PostMapping(value = "/resource/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MobileResourceModel addMobileResource(@NotNull @Valid @RequestBody AddMobileResource addMobileResource) {

        log.info("addMobileResource {}", addMobileResource);

        return mobileResourceService.saveMobileResource(addMobileResource);
    }

    @Override
    @PutMapping(value = "/resource/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public MobileResourceModel updateMobileResource(@RequestBody @NotNull @Valid UpdateMobileResource updateMobileResource) {

        log.info("updateMobileResource {}", updateMobileResource);

        return mobileResourceService.updateMobileResource(updateMobileResource);
    }

    @Override
    @DeleteMapping(value = "/resource/delete/{resourceId}")
    public void deleteMobileResource(@PathVariable Long resourceId) {

        log.info("deleteMobileResource with id={}", resourceId);

        mobileResourceService.deleteMobileResource(resourceId);
    }

    @PutMapping(value = "/resource/book/{resourceId}/user/{userId}")
    public MobileResourceModel bookResource(@PathVariable Long resourceId, @PathVariable Long userId) {

        log.info("bookResource with resourceId={}, for userId={}", resourceId, userId);

        return mobileResourceService.bookResource(resourceId, userId);
    }

    @Override
    @PutMapping(value = "/resource/checkout/{resourceId}")
    public MobileResourceModel checkoutResource(@PathVariable Long resourceId) {

        log.info("checkoutResource with id={}", resourceId);

        return mobileResourceService.checkoutResource(resourceId);
    }

}
