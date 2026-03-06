package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.Location;
import com.unisinos.carRental.service.LocationService;

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
@RequestMapping("/locations")
@Tag(name = "Location", description = "Operações básicas para gerenciamento de locais")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // GET /locations
    @GetMapping
    @Operation(summary = "Listar todos os locais",
               description = "Obtém a lista de todos os locais registrados")
    @ApiResponse(responseCode = "200", description = "Lista de locais retornada com sucesso")
    public List<Location> getAllLocations() {
        return locationService.getAll();
    }

    // GET /locations/{id}
    @GetMapping("/{id}")
    @Parameters({
	@Parameter(name = "id", description = "UUID da Localização")
    })
    @Operation(summary = "Buscar local por ID",
               description = "Retorna um local específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Local encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public Location getLocationById(
        @Parameter(name = "ID", description = "ID do local a ser buscado", required = true)
        @PathVariable UUID id) {

        return locationService.getOne(id);
    }

    // POST /locations
    @PostMapping
    @Operation(summary = "Criar novo local",
               description = "Registra um novo local no sistema")
    @ApiResponse(responseCode = "201", description = "Local criado com sucesso")
    public void createLocation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de local a ser criado", required = true)
            @RequestBody Location location) {

        locationService.save(location);
    }

    // PUT /locations
    @PutMapping
    @Operation(summary = "Atualizar local",
               description = "Atualiza os dados de um local existente")
    @ApiResponse(responseCode = "200", description = "Local atualizado com sucesso")
    public void updateLocation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de local a ser atualizado", required = true)
            @RequestBody Location location) {

        locationService.save(location);
    }

    // DELETE /locations/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir local",
               description = "Remove um local existente pelo ID")
    @Parameters({
        @Parameter(name = "id", description = "ID do local a ser excluído", example = "1")
    })
    @ApiResponse(responseCode = "200", description = "Local excluído com sucesso")
    public void deleteLocation(@PathVariable UUID id) {
        locationService.delete(id);
    }
}
