package com.ilkun.hospital.command;

import com.ilkun.hospital.controller.Page;
import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.ConcretePrescriptionException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.ConcretePrescriptionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This class provides the implementation of <tt>Command</tt> interface.
 * Provides custom implementation of the execute() method to set
 * concrete prescription object performed.
 *
 * @author alexander-ilkun
 */
public class PerformPrescriptionCommand implements Command {

    private static final String PARAM_NAME_CON_PRESC_ID = "conPrescId";
    private static final String PARAM_NAME_PATIENT_ID = "patientId";
    private static final ConcretePrescriptionService concretePrescriptionService = ServiceFactory.getConcretePrescriptionService();

    /**
     * Sets concrete prescription object performed in the database.
     *
     * @param request - request containing information from the form
     * @param response - response for processing
     * @return the page for forwarding or redirecting
     */
    @Override
    public Page execute(HttpServletRequest request,
            HttpServletResponse response) {
        Page page = null;
        String conPrescId = request.getParameter(PARAM_NAME_CON_PRESC_ID);
        String patientId = request.getParameter(PARAM_NAME_PATIENT_ID);
        ConcretePrescription concretePrescription;
        User user = (User) request.getSession().getAttribute("user");
        try {
            concretePrescription = concretePrescriptionService.getInitializedById(Integer.parseInt(conPrescId));
            concretePrescription.setPerformed(true);
            concretePrescription.setPerformer(user);
            concretePrescriptionService.save(concretePrescription);
            page = new Page("/patient-detail.jsp?id=" + patientId, true);
            Logger.getLogger(this.getClass()).info("Prescription performed");
        } catch (ConcretePrescriptionException ex) {
            request.setAttribute("errorMessage", "Try again later");
            page = new Page("/error.jsp", false);
            Logger.getLogger(this.getClass()).info(ex.getMessage());
        }
        return page;
    }
}
