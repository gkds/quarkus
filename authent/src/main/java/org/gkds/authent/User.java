package org.gkds.authent;

public class User {

    public String login;
    public String firstname;
    public String lastname;
    public String email;
    public String city;
    public String password;
    public String role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // For JSON Serializer layer
    public User(){

    }
    
    public User(String login, String fname, String lname, String email, String city,String password,String role) {
        this.login = login;
        this.password = password;
        this.firstname = fname;
        this.lastname = lname;
        this.city= city;
        this.email = email;
        this.role = role;
    }

}
