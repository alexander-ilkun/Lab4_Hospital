package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method for
 * unknown command processing.
 *
 * @author alexander-ilkun
 */
public class NoCommand implements Command {

    /**
     * Processes unknown command.
     * 
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response)  {
        Page page = new Page("/index.jsp", true);
        return page;
    }
    
}
