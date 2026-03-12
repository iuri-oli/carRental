package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.Rental;
import com.unisinos.carRental.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rental", description = "Operações básicas para gerenciamento de locações")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // GET /rentals
    @GetMapping
    @Operation(
        summary = "Listar todas as locações",
        description = "Obtém a lista de todas as locações registradas"
    )
    @ApiResponse(responseCode = "200", description = "Lista de locações retornada com sucesso")
    public List<Rental> getAllRentals() {
        return rentalService.getAll();
    }

    // GET /rentals/{id}
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar locação por ID",
        description = "Retorna uma locação específica pelo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação encontrada"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public Rental getRentalById(
            @Parameter(description = "UUID da locação", required = true)
            @PathVariable UUID id) {

        return rentalService.getOne(id);
    }

    // POST /rentals
    @PostMapping
    @Operation(
        summary = "Criar nova locação",
        description = "Registra uma nova locação no sistema"
    )
    @ApiResponse(responseCode = "201", description = "Locação criada com sucesso")
    public void createRental(@RequestBody Rental rental) {
        rentalService.save(rental);
    }

    // PUT /rentals
    @PutMapping
    @Operation(
        summary = "Atualizar locação",
        description = "Atualiza os dados de uma locação existente"
    )
    @ApiResponse(responseCode = "200", description = "Locação atualizada com sucesso")
    public void updateRental(@RequestBody Rental rental) {
        rentalService.save(rental);
    }

    // DELETE /rentals/{id}
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir locação",
        description = "Remove uma locação existente pelo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public void deleteRental(
            @Parameter(description = "UUID da locação a ser excluída")
            @PathVariable UUID id) {

        rentalService.delete(id);
    }

}
