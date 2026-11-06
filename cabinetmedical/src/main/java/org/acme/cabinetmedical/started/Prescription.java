package org.acme.cabinetmedical.started;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Prescription extends PanacheEntity {

    public String libelle;
    public String posologie;

}
