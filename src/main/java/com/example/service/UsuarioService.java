package com.example.service;

import com.example.controller.dtos.UsuarioRequestDTO;
import com.example.exception.UsuarioJaExisteException;
import com.example.exception.UsuarioNotFoundException;
import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    private final static Logger logger = Logger.getLogger(UsuarioService.class.getName());

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    };

    public Usuario criarUsuario(UsuarioRequestDTO usuario){
        Optional<Usuario> optionalUsuario = buscaCPF(usuario.getCpf());
        if(optionalUsuario.isPresent()){
            throw new UsuarioJaExisteException("Usuário já existe com este CPF " + usuario.getCpf());
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro qualquer");
        }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setCpf(usuario.getCpf());
        novoUsuario.setNome(usuario.getNome());
        novoUsuario.setIdade(usuario.getIdade());

        novoUsuario.setId(null);
        return repository.save(novoUsuario);
    };

    public Optional<Usuario> buscaCPF(String cpf){
        return repository.findByCpf(cpf);
    }

    public Usuario buscaId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("O usuário não existe com este " + id));
    }

    public Page<Usuario> buscaUsuarios(Integer page, Integer linesPerPage,
                                       String direction, String orderBy){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }
}
