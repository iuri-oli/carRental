package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment", description = "Endpoints para gerenciar pagamentos")
public class PaymentController {

    
    private final Map<Long, Map<String, Object>> payments = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todos os pagamentos", description = "Retorna todos os pagamentos armazenados")
    public List<Map<String, Object>> getAllPayments() {
        return new ArrayList<>(payments.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Retorna um pagamento específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public Map<String, Object> getPaymentById(
            @Parameter(description = "ID do pagamento a ser buscado", required = true)
            @PathVariable Long id) {
        return payments.getOrDefault(id, Map.of("erro", "Pagamento não encontrado"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo pagamento", description = "Adiciona um novo pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso")
    })
    public Map<String, Object> createPayment(
            @Parameter(description = "Dados do pagamento a ser criado", required = true)
            @RequestBody Map<String, Object> body) {
        long id = nextId++;
        body.put("id", id);
        payments.put(id, body);
        return body;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pagamento", description = "Atualiza os dados de um pagamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public Map<String, Object> updatePayment(
            @Parameter(description = "ID do pagamento a ser atualizado", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do pagamento", required = true)
            @RequestBody Map<String, Object> updates) {

        Map<String, Object> existing = payments.get(id);
        if (existing == null) {
            return Map.of("erro", "Pagamento não encontrado");
        }

        existing.putAll(updates);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pagamento", description = "Remove um pagamento pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public Map<String, Object> deletePayment(
            @Parameter(description = "ID do pagamento a ser deletado", required = true)
            @PathVariable Long id) {
        if (payments.remove(id) != null) {
            return Map.of("mensagem", "Pagamento removido com sucesso");
        } else {
            return Map.of("erro", "Pagamento não encontrado");
        }
    }
}


