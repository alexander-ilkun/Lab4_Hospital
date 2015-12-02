package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import com.ilkun.hospital.db.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method for
 * logout and session invalidation.
 *
 * @author alexander-ilkun
 */
public class LogoutCommand implements Command {

    /**
     * Log outs the user and invalidates its session object.
     * 
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response) {
        Page page;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Logger.getLogger(this.getClass()).info("User " + ((User)session.getAttribute("user")).getName() + " logged out");
            session.invalidate();
        }
        page = new Page("/index.jsp", true);
        return page;
    }
}
