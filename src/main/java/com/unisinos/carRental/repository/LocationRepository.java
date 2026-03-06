package com.unisinos.carRental.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unisinos.carRental.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {}