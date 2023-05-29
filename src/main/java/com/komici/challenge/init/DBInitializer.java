package com.komici.challenge.init;

import com.komici.challenge.persistence.resource.MobileResourceEntity;

import com.komici.challenge.persistence.resource.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test-h2-db", "dev"})
public class DBInitializer {

    private final Logger log = LoggerFactory.getLogger(DBInitializer.class);

    private final ResourceRepository resourceRepository;

    @Autowired
    public DBInitializer(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;

        log.info("Inserting default mobile resources");

        initMobileResources();
    }

    private void initMobileResources() {

        String[] mobileNames = {"Samsung Galaxy S9", "2x Samsung Galaxy S8", "Motorola Nexus 6", "Oneplus 9",
                "Apple iPhone 13", "Apple iPhone 12", "Apple iPhone 11", "iPhone X", "Nokia 3310"};

        for (String mobileName : mobileNames) {

            MobileResourceEntity mobileResource = new MobileResourceEntity(mobileName);
            resourceRepository.save(mobileResource);
        }
    }

}

