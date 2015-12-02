package com.ilkun.hospital.filter;

import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.exception.ConcretePrescriptionException;
import com.ilkun.hospital.exception.PatientException;
import com.ilkun.hospital.exception.PrescriptionException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.ConcretePrescriptionService;
import com.ilkun.hospital.service.PatientService;
import com.ilkun.hospital.service.PrescriptionService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This filter fills the models for requested page.
 *
 * @author alexander-ilkun
 */
public class PatientFilter implements Filter {

    private PatientService patientService;
    private PrescriptionService prescriptionService;
    private ConcretePrescriptionService concretePrescriptionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        patientService = ServiceFactory.getPatientService();
        prescriptionService = ServiceFactory.getPrescriptionService();
        concretePrescriptionService = ServiceFactory.getConcretePrescriptionService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        if (request.getParameter("id") != null) {
            try {
                Patient patient = patientService.getById(Integer.parseInt(request.getParameter("id")));
                List<Prescription> prescriptions = prescriptionService.query().find(1, 100);
                List<ConcretePrescription> concretePrescriptions = concretePrescriptionService.query().hasPatient(patient).findInitialized(1, 100);
                request.setAttribute("patient", patient);
                request.setAttribute("prescriptions", prescriptions);
                request.setAttribute("concretePrescriptions", concretePrescriptions);
            } catch (PatientException | PrescriptionException | ConcretePrescriptionException ex) {
                Logger.getLogger(PatientFilter.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", "Try again later");
            }
            filterChain.doFilter(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
