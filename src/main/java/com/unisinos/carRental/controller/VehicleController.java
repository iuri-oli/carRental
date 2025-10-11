package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/vehicles")
@Tag(name = "Vehicle", description = "Endpoints para gerenciar veículos (sem banco de dados)")
public class VehicleController {

    // "Banco de dados" em memória
    private final Map<Long, Map<String, Object>> vehicles = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todos os veículos", description = "Retorna todos os veículos em memória")
    public List<Map<String, Object>> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID", description = "Retorna um veículo específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Map<String, Object> getVehicleById(
            @Parameter(description = "ID do veículo a ser buscado", required = true)
            @PathVariable Long id) {

        Map<String, Object> vehicle = vehicles.get(id);
        if (vehicle == null) {
            return Map.of("erro", "Veículo não encontrado");
        }
        return vehicle;
    }

    @PostMapping
    @Operation(summary = "Criar um novo veículo", description = "Adiciona um novo veículo ao sistema (em memória)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Veículo criado com sucesso")
    })
    public Map<String, Object> createVehicle(
            @Parameter(description = "Dados do veículo a ser criado", required = true)
            @RequestBody Map<String, Object> vehicle) {

        long id = nextId++;
        vehicle.put("id", id);
        vehicles.put(id, vehicle);
        return vehicle;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veículo", description = "Atualiza os dados de um veículo existente (em memória)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Map<String, Object> updateVehicle(
            @Parameter(description = "ID do veículo a ser atualizado", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do veículo", required = true)
            @RequestBody Map<String, Object> vehicleDetails) {

        Map<String, Object> existing = vehicles.get(id);
        if (existing == null) {
            return Map.of("erro", "Veículo não encontrado");
        }

        existing.putAll(vehicleDetails);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veículo", description = "Remove um veículo pelo ID (em memória)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Map<String, Object> deleteVehicle(
            @Parameter(description = "ID do veículo a ser deletado", required = true)
            @PathVariable Long id) {

        if (vehicles.remove(id) != null) {
            return Map.of("mensagem", "Veículo removido com sucesso");
        } else {
            return Map.of("erro", "Veículo não encontrado");
        }
    }
}
