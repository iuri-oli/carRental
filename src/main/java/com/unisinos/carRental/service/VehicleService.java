package com.unisinos.carRental.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unisinos.carRental.model.Vehicle;
import com.unisinos.carRental.repository.VehicleRepository;


@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle getOne(UUID id) {
        return vehicleRepository.findById(id)
            .orElse(null);
    }

    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void delete(UUID id) {
        vehicleRepository.deleteById(id);
    }
}
