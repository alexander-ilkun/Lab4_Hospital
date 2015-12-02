package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the Concrete Prescription.
 *
 * @author alexander-ilkun
 */
public class ConcretePrescriptionException extends GenericException {

    private String exceptionMessage = "";

    public ConcretePrescriptionException() {
        super();
    }

    public ConcretePrescriptionException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

}
