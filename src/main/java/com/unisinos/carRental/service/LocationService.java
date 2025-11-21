package com.unisinos.carRental.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisinos.carRental.model.Location;
import com.unisinos.carRental.repository.LocationRepository;


@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location getOne(UUID id) {
        return locationRepository.findById(id)
            .orElse(null);
    }
    
    public void save(Location location) {
        locationRepository.save(location);
    }

    public void delete(UUID id) {
        locationRepository.deleteById(id);
    }
}
