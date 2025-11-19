package com.unisinos.carRental.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unisinos.carRental.model.MaintenanceRecord;
import com.unisinos.carRental.repository.MaintenanceRecordRepository;


@Service
public class MaintenanceRecordService {

    @Autowired
    private MaintenanceRecordRepository maintenanceRecordRepository;

    public List<MaintenanceRecord> getAll() {
        return maintenanceRecordRepository.findAll();
    }

    public MaintenanceRecord getOne(UUID id) {
        return maintenanceRecordRepository.findById(id)
            .orElse(null);
    }

    public void save(MaintenanceRecord maintenanceRecord) {
        maintenanceRecordRepository.save(maintenanceRecord);
    }

    public void delete(UUID id) {
        maintenanceRecordRepository.deleteById(id);
    }
}
