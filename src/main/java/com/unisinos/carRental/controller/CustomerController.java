package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "Endpoints para gerenciar clientes (sem banco de dados)")
public class CustomerController {

    // Simulação de armazenamento em memória
    private final Map<Long, Map<String, Object>> customers = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna todos os clientes em memória")
    public List<Map<String, Object>> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Map<String, Object> getCustomerById(
            @Parameter(description = "ID do cliente a ser buscado", required = true)
            @PathVariable Long id) {

        return customers.getOrDefault(id, Map.of("erro", "Cliente não encontrado"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente", description = "Adiciona um cliente ao mapa em memória")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    })
    public Map<String, Object> createCustomer(
            @Parameter(description = "Dados do cliente", required = true)
            @RequestBody Map<String, Object> body) {

        long id = nextId++;
        body.put("id", id);
        customers.put(id, body);
        return body;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza dados de um cliente no mapa em memória")
    public Map<String, Object> updateCustomer(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Map<String, Object> existing = customers.get(id);
        if (existing == null) {
            return Map.of("erro", "Cliente não encontrado");
        }

        existing.putAll(updates);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente pelo ID (em memória)")
    public Map<String, Object> deleteCustomer(@PathVariable Long id) {
        if (customers.remove(id) != null) {
            return Map.of("mensagem", "Cliente removido com sucesso");
        } else {
            return Map.of("erro", "Cliente não encontrado");
        }
    }
}
