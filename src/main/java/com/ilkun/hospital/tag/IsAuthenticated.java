package com.ilkun.hospital.tag;

import com.ilkun.hospital.db.entity.User;
import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * This class provides functionality for <isAuthenticated> tag.
 * It shows the body of the <isAuthenticated> tag if user authenticated.
 *
 * @author alexander-ilkun
 */
public class IsAuthenticated extends SimpleTagSupport {

    private String value;
    
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter jspWriter = getJspContext().getOut();
        StringWriter stringWriter = new StringWriter();
        User user = (User) ((PageContext) getJspContext()).getSession().getAttribute("user");
        if ((value.equals("true") && user != null) || 
                (value.equals("false") && user == null)) {
            getJspBody().invoke(stringWriter);
            jspWriter.write(stringWriter.toString());
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

}
