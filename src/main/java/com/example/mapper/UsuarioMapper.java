package com.example.mapper;
import com.example.controller.dtos.UsuarioResponseDTO;
import com.example.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface UsuarioMapper {
    UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);

    List<UsuarioResponseDTO> toUsuarioDTOs(List<Usuario> usuarios);
}