package org.acme.cabinetmedical.started;

import javax.persistence.Entity;
import javax.persistence.Column;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Medecin extends PanacheEntity{

    public String name;
    @Column(unique=true)
    public String login;
    public String specialite;

    public static Medecin findByLogin(String loginName){
        return find("login", loginName).singleResult();
    }
}
