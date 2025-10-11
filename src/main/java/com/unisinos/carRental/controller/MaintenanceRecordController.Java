package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/maintenance-records")
@Tag(name = "MaintenanceRecord", description = "Endpoints para gerenciar registros de manutenção (sem banco de dados)")
public class MaintenanceRecordController {

    // "Banco" em memória
    private final Map<Long, Map<String, Object>> maintenanceRecords = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todos os registros de manutenção", description = "Retorna todos os registros de manutenção armazenados em memória")
    public List<Map<String, Object>> getAllRecords() {
        return new ArrayList<>(maintenanceRecords.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro de manutenção por ID", description = "Retorna um registro específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public Map<String, Object> getRecordById(
            @Parameter(description = "ID do registro de manutenção a ser buscado", required = true)
            @PathVariable Long id) {
        return maintenanceRecords.getOrDefault(id, Map.of("erro", "Registro não encontrado"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo registro de manutenção", description = "Adiciona um novo registro de manutenção em memória")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro criado com sucesso")
    })
    public Map<String, Object> createRecord(
            @Parameter(description = "Dados do registro de manutenção a ser criado", required = true)
            @RequestBody Map<String, Object> body) {
        long id = nextId++;
        body.put("id", id);
        maintenanceRecords.put(id, body);
        return body;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de manutenção", description = "Atualiza os dados de um registro de manutenção existente em memória")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public Map<String, Object> updateRecord(
            @Parameter(description = "ID do registro a ser atualizado", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do registro de manutenção", required = true)
            @RequestBody Map<String, Object> updates) {

        Map<String, Object> existing = maintenanceRecords.get(id);
        if (existing == null) {
            return Map.of("erro", "Registro não encontrado");
        }

        existing.putAll(updates);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar registro de manutenção", description = "Remove um registro de manutenção pelo ID (em memória)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public Map<String, Object> deleteRecord(
            @Parameter(description = "ID do registro de manutenção a ser deletado", required = true)
            @PathVariable Long id) {
        if (maintenanceRecords.remove(id) != null) {
            return Map.of("mensagem", "Registro de manutenção removido com sucesso");
        } else {
            return Map.of("erro", "Registro não encontrado");
        }
    }
}

