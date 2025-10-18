package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rental", description = "Endpoints para gerenciar locações")
public class RentalController {

    
    private final Map<Long, Map<String, Object>> rentals = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todas as locações", description = "Retorna todas as locações armazenadas")
    public List<Map<String, Object>> getAllRentals() {
        return new ArrayList<>(rentals.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar locação por ID", description = "Retorna uma locação específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação encontrada"),
        @ApiResponse(responseCode = "404", description = "Locação não encontrada")
    })
    public Map<String, Object> getRentalById(
            @Parameter(description = "ID da locação a ser buscada", required = true)
            @PathVariable Long id) {
        return rentals.getOrDefault(id, Map.of("erro", "Locação não encontrada"));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova locação", description = "Adiciona uma nova locação ao sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Locação criada com sucesso")
    })
    public Map<String, Object> createRental(
            @Parameter(description = "Dados da locação a ser criada", required = true)
            @RequestBody Map<String, Object> body) {

        long id = nextId++;
        body.put("id", id);
        rentals.put(id, body);
        return body;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar locação", description = "Atualiza os dados de uma locação existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Locação não encontrada")
    })
    public Map<String, Object> updateRental(
            @Parameter(description = "ID da locação a ser atualizada", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados da locação", required = true)
            @RequestBody Map<String, Object> updates) {

        Map<String, Object> existing = rentals.get(id);
        if (existing == null) {
            return Map.of("erro", "Locação não encontrada");
        }

        existing.putAll(updates);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar locação", description = "Remove uma locação pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Locação não encontrada")
    })
    public Map<String, Object> deleteRental(
            @Parameter(description = "ID da locação a ser deletada", required = true)
            @PathVariable Long id) {

        if (rentals.remove(id) != null) {
            return Map.of("mensagem", "Locação removida com sucesso");
        } else {
            return Map.of("erro", "Locação não encontrada");
        }
    }
}

