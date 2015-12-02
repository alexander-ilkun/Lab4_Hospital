package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the Prescription.
 *
 * @author alexander-ilkun
 */
public class PrescriptionException extends GenericException {

    private String exceptionMessage = "";

    public PrescriptionException() {
        super();
    }

    public PrescriptionException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

}
