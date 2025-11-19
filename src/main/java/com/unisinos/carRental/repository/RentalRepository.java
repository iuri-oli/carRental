package com.unisinos.carRental.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unisinos.carRental.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {

    List<Rental> findByCustomerId(UUID customerId);

    List<Rental> findByVehicleId(UUID vehicleId);
}
