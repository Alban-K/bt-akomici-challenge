package com.komici.challenge.rest.controller;

import com.komici.challenge.persistence.ResourceRepository;
import com.komici.challenge.rest.api.MobileResourceApi;
import com.komici.challenge.rest.model.MobileResource;
import com.komici.challenge.service.MobileResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class MobileResourceController implements MobileResourceApi
{

	final ResourceRepository resourceRepository;
	final MobileResourceService mobileResourceService;

	public MobileResourceController(ResourceRepository resourceRepository, MobileResourceService mobileResourceService) {
		this.resourceRepository = resourceRepository;
		this.mobileResourceService = mobileResourceService;
	}

	@GetMapping(value = "/resources")
	public ResponseEntity<List<MobileResource>> getAllMobileResources() {

		ArrayList<MobileResource> tutorials = new ArrayList<>();
		tutorials.add(new MobileResource("name", "desc", true));
		tutorials.add(new MobileResource("name2", "desc2", false));
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/resource/{id}",  produces = APPLICATION_JSON_VALUE)
	public MobileResource getMobileResource(@PathVariable final Long id) {


		MobileResource mobileResource = mobileResourceService.getMobileResource(id);

		return mobileResource;
	}

}
