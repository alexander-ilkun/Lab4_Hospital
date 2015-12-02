package com.ilkun.hospital.factory;

import com.ilkun.hospital.service.ConcretePrescriptionService;
import com.ilkun.hospital.service.PatientService;
import com.ilkun.hospital.service.PrescriptionService;
import com.ilkun.hospital.service.RoleService;
import com.ilkun.hospital.service.UserService;
import com.ilkun.hospital.service.impl.ConcretePrescriptionServiceImpl;
import com.ilkun.hospital.service.impl.PatientServiceImpl;
import com.ilkun.hospital.service.impl.PrescriptionServiceImpl;
import com.ilkun.hospital.service.impl.RoleServiceImpl;
import com.ilkun.hospital.service.impl.UserServiceImpl;

/**
 * Represents Factory pattern for retrieving Service objects.
 *
 * @author alexander-ilkun
 */
public class ServiceFactory {
    
    private static final UserService userService = new UserServiceImpl();
    private static final RoleService roleService = new RoleServiceImpl();
    private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl();
    private static final PatientService patientService = new PatientServiceImpl();
    private static final ConcretePrescriptionService concretePrescriptionService = new ConcretePrescriptionServiceImpl();
    
    /**
     * Returns UserService
     * 
     * @return UserDAO
     */
    public static UserService getUserService() {
        return userService;
    }

    /**
     * Returns RoleService
     * 
     * @return RoleService
     */
    public static RoleService getRoleService() {
        return roleService;
    }

    /**
     * Returns PrescriptionService
     * 
     * @return PrescriptionService
     */
    public static PrescriptionService getPrescriptionService() {
        return prescriptionService;
    }

    /**
     * Returns PatientService
     * 
     * @return PatientService
     */
    public static PatientService getPatientService() {
        return patientService;
    }

    /**
     * Returns ConcretePrescriptionService
     * 
     * @return ConcretePrescriptionService
     */
    public static ConcretePrescriptionService getConcretePrescriptionService() {
        return concretePrescriptionService;
    }

}
