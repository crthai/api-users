package com.example.domain.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "login")
@Entity(name = "login")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Login implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
 private String id;
 private String login;
 private String password;
 private Perfil role;

 public Login(String login, String password, Perfil role){
     this.login = login;
     this.password = password;
     this.role = role;
 }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Perfil.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
