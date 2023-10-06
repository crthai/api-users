package com.example.service;

import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final static Logger logger = Logger.getLogger(UsuarioService.class.getName());

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    };

    public Usuario criarUsuario(Usuario usuario){
        Optional<Usuario> optionalUsuario = buscaCPF(usuario.getCpf());
        if(optionalUsuario.isPresent()){
            logger.log(Level.WARNING,  "Usuário já existe com este CPF " + usuario.getCpf());
            return null;
        }
        usuario.setId(null);
        return repository.save(usuario);
    };

    public Optional<Usuario> buscaCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
