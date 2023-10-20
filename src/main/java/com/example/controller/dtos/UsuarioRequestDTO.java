package com.example.controller.dtos;


public class UsuarioRequestDTO {

    private final String nome;

    private final Integer idade;

    private final String cpf;

    public UsuarioRequestDTO(String nome, Integer idade, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public Integer getIdade() {
        return idade;
    }
    public String getCpf() {
        return cpf;
    }
}
