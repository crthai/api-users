package com.example.domain.enums;

public enum Perfil {
   ADMIN("admin"),
    USER("user");


   private final String role;

    Perfil(String role){
       this.role = role;
   }

    public String getRole(){
        return role;
   }
}