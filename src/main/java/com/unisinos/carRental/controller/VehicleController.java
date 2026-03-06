package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.Vehicle;
import com.unisinos.carRental.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/vehicles")
@Tag(name = "Vehicle", description = "Operações básicas para gerenciamento de veículos")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // GET /vehicles
    @GetMapping
    @Operation(summary = "Listar todos os veículos",
               description = "Obtém a lista de todos os veículos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAll();
    }

    // GET /vehicles/{id}
    @GetMapping("/{id}")
    @Parameters({
	@Parameter(name = "id", description = "UUID do Veículo")
    })
    @Operation(summary = "Buscar veículo por ID",
               description = "Retorna um veículo específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public Vehicle getVehicleById(
        @Parameter(name = "ID", description = "ID do veículo a ser buscado", required = true)
        @PathVariable UUID id) {

        return vehicleService.getOne(id);
    }

    // POST /vehicles
    @PostMapping
    @Operation(summary = "Criar novo veículo",
               description = "Registra um novo veículo no sistema")
    @ApiResponse(responseCode = "201", description = "Veículo criado com sucesso")
    public void createVehicle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de veículo a ser criado", required = true)
            @RequestBody Vehicle vehicle) {

        vehicleService.save(vehicle);
    }

    // PUT /vehicles
    @PutMapping
    @Operation(summary = "Atualizar veículo",
               description = "Atualiza os dados de um veículo existente")
    @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso")
    public void updateVehicle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de veículo a ser atualizado", required = true)
            @RequestBody Vehicle vehicle) {

        vehicleService.save(vehicle);
    }

    // DELETE /vehicles/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir veículo",
               description = "Remove um veículo existente pelo ID")
    @Parameters({
        @Parameter(name = "id", description = "ID do veículo a ser excluído", example = "1")
    })
    @ApiResponse(responseCode = "200", description = "Veículo excluído com sucesso")
    public void deleteVehicle(@PathVariable UUID id) {
        vehicleService.delete(id);
    }
}
