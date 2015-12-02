package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.RoleException;
import com.ilkun.hospital.exception.UserException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.RoleService;
import com.ilkun.hospital.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method for registration.
 *
 * @author alexander-ilkun
 */
public class RegisterCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_FULL_NAME = "fullName";
    private static final String PARAM_NAME_ROLE_NAME = "roleName";
    private static final UserService userService = ServiceFactory.getUserService();
    private static final RoleService roleService = ServiceFactory.getRoleService();

    /**
     * Registers or not registers the user in system.
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
        String fullName = request.getParameter(PARAM_NAME_FULL_NAME);
        String roleName = request.getParameter(PARAM_NAME_ROLE_NAME);
        List<Role> resultRole;
        List<User> resultUser;
        Role role;
        User user;
        try {
            resultUser = userService.query().hasLogin(login).find(1, 1);
            if (!resultUser.isEmpty()) {
                request.setAttribute("errorMessage", "Login already exists");
                page = new Page("/error.jsp", false);
                Logger.getLogger(this.getClass()).info("Login already exists");
            } else {
                resultRole = roleService.query().hasName(roleName).find(1, 1);
                if (resultRole.isEmpty()) {
                    request.setAttribute("errorMessage", "Wrong role selectd");
                    page = new Page("/error.jsp", false);
                } else {
                    role = resultRole.get(0);
                    user = new User();
                    user.setLogin(login);
                    user.setName(fullName);
                    user.setPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));
                    user.setActivated(true);
                    user.setRoles(new ArrayList<Role>());
                    user.getRoles().add(role);
                    userService.save(user);
                    page = new Page("/index.jsp", true);
                    Logger.getLogger(this.getClass()).info("Registration successful");
                }
            }
        } catch (UserException | RoleException ex) {
            request.setAttribute("errorMessage", "Try again later");
            page = new Page("/error.jsp", false);
            Logger.getLogger(this.getClass()).info(ex.getMessage());
        }
        return page;
    }
}
