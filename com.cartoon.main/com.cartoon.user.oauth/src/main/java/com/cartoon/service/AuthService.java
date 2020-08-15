package com.cartoon.service;


import com.cartoon.entity.AuthToken;
import com.cartoon.exceptions.UnauthorizedException;

public interface AuthService {

    AuthToken userLogin(String username, String password, String clientId, String clientSet, String ttl)throws UnauthorizedException;



    //void LoginOut(String username);
}
