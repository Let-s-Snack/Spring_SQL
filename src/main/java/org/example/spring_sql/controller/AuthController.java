package org.example.spring_sql.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AdministratorService administratorService;

    //Inicializando a chave secreta em tempo de execução
    private final SecretKey secretKey; //Chave secreta segura
    public AuthController(AdministratorService administratorService, SecretKey secretKey){
        this.administratorService = administratorService;
        this.secretKey = secretKey;
    }

    @PostMapping("/login")
    @Operation(summary = "Gerar token de autenticação", description = "Faz a geração do token JWT de autenticação")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "O token foi gerado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyLnRlc3RlQGdtYWlsLmNvbSIsInJvbGVzIjpbXSwiZXhwIjoxNzMwMjUwMzA2fQ.P_QJxfbLxzOdOdad6nCuiJQbzxBVsmgycu7gMPrLdoOnSGl_W_4VWaPUAGubTyNQVv5v7LqVNlkDXYBBXUKc9A"))),
            @ApiResponse(responseCode = "404", description = "Endpoint não foi encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Endpoint não foi encontrado"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Erro interno no servidor"))),
    })
    public ResponseEntity<?> login(@RequestBody @Parameter(description = "Dados do administrador para login")
                                       @Schema(description = "Objeto contendo o e-mail e senha do administrador", example = "{\"email\": \"user.teste@gmail.com\", \"password\": \"12345\"}") Map<String, String> objectAdm) {
        if (objectAdm.containsKey("email") && objectAdm.containsKey("password")) {
            String email = objectAdm.get("email");
            String password = objectAdm.get("password");

            Administrator adm = administratorService.findAdministratorByEmail(email);

            if (adm != null && checkPassword(password, adm.getPassword())) {
                try {
                    String token = Jwts.builder()
                            .setSubject(adm.getEmail())
                            .claim("roles", Collections.emptyList())
                            .setExpiration(new Date(System.currentTimeMillis() + 180_000)) // Token válido por 3 minutos
                            .signWith(secretKey, SignatureAlgorithm.HS512) //Usa a chave secreta para assinar
                            .compact();

                    return ResponseEntity.ok(token);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o Token JWT");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("E-mail ou senha inválidos");
            }
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Valores foram inseridos incorretamente!") ;
        }
    }

    // Verifica se a senha corresponde ao hash
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
