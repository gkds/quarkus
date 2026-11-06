package org.acme.cabinetmedical.started;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Patient extends PanacheEntity {
    public String lname;
    public String fname;
    public LocalDate birth;
    public Status status;
    public String profession;
    public String familySituation;
    public String sexe;
    public String personInCharge;
    public String phone;
    public LocalDate createdAt = LocalDate.now();
    public LocalDate updateAt;

    @ManyToOne
    public Medecin doctor;

    @OneToOne( cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public DossierMedical dossierMedical;

    public static Patient findByName(String name){
        return find("lname", name).firstResult();
    }

    public static List<Patient> findAlive(){
        return list("status", Status.Alive);
    }

    public static void deleteStefs(){
        delete("lname", "Stef");
    }

    public static List<Patient> findNotAlive() {
        return list("status", Status.Dead);
    }

    public static List<Patient> findByDoctorName(String doctorName) {
        System.out.println(">> in findByDoctorName : "+doctorName);
        return list("doctor.login", doctorName);
    }
}

enum Status {
    Alive,
    Dead
}