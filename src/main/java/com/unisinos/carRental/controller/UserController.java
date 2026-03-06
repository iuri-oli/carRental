package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.User;
import com.unisinos.carRental.service.UserService;

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
@RequestMapping("/users")
@Tag(name = "User", description = "Operações básicas para gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /users
    @GetMapping
    @Operation(summary = "Listar todos os usuários",
               description = "Obtém a lista de todos os usuários registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    @Parameters({
	@Parameter(name = "id", description = "UUID do Usuaário")
    })
    @Operation(summary = "Buscar usuário por ID",
               description = "Retorna um usuário específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public User getUserById(
        @Parameter(name = "ID", description = "ID do usuário a ser buscado", required = true)
        @PathVariable UUID id) {

        return userService.getOne(id);
    }

    // POST /users
    @PostMapping
    @Operation(summary = "Criar novo usuário",
               description = "Registra um novo usuário no sistema")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public void createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de usuário a ser criado", required = true)
            @RequestBody User user) {

        userService.save(user);
    }

    // PUT /users
    @PutMapping
    @Operation(summary = "Atualizar usuário",
               description = "Atualiza os dados de um usuário existente")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    public void updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de usuário a ser atualizado", required = true)
            @RequestBody User user) {

        userService.save(user);
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário",
               description = "Remove um usuário existente pelo ID")
    @Parameters({
        @Parameter(name = "id", description = "ID do usuário a ser excluído", example = "1")
    })
    @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso")
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }
}
