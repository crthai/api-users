package com.example.mapper;

import com.example.controller.dtos.UsuarioRequestDTO;
import com.example.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper {
 Usuario usuarioRequestToUsuario(UsuarioRequestDTO usuarioRequest);
}
