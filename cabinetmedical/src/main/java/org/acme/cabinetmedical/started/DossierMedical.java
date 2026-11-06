package org.acme.cabinetmedical.started;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class DossierMedical extends PanacheEntity{

    public String affection;
    public String clinicalSymptoms;
    public String facteursRisqueVasculaire;
    public String antecedents;
    public String traitement;

   // @OneToOne
   // @JoinColumn(name="id", referencedColumnName="id")
   // private Patient person;

   // @OneToOne
   // private Prescription prescription;
    

}