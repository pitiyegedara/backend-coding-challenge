package com.entertainment.port.outgoing.db.repository;

import com.entertainment.port.outgoing.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUserName(String username);

    Optional<UserEntity> findByEmail(String email);


}
