package com.cartoon.entity;

import java.io.Serializable;
import java.util.List;

public class MyJwt implements Serializable {
    private List<String> scope;
    private Integer id;
    private Long exp;
    private List<String> authorities;
    private String jti;
    private String client_id;
    private String username;

    @Override
    public String toString() {
        return "MyJwt{" +
                "scope=" + scope +
                ", id=" + id +
                ", exp=" + exp +
                ", authorities=" + authorities +
                ", jti='" + jti + '\'' +
                ", client_id='" + client_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
