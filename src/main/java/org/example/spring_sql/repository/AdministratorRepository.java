package org.example.spring_sql.repository;

import org.example.spring_sql.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    //Listando o administrador pelo nome
    List<Administrator> findAdministratorsByNameContainingIgnoreCaseAndIsDeletedIsFalse(String name);

    //Listando o adminstrador pelo e-mail e a senha
    Administrator findAdministratorByEmailAndIsDeletedIsFalse(String email);
}
