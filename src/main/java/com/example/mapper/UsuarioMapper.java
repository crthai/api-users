package com.example.mapper;

import com.example.controller.dtos.UsuarioResponseDTO;
import com.example.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface UsuarioMapper {
   // Usuario INSTANCE = Mappers.getMapper(Usuario.class);
    UsuarioResponseDTO toUsuarioDTO(Usuario usuario);

    List<UsuarioResponseDTO> toUsuarioDTOs(List<Usuario> usuarios);
}