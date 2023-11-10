package com.example.controller;

import com.example.controller.dtos.UsuarioRequestDTO;
import com.example.controller.dtos.UsuarioResponseDTO;
import com.example.controller.dtos.Views;
import com.example.model.Usuario;
import com.example.service.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id){
         return ResponseEntity.ok(usuarioService.buscaId(id));
     }

     @GetMapping
     public ResponseEntity<Page<UsuarioResponseDTO>> getUsers(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "linesPerPage", defaultValue = "3")Integer linesPerPage,
                                   @RequestParam(value = "direction", defaultValue = "ASC")String direction,
                                   @RequestParam(value = "orderBy", defaultValue = "id")String orderBy) {
         Page<UsuarioResponseDTO> usuarioPage = usuarioService.buscaUsuarios(page, linesPerPage, direction, orderBy);
         return ResponseEntity.ok(usuarioPage);
     }
}
