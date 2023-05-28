package com.komici.challenge.controller;

import com.komici.challenge.model.MobileResource;
import com.komici.challenge.repository.ResourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResourceController {

	final ResourceRepository resourceRepository;

	public ResourceController(ResourceRepository resourceRepository) {
		this.resourceRepository = resourceRepository;
	}

	@GetMapping(value = "/resource")

	public ResponseEntity<List<MobileResource>> getAllMobileResources() {

		ArrayList<MobileResource> tutorials = new ArrayList<>();
		tutorials.add(new MobileResource("name", "desc", true));
		tutorials.add(new MobileResource("name2", "desc2", false));
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

}
