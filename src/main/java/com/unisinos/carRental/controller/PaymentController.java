package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.Payment;
import com.unisinos.carRental.service.PaymentService;

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
@RequestMapping("/payments")
@Tag(name = "Payment", description = "Operações básicas para gerenciamento de pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // GET /payments
    @GetMapping
    @Operation(summary = "Listar todos os pagamentos",
               description = "Obtém a lista de todos os pagamentos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso")
    public List<Payment> getAllPayments() {
        return paymentService.getAll();
    }

    // GET /payments/{id}
    @GetMapping("/{id}")
    @Parameters({
	@Parameter(name = "id", description = "UUID do Pagamento")
    })
    @Operation(summary = "Buscar pagamento por ID",
               description = "Retorna um pagamento específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public Payment getPaymentById(
        @Parameter(name = "ID", description = "ID do pagamento a ser buscado", required = true)
        @PathVariable UUID id) {

        return paymentService.getOne(id);
    }

    // POST /payments
    @PostMapping
    @Operation(summary = "Criar novo pagamento",
               description = "Registra um novo pagamento no sistema")
    @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso")
    public void createPayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de pagamento a ser criado", required = true)
            @RequestBody Payment payment) {

        paymentService.save(payment);
    }

    // PUT /payments
    @PutMapping
    @Operation(summary = "Atualizar pagamento",
               description = "Atualiza os dados de um pagamento existente")
    @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso")
    public void updatePayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de pagamento a ser atualizado", required = true)
            @RequestBody Payment payment) {

        paymentService.save(payment);
    }

    // DELETE /payments/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pagamento",
               description = "Remove um pagamento existente pelo ID")
    @Parameters({
        @Parameter(name = "id", description = "ID do pagamento a ser excluído", example = "1")
    })
    @ApiResponse(responseCode = "200", description = "Pagamento excluído com sucesso")
    public void deletePayment(@PathVariable UUID id) {
        paymentService.delete(id);
    }
}
