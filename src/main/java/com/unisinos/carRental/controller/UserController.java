package com.unisinos.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Endpoints para gerenciar usuários")
public class UserController {

    
    private final Map<Long, Map<String, Object>> users = new HashMap<>();
    private long nextId = 1;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna todos os usuários")
    public List<Map<String, Object>> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public Map<String, Object> getUserById(
            @Parameter(description = "ID do usuário a ser buscado", required = true)
            @PathVariable Long id) {

        return users.getOrDefault(id, Map.of("erro", "Usuário não encontrado"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Adiciona um novo usuário ao sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    })
    public Map<String, Object> createUser(
            @Parameter(description = "Dados do usuário a ser criado", required = true)
            @RequestBody Map<String, Object> user) {

        long id = nextId++;
        user.put("id", id);
        users.put(id, user);
        return user;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public Map<String, Object> updateUser(
            @Parameter(description = "ID do usuário a ser atualizado", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do usuário", required = true)
            @RequestBody Map<String, Object> updates) {

        Map<String, Object> existing = users.get(id);
        if (existing == null) {
            return Map.of("erro", "Usuário não encontrado");
        }

        existing.putAll(updates);
        existing.put("id", id);
        return existing;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public Map<String, Object> deleteUser(
            @Parameter(description = "ID do usuário a ser deletado", required = true)
            @PathVariable Long id) {

        if (users.remove(id) != null) {
            return Map.of("mensagem", "Usuário removido com sucesso");
        } else {
            return Map.of("erro", "Usuário não encontrado");
        }
    }
}

