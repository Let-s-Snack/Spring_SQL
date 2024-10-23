package org.example.spring_sql.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

import java.util.Date;

@Entity
@Table(name = "let_adm")
public class Administrator {
    @Id
    @Column(name = "pk_id")
    @Schema(description = "ID do administrador", example = "1")
    private Integer pkId;

   @Schema(description = "E-mail do administrador", example = "teste@gmail.com")
   @Email(message = "E-mail deve ser válido")
   private String email;

   @Schema(description = "Senha do administrador", example = "@Teste1234")
   private String password;

    @Schema(description = "Nome do administrador", example = "Teste")
    private String name;

    @Schema(description = "Indica se o administrador foi excluido ou não", example = "true")
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Schema(description = "Data de criação do usuário", example = "2024-09-23T16:08:35.144+00:00")
    @Column(name = "creation_date")
    private Date creationDate;

    public Administrator() {}

    public Administrator(Integer pkId, String email, String password, String name, boolean isDeleted, Date creationDate) {
        this.pkId = pkId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
    }

    public Administrator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String toString() {
        return "Administrator{" +
                "pkId=" + pkId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", isDeleted=" + isDeleted +
                ", creationDate=" + creationDate +
                '}';
    }
}
