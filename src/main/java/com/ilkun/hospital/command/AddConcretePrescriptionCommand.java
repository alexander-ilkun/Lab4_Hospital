package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.exception.ConcretePrescriptionException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.ConcretePrescriptionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method to add
 * new concrete prescription object to the database.
 *
 * @author alexander-ilkun
 */
public class AddConcretePrescriptionCommand implements Command {

    private static final String PARAM_NAME_PRESCRIPTION_ID = "prescriptionId";
    private static final String PARAM_NAME_PATIENT_ID = "patientId";
    private static final String PARAM_NAME_DESCRIPTION = "description";
    private static final ConcretePrescriptionService concretePrescriptionService = ServiceFactory.getConcretePrescriptionService();

    /**
     * Adds new concrete prescription object to the database.
     * 
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response) {
        Page page = null;
        String prescriptionId = request.getParameter(PARAM_NAME_PRESCRIPTION_ID);
        String patientId = request.getParameter(PARAM_NAME_PATIENT_ID);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        ConcretePrescription concretePrescription = new ConcretePrescription();
        Prescription prescription = new Prescription();
        Patient patient = new Patient();
        prescription.setId(Integer.parseInt(prescriptionId));
        patient.setId(Integer.parseInt(patientId));
        concretePrescription.setPrescription(prescription);
        concretePrescription.setPatient(patient);
        concretePrescription.setDescription(description);
        try {
            concretePrescriptionService.save(concretePrescription);
            page = new Page("/patient-detail.jsp?id=" + patientId, true);
            Logger.getLogger(this.getClass()).info("ConcretePrscription added");
        } catch (ConcretePrescriptionException ex) {
            request.setAttribute("errorMessage", "Try again later");
            page = new Page("/error.jsp", false);
            Logger.getLogger(this.getClass()).info(ex.getMessage());
        }
        return page;
    }
}
