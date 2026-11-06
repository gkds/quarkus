package org.gkds.authent;

public class LoginResult {

    public String username;
    
    public String role;
    public String originalUserName;
    public String accessToken;
    public String tokenType = "Bearer";
    public String refreshToken;

    public LoginResult(){}

    public LoginResult(String username,String role,String originalUsername, String accessToken, String refreshToken){
        this.username = username;
        this.role = role;
        this.originalUserName = originalUsername;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}