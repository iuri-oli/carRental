package com.unisinos.carRental.controller;

import com.unisinos.carRental.model.MaintenanceRecord;
import com.unisinos.carRental.service.MaintenanceRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/maintenance-records")
@Tag(name = "MaintenanceRecord", description = "Operações básicas para gerenciamento de registros de manutenção")
public class MaintenanceRecordController {

    @Autowired
    private MaintenanceRecordService maintenanceRecordService;

    // GET /maintenance-records
    @GetMapping
    @Operation(summary = "Listar todos os registros de manutenção",
               description = "Obtém a lista de todos os registros de manutenção cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de registros retornada com sucesso")
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordService.getAll();
    }

    // GET /maintenance-records/{id}
    @GetMapping("/{id}")
    @Parameters({
	@Parameter(name = "id", description = "UUID da Manutenção")
    })
    @Operation(summary = "Buscar registro de manutenção por ID",
               description = "Retorna um registro de manutenção específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    public MaintenanceRecord getMaintenanceRecordById(
        @Parameter(name = "ID", description = "ID do registro de manutenção a ser buscado", required = true)
        @PathVariable UUID id) {

        return maintenanceRecordService.getOne(id);
    }

    // POST /maintenance-records
    @PostMapping
    @Operation(summary = "Criar novo registro de manutenção",
               description = "Registra um novo serviço de manutenção no sistema")
    @ApiResponse(responseCode = "201", description = "Registro de manutenção criado com sucesso")
    public void createMaintenanceRecord(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de registro de manutenção a ser criado", required = true)
            @RequestBody MaintenanceRecord maintenanceRecord) {

        maintenanceRecordService.save(maintenanceRecord);
    }

    // PUT /maintenance-records
    @PutMapping
    @Operation(summary = "Atualizar registro de manutenção",
               description = "Atualiza os dados de um registro de manutenção existente")
    @ApiResponse(responseCode = "200", description = "Registro de manutenção atualizado com sucesso")
    public void updateMaintenanceRecord(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto de registro de manutenção a ser atualizado", required = true)
            @RequestBody MaintenanceRecord maintenanceRecord) {

        maintenanceRecordService.save(maintenanceRecord);
    }

    // DELETE /maintenance-records/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de manutenção",
               description = "Remove um registro de manutenção existente pelo ID")
    @Parameters({
        @Parameter(name = "id", description = "ID do registro a ser excluído", example = "1")
    })
    @ApiResponse(responseCode = "200", description = "Registro de manutenção excluído com sucesso")
    public void deleteMaintenanceRecord(@PathVariable UUID id) {
        maintenanceRecordService.delete(id);
    }
}
