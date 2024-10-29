package org.example.spring_sql.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.spring_sql.model.Administrator;
import org.example.spring_sql.service.AdministratorService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    private final AdministratorService administratorService;

    public AdministratorController(AdministratorService administratorService){
        this.administratorService = administratorService;
    }

    //Método para listar todos os administradores
    @GetMapping("/findAll")
    @Operation(summary = "Lista os Administradores", description = "Lista todos os Administradores")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Os administradores foram retornados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrator.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Endpoint não foi encontrado"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Erro interno no servidor"))),
    })
    public ResponseEntity<?> findAllAdminstrators(){
        try{
            return ResponseEntity.ok(administratorService.findAllAdministrators());
        } catch (HttpServerErrorException.InternalServerError ise) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível encontrar os administradores!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor.");
        }
    }

    //Método para listar os administrador pelo id
    @GetMapping("/findAdministratorById/{id}")
    @Operation(summary = "Listar Administrador pelo ID", description = "Faz a busca do administrador a partir do ID")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "O administrador foi encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrator.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Endpoint não foi encontrado"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Erro interno no servidor"))),
    })
    public ResponseEntity<?> findAdministratorById(@Parameter(description = "ID do usuário", example = "1") @PathVariable Integer id){
        try{
            return ResponseEntity.ok(administratorService.findAdministratorById(id));
        } catch (HttpServerErrorException.InternalServerError ise) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível encontrar os administradores!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor.");
        }
    }

    //Método para listar os administrador pelo nome
    @GetMapping("/findAdministratorByName/{name}")
    @Operation(summary = "Listar Administrador pelo nome", description = "Faz a busca do administrador a partir do nome")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "O administrador foi encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrator.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Endpoint não foi encontrado"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Erro interno no servidor"))),
    })
    public ResponseEntity<?> findAdministratorByName(@Parameter(description = "Nome do usuário", example = "Yudi") @PathVariable String name){
        try{
            return ResponseEntity.ok(administratorService.findAdministratorByName(name));
        } catch (HttpServerErrorException.InternalServerError ise) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível encontrar os administradores!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor.");
        }
    }

    //Método para listar os administrador pelo e-mail e senha
    @GetMapping("/findAdministratorByEmailAndPassword")
    @Operation(summary = "Listar Administrador pelo e-mail e senha", description = "Faz a busca do administrador a partir do seu e-mail e senha")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "O administrador foi encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrator.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Endpoint não foi encontrado"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Erro interno no servidor"))),
    })
    public ResponseEntity<?> findAdministratorByEmailAndPassword(@Parameter(description = "E-mail do administrador", example = "enzo.hino@germinare.org.br") @RequestParam String email, @Parameter(description = "Senha do usuário", example = "123") @RequestParam String password){
        try{
            Administrator administrator = administratorService.findAdministratorByEmail(email);

            if (administrator != null) {
                if (checkPassword(password, administrator.getPassword())) {
                    return ResponseEntity.ok(administrator);
                } else {
                    return ResponseEntity.ok("Usuário ou senha está incorreta");
                }
            } else {
                return ResponseEntity.ok("Não foi possível encontrar o administrador");
            }
        } catch (HttpServerErrorException.InternalServerError ise) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível encontrar os administradores!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor.");
        }
    }



    // Verifica se a senha corresponde ao hash
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
