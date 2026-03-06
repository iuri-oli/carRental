package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.Customer;
import com.unisinos.carRental.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "Operações básicas para gerenciamento de clientes")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // GET /customers
    @GetMapping
    @Operation(summary = "Listar todos os clientes",
               description = "Obtém a lista de todos os clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    public List<Customer> getAllCustomers() {
         return customerService.getAll(); 
    }

    // GET /customers/{id}
    @GetMapping("/{id}")
    @Parameters({
	@Parameter(name = "id", description = "UUID do cliente")
    })
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public Customer getCustomerById(
        @Parameter(name = "ID", description = "ID do cliente a ser buscado", required = true)
        @PathVariable UUID id) {

        return customerService.getOne(id); 
    }

    // POST /customers
    @PostMapping
    @Operation(summary = "Criar novo cliente",
               description = "Registra um novo cliente no sistema")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    public void createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de cliente a ser criado", required = true)
            @RequestBody Customer customer) {

        customerService.save(customer);
    }

    // PUT /customers
    @PutMapping
    @Operation(summary = "Atualizar cliente",
               description = "Atualiza os dados de um cliente existente")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    public void updateCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de cliente a ser atualizado", required = true)
            @RequestBody Customer customer) {

        customerService.save(customer);
    }

    // DELETE /customers/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente",
               description = "Remove um cliente existente pelo ID")
    @Parameters({
        @Parameter(name = "id", description = "ID do cliente a ser excluído", example = "1")
    })
    @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso")
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.delete(id);
    }
}
