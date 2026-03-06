package com.unisinos.carRental.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisinos.carRental.model.Rental;
import com.unisinos.carRental.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    public Rental getOne(UUID id) {
        return rentalRepository.findById(id)
                .orElse(null);
    }

    public List<Rental> getByCustomer(UUID customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    public List<Rental> getByVehicle(UUID vehicleId) {
        return rentalRepository.findByVehicleId(vehicleId);
    }

    public void save(Rental rental) {
        rentalRepository.save(rental);
    }

    public void delete(UUID id) {
        rentalRepository.deleteById(id);
    }
}
