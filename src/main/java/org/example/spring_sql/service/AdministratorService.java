package org.example.spring_sql.service;

import org.example.spring_sql.model.Administrator;
import org.example.spring_sql.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository){
        this.administratorRepository = administratorRepository;
    }

    //Fazendo um método para retornar todos os administradores
    public List<Administrator> findAllAdministrators(){
        return administratorRepository.findAll();
    }

    //Fazendo um método para retornar o administrador pelo id
    public Administrator findAdministratorById(Integer id){
        return administratorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Administrador não encontrado"));
    }

    //Fazendo um método para retornar o administrador pelo nome
    public List<Administrator> findAdministratorByName(String name){
        return administratorRepository.findAdministratorsByNameContainingIgnoreCaseAndIsDeletedIsFalse(name);
    }

    //Fazendo um método para retornar o administrador pelo e-mail e senha
    public Administrator findAdministratorByEmail(String email){
        return administratorRepository.findAdministratorByEmailAndIsDeletedIsFalse(email);
    }

}
