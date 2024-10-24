package org.example.spring_sql.Repository;

import org.example.spring_sql.Model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    //Listando o administrador pelo nome
    List<Administrator> findAdministratorsByNameContainingIgnoreCaseAndIsDeletedIsFalse(String name);

    //Listando o adminstrador pelo e-mail e a senha
    Administrator findAdministratorByEmailAndIsDeletedIsFalse(String email);
}
