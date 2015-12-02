package com.ilkun.hospital.filter;

import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.PatientService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * This filter checks if the user have permissions to access the page.
 *
 * @author alexander-ilkun
 */
public class SecurityFilter implements Filter {

    private PatientService patientService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        patientService = ServiceFactory.getPatientService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        if (((HttpServletRequest) request).getSession().getAttribute("language") == null) {
            request.setAttribute("language", "en");
        }
        String[] securePages = {"/patients.jsp", "/patient-detail.jsp"};
        String path = ((HttpServletRequest) request).getRequestURL().toString();
        for (String securePage : securePages) {
            if (path.contains(securePage)) {
                if (((HttpServletRequest) request).getSession().getAttribute("user") == null) {
                    request.setAttribute("errorMessage", "Access denied");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
