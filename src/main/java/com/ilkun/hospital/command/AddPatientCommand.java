package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.exception.PatientException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.PatientService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method to add
 * new patient object to the database.
 *
 * @author alexander-ilkun
 */
public class AddPatientCommand implements Command {

    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_DIAGNOSIS = "diagnosis";
    private static final PatientService patientService = ServiceFactory.getPatientService();

    /**
     * Adds new patient object to the database.
     * 
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response) {
        Page page = null;
        String name = request.getParameter(PARAM_NAME_NAME);
        String diagnosis = request.getParameter(PARAM_NAME_DIAGNOSIS);
        Patient patient = new Patient();
        patient.setName(name);
        patient.setDiagnosis(diagnosis);
        try {
            patientService.save(patient);
            page = new Page("/patients.jsp", true);
            Logger.getLogger(this.getClass()).info("Patient added");
        } catch (PatientException ex) {
            request.setAttribute("errorMessage", "Try again later");
            page = new Page("/error.jsp", false);
            Logger.getLogger(this.getClass()).info(ex.getMessage());
        }
        return page;
    }
}
