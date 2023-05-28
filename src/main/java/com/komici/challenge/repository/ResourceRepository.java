package com.komici.challenge.repository;

import java.util.List;


import com.komici.challenge.model.MobileResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<MobileResource, Long> {
  List<MobileResource> findByBooked(boolean published);

  List<MobileResource> findByNameContaining(String title);
}
