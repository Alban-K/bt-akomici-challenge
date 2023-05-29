package com.komici.challenge.service;

import com.komici.challenge.exception.BTNoEntityFoundException;
import com.komici.challenge.persistence.MobileResourceEntity;
import com.komici.challenge.persistence.ResourceRepository;
import com.komici.challenge.rest.model.MobileResource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service(value = "mobileResourceService")
public class MobileResourceServiceImpl implements MobileResourceService
{

    private final ResourceRepository resourceRepository;

    public MobileResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public MobileResource getMobileResource(Long id) {

        Optional<MobileResourceEntity> resourceEntityOpt = resourceRepository.findById(id);

        if (!resourceEntityOpt.isPresent()) {
            throw new BTNoEntityFoundException(id);
        }

        return convertToModel(resourceEntityOpt.get());
    }

    private static MobileResource convertToModel(MobileResourceEntity resourceEntity) {
        MobileResource mobileResource = new MobileResource();
        mobileResource.setId(resourceEntity.getId());
        mobileResource.setName(resourceEntity.getName());
        mobileResource.setDescription(resourceEntity.getDescription());
        mobileResource.setBooked(resourceEntity.isBooked());

        return mobileResource;
    }
}
