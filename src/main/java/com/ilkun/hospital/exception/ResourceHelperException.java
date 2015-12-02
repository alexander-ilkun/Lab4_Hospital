package com.ilkun.hospital.exception;

/**
 * This class represents exception related to the application resources.
 *
 * @author alexander-ilkun
 */
public class ResourceHelperException extends Exception {

    private String exceptionMessage = "";

    public ResourceHelperException() {
        super();
    }

    public ResourceHelperException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
