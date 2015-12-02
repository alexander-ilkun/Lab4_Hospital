package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the Role.
 *
 * @author alexander-ilkun
 */
public class RoleException extends GenericException {

    private String exceptionMessage = "";

    public RoleException() {
        super();
    }

    public RoleException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

}
