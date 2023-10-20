package com.example.controller;

import com.example.controller.dtos.UsuarioRequestDTO;
import com.example.controller.dtos.Views;
import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;
import com.example.service.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

   private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @JsonView(Views.UsuarioResponse.class)
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody UsuarioRequestDTO usuario){
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

     @GetMapping("/{id}")
     @JsonView(Views.UsuarioResponse.class)
     public ResponseEntity<Usuario> findById(@PathVariable Long id){
         return usuarioService.buscaID(id)
                 .map(record -> ResponseEntity.ok().body(record))
                 .orElse(ResponseEntity.notFound().build());
     }

     @GetMapping("/pagination/{offset}/{pageSize}")
     public Page<Usuario> getUsers(@PathVariable int offset,@PathVariable int pageSize) {
        return usuarioService.buscaUsuarios(offset, pageSize);
     }


}
