package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the UserRole.
 *
 * @author alexander-ilkun
 */
public class UserRoleException extends GenericException {

    private String exceptionMessage = "";

    public UserRoleException() {
        super();
    }

    public UserRoleException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

}
