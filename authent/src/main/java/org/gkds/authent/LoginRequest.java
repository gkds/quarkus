package org.gkds.authent;

public class LoginRequest {

    public String username;
    public String password;
    public String refreshToken;

    public LoginRequest(){
    }

    public LoginRequest(String username,String password, String refreshToken){
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
    }
}
