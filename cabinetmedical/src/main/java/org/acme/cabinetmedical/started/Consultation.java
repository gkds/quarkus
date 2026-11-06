package org.acme.cabinetmedical.started;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Consultation extends PanacheEntity {
    
    public LocalDateTime dateConsultation = LocalDateTime.now();
    @OneToMany
    public Set<Actes> actesPratiques = new HashSet<>();

    @OneToMany
    public Set<Prescription> prescriptions = new HashSet<>();

    @OneToOne
    public Patient patient;

    @ManyToOne
    public Medecin soignant;
    
}