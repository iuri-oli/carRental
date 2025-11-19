package com.unisinos.carRental.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unisinos.carRental.model.Payment;
import com.unisinos.carRental.repository.PaymentRepository;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getOne(UUID id) {
        return paymentRepository.findById(id)
            .orElse(null);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }
}
