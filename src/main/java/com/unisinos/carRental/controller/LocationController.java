package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/locations")
@Tag(name = "Location", description = "Endpoints para gerenciar localizações (sem banco de dados)")
public class LocationController {

    // Armazenamento em memória
    private final Map<Long, Map<String, Object>> locations = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todas as localizações", description = "Retorna todas as localizações armazenadas em memória")
    public List<Map<String, Object>> getAllLocations() {
        return new ArrayList<>(locations.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar localização por ID", description = "Retorna uma localização específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Localização encontrada"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    public Map<String, Object> getLocationById(
            @Parameter(description = "ID da localização a ser buscada", required = true)
            @PathVariable Long id) {
        return locations.getOrDefault(id, Map.of("erro", "Localização não encontrada"));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova localização", description = "Adiciona uma nova localização em memória")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Localização criada com sucesso")
    })
    public Map<String, Object> createLocation(
            @Parameter(description = "Dados da localização a ser criada", required = true)
            @RequestBody Map<String, Object> body) {
        long id = nextId++;
        body.put("id", id);
        locations.put(id, body);
        return body;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar localização", description = "Atualiza os dados de uma localização existente em memória")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    public Map<String, Object> updateLocation(
            @Parameter(description = "ID da localização a ser atualizada", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados da localização", required = true)
            @RequestBody Map<String, Object> updates) {

        Map<String, Object> existing = locations.get(id);
        if (existing == null) {
            return Map.of("erro", "Localização não encontrada");
        }

        existing.putAll(updates);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar localização", description = "Remove uma localização pelo ID (em memória)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Localização deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    public Map<String, Object> deleteLocation(
            @Parameter(description = "ID da localização a ser deletada", required = true)
            @PathVariable Long id) {
        if (locations.remove(id) != null) {
            return Map.of("mensagem", "Localização removida com sucesso");
        } else {
            return Map.of("erro", "Localização não encontrada");
        }
    }
}


