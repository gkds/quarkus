package org.acme.cabinetmedical.started;

import java.time.LocalDateTime;

public class UserBean {

    public String name;

    public LocalDateTime createdAt = LocalDateTime.now();

    public String location;

    public UserBean(){}

    public UserBean(String name, String location) {
        this.name = name;
        this.location = location;
    }

}
