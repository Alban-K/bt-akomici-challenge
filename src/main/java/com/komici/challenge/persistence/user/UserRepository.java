package com.komici.challenge.persistence.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByEnable(boolean active);

    List<UserEntity> findByNameContaining(String title);
}
