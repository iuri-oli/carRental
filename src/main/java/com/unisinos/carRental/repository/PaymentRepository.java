package com.unisinos.carRental.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unisinos.carRental.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {}