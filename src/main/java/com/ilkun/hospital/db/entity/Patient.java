package com.ilkun.hospital.db.entity;

import java.util.List;

/**
 *
 * @author alexander-ilkun
 */
public class Patient {
    
    private int id;
    private String name;
    private String diagnosis;
    private List<ConcretePrescription> concretePrescriptions;
    private boolean discharged;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<ConcretePrescription> getConcretePrescriptions() {
        return concretePrescriptions;
    }

    public void setConcretePrescriptions(List<ConcretePrescription> concretePrescriptions) {
        this.concretePrescriptions = concretePrescriptions;
    }

    public boolean getDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

}
