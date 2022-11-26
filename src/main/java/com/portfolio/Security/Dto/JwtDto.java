package com.portfolio.Security.Dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String username;
    private boolean exist;
    private Collection<? extends GrantedAuthority> authorities;
    //contructor

    public JwtDto(String token, String username, Collection<? extends GrantedAuthority> authorities, boolean exist) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.exist = exist;

    }

    //Getter & Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBrarer(String bearer) {
        this.bearer = bearer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

}
