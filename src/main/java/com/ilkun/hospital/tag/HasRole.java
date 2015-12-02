package com.ilkun.hospital.tag;

import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * This class provides functionality for <hasRole> tag.
 * It shows the body of the <hasRole> tag if user has specified role.
 *
 * @author alexander-ilkun
 */
public class HasRole extends SimpleTagSupport {

    private String roles;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter jspWriter = getJspContext().getOut();
        StringWriter stringWriter = new StringWriter();
        String[] roleNames = roles.split(",");
        User user = (User) ((PageContext) getJspContext()).getSession().getAttribute("user");
        if (user != null) {
            outer:
            for (Role userRole : user.getRoles()) {
                for (String roleName : roleNames) {
                    if (userRole.getName().equals(roleName)) {
                        getJspBody().invoke(stringWriter);
                        jspWriter.write(stringWriter.toString());
                        break outer;
                    }
                }
            }
        }
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
