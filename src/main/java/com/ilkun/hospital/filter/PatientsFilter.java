package com.ilkun.hospital.filter;

import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.PatientException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.PatientService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This filter fills the models for requested page.
 *
 * @author alexander-ilkun
 */
public class PatientsFilter implements Filter {

    private PatientService patientService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        patientService = ServiceFactory.getPatientService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            List<Patient> patients = patientService.query().find(1, 100);
            request.setAttribute("patients", patients);
        } catch (PatientException ex) {
            Logger.getLogger(PatientsFilter.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Try again later");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
