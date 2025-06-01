package com.entertainment.port.outgoing.db.repository;

import com.entertainment.port.outgoing.db.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<RatingEntity, UUID> {
}
