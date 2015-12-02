package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the Patient.
 *
 * @author alexander-ilkun
 */
public class PatientException extends GenericException {

    private String exceptionMessage = "";

    public PatientException() {
        super();
    }

    public PatientException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

}
