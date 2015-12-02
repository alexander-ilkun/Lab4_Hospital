package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.UserException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method for
 * authorization checking.
 *
 * @author alexander-ilkun
 */
public class LoginCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final UserService userService = ServiceFactory.getUserService();

    /**
     * Authorizes or not authorizes the user in system.
     * 
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response) {
        Page page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        List<User> result;
        try {
            result = userService.query().hasLogin(login).findInitialized(1, 1);
            if (result.isEmpty() || !BCrypt.checkpw(pass, result.get(0).getPassword())) {
                request.setAttribute("errorMessage", "Login does not exist or password is wrong");
                page = new Page("/error.jsp", false);
                Logger.getLogger(this.getClass()).info("Access denied for user " + login);
            } else {
                request.getSession().setAttribute("user", result.get(0));
                page = new Page("/index.jsp", true);
                Logger.getLogger(this.getClass()).info("User " + login + " logged in");
            }
        } catch (UserException ex) {
            request.setAttribute("errorMessage", "Try again later");
            page = new Page("/error.jsp", false);
            Logger.getLogger(this.getClass()).info(ex.getMessage());
        }
        return page;
    }
}
