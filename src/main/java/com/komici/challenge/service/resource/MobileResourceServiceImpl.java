package com.komici.challenge.service.resource;

import com.komici.challenge.exception.BTNoEntityFoundException;
import com.komici.challenge.exception.BTOperationNotAllowedException;
import com.komici.challenge.persistence.resource.MobileResourceEntity;
import com.komici.challenge.persistence.resource.ResourceRepository;
import com.komici.challenge.persistence.user.UserEntity;
import com.komici.challenge.persistence.user.UserRepository;
import com.komici.challenge.rest.model.resource.*;
import com.komici.challenge.rest.model.user.LiteUser;
import com.komici.challenge.rest.model.user.UserModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service(value = "mobileResourceService")
public class MobileResourceServiceImpl implements MobileResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public MobileResourceServiceImpl(ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MobileResourceModel getMobileResource(Long id) {

        Optional<MobileResourceEntity> resourceEntityOpt = resourceRepository.findById(id);

        if (!resourceEntityOpt.isPresent()) {
            throw new BTNoEntityFoundException(id);
        }

        return convertToModel(resourceEntityOpt.get());
    }

    @Override
    public MobileResourceListResponse getAllMobileResources() {

        List<MobileResourceEntity> resourceEntities = resourceRepository.findAll();

        MobileResourceListResponse mobileResourceListResponse = new MobileResourceListResponse();
        List<MobileResourceModel> resourceList = new ArrayList<>();

        resourceEntities.forEach(resourceEntity -> resourceList.add(convertToModel(resourceEntity)));

        mobileResourceListResponse.setMobileResources(resourceList);

        return mobileResourceListResponse;
    }

    @Override
    public MobileResourceModel saveMobileResource(AddMobileResource addMobileResource) {

        MobileResourceEntity resourceEntity = convertToEntity(addMobileResource);

        MobileResourceEntity savedMobileResource = resourceRepository.save(resourceEntity);

        return convertToModel(savedMobileResource);
    }

    @Override
    public MobileResourceModel updateMobileResource(UpdateMobileResource updateMobileResource) {

        Long resourceId = updateMobileResource.getId();

        if (!resourceRepository.existsById(resourceId)) {
            throw new BTNoEntityFoundException("The requested resource does not exists");
        }

        MobileResourceEntity resourceEntity = resourceRepository.getReferenceById(resourceId);

        resourceEntity.setName(updateMobileResource.getName());
        resourceEntity.setDescription(updateMobileResource.getDescription());

        resourceRepository.save(resourceEntity);

        return convertToModel(resourceEntity);
    }

    @Override
    public void deleteMobileResource(Long resourceId) {

        if (!resourceRepository.existsById(resourceId)) {
            throw new BTNoEntityFoundException("The requested resource does not exists");
        }

        resourceRepository.deleteById(resourceId);
    }

    @Override
    public MobileResourceModel bookResource(Long resourceId, Long userId) {

        if (!resourceRepository.existsById(resourceId) || !userRepository.existsById(userId)) {
            throw new BTNoEntityFoundException("The requested resource or user does not exists");
        }

        MobileResourceEntity resourceEntity = resourceRepository.getReferenceById(resourceId);

        if (resourceEntity.isBooked()) {
            throw new BTOperationNotAllowedException("The requested resource is already booked");
        }

        resourceEntity.setBookingDate(new Date());
        resourceEntity.setBooked(true);
        resourceEntity.setBookedBy(new UserEntity(userId));

        MobileResourceEntity savedResource = resourceRepository.save(resourceEntity);

        return convertToModel(savedResource);
    }

    @Override
    public MobileResourceModel checkoutResource(Long resourceId) {

        if (!resourceRepository.existsById(resourceId)) {
            throw new BTNoEntityFoundException("The requested resource does not exists");
        }

        MobileResourceEntity resourceEntity = resourceRepository.getReferenceById(resourceId);
        resourceEntity.setBookingDate(null);
        resourceEntity.setBooked(false);
        resourceEntity.setBookedBy(null);

        MobileResourceEntity savedResource = resourceRepository.save(resourceEntity);

        return convertToModel(savedResource);
    }

    private static MobileResourceModel convertToModel(MobileResourceEntity resourceEntity) {
        MobileResourceModel mobileResource = new MobileResourceModel();
        mobileResource.setId(resourceEntity.getId());
        mobileResource.setName(resourceEntity.getName());
        mobileResource.setDescription(resourceEntity.getDescription());

        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setBooked(resourceEntity.isBooked());
        bookingInfo.setBookingDate(resourceEntity.getBookingDate());

        if (resourceEntity.isBooked()) {
            LiteUser user = new LiteUser();
            UserEntity userEntity = resourceEntity.getBookedBy();
            //mapping only basic info such as user id, username and email
            user.setId(userEntity.getId());
            user.setUsername(userEntity.getUsername());
            user.setEmail(userEntity.getEmail());
            bookingInfo.setUser(user);
        }

        mobileResource.setBookingInfo(bookingInfo);

        return mobileResource;
    }

    private MobileResourceEntity convertToEntity(AddMobileResource addMobileResource) {

        MobileResourceEntity resourceEntity = new MobileResourceEntity();

        resourceEntity.setName(addMobileResource.getName());
        resourceEntity.setDescription(addMobileResource.getDescription());

        return resourceEntity;
    }
}
