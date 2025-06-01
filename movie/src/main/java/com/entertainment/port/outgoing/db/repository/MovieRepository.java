package com.entertainment.port.outgoing.db.repository;

import com.entertainment.port.outgoing.db.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {
}
