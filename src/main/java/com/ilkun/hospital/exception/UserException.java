package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the User.
 *
 * @author alexander-ilkun
 */
public class UserException extends GenericException {

    private String exceptionMessage = "";

    public UserException() {
        super();
    }

    public UserException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

}
