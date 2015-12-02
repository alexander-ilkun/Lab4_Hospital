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
 * Provides custom implementation of the execute() method to set patient object
 * discharged.
 *
 * @author alexander-ilkun
 */
public class DischargeCommand implements Command {

    private static final String PARAM_NAME_FINAL_DIAGNOSIS = "finalDiagnosis";
    private static final String PARAM_NAME_PATIENT_ID = "patientId";
    private static final PatientService patientService = ServiceFactory.getPatientService();

    /**
     * Sets patient object discharged in the database.
     *
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response) {
        Page page = null;
        String finalDiagnosis = request.getParameter(PARAM_NAME_FINAL_DIAGNOSIS);
        String patientId = request.getParameter(PARAM_NAME_PATIENT_ID);
        Patient patient;
        try {
            patient = patientService.getById(Integer.parseInt(patientId));
            patient.setDiagnosis(finalDiagnosis);
            patient.setDischarged(true);
            patientService.save(patient);
            page = new Page("/patient-detail.jsp?id=" + patientId, true);
            Logger.getLogger(this.getClass()).info("Patient " + patient.getName() + " discharged");
        } catch (PatientException ex) {
            request.setAttribute("errorMessage", "Try again later");
            page = new Page("/error.jsp", false);
            Logger.getLogger(this.getClass()).info(ex.getMessage());
        }
        return page;
    }
}
