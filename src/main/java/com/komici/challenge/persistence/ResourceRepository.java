package com.komici.challenge.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<MobileResourceEntity, Long> {
  List<MobileResourceEntity> findByBooked(boolean published);

  List<MobileResourceEntity> findByNameContaining(String title);
}
