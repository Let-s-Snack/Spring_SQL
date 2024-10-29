package org.example.spring_sql.service;

import org.example.spring_sql.model.Administrator;
import org.example.spring_sql.repository.AdministratorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdmCustomDetailsService implements UserDetailsService {
    private final AdministratorRepository administratorRepository;

    public AdmCustomDetailsService(AdministratorRepository administratorRepository){
        this.administratorRepository = administratorRepository;
    }

    @Override//Método para retornar verificar se o adm está cadastrado ou não (username = email)
    public UserDetails loadUserByUsername(String email){
        Administrator administrator = administratorRepository.findAdministratorByEmailAndIsDeletedIsFalse(email);

        // Retornar uma implementação de UserDetails
        return new org.springframework.security.core.userdetails.User(
                administrator.getEmail(),
                administrator.getPassword(), // Senha criptografada
                true,
                true,
                true,
                true,
                Collections.emptyList()
        );
    }
}
