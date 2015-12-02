package com.ilkun.hospital.db.entity;

/**
 *
 * @author alexander-ilkun
 */
public class ConcretePrescription {
    
    private int id;
    private Prescription prescription;
    private Patient patient;
    private User performer;
    private String description;
    private boolean performed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getPerformer() {
        return performer;
    }

    public void setPerformer(User performer) {
        this.performer = performer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

}
