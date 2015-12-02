package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface represents Command pattern.
 * It provides different functionality for execute() method().
 *
 * @author alexander-ilkun
 */
public interface Command {

    /**
     * Processes the request and response objects depending on the implementation.
     * 
     * @param request - request for processing
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    Page execute(HttpServletRequest request, HttpServletResponse response);

}
