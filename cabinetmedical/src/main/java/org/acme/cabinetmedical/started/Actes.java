package org.acme.cabinetmedical.started;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Actes extends PanacheEntity {
    public String code;
    public String libelle;
    public Double prix;

}
