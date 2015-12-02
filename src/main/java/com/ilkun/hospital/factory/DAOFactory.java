package com.ilkun.hospital.factory;

import com.ilkun.hospital.db.dao.ConcretePrescriptionDAO;
import com.ilkun.hospital.db.dao.PatientDAO;
import com.ilkun.hospital.db.dao.PrescriptionDAO;
import com.ilkun.hospital.db.dao.RoleDAO;
import com.ilkun.hospital.db.dao.UserDAO;
import com.ilkun.hospital.db.dao.UserRoleDAO;
import com.ilkun.hospital.db.dao.impl.ConcretePrescriptionJdbcDAOImpl;
import com.ilkun.hospital.db.dao.impl.PatientJdbcDAOImpl;
import com.ilkun.hospital.db.dao.impl.PrescriptionJdbcDAOImpl;
import com.ilkun.hospital.db.dao.impl.RoleJdbcDAOImpl;
import com.ilkun.hospital.db.dao.impl.UserJdbcDAOImpl;
import com.ilkun.hospital.db.dao.impl.UserRoleJdbcDAOImpl;

/**
 * Represents Factory pattern for retrieving DAO objects.
 *
 * @author alexander-ilkun
 */
public class DAOFactory {
    
    /**
     * Returns UserDAO
     * 
     * @return UserDAO
     */
    public static UserDAO getUserDAO() {
        return new UserJdbcDAOImpl();
    }

    /**
     * Returns RoleDAO
     * 
     * @return RoleDAO
     */
    public static RoleDAO getRoleDAO() {
        return new RoleJdbcDAOImpl();
    }

    /**
     * Returns UserRoleDAO
     * 
     * @return UserRoleDAO
     */
    public static UserRoleDAO getUserRoleDAO() {
        return new UserRoleJdbcDAOImpl();
    }

    /**
     * Returns PrescriptionDAO
     * 
     * @return PrescriptionDAO
     */
    public static PrescriptionDAO getPrescriptionDAO() {
        return new PrescriptionJdbcDAOImpl();
    }

    /**
     * Returns PatientDAO
     * 
     * @return PatientDAO
     */
    public static PatientDAO getPatientDAO() {
        return new PatientJdbcDAOImpl();
    }

    /**
     * Returns ConcretePrescriptionDAO
     * 
     * @return ConcretePrescriptionDAO
     */
    public static ConcretePrescriptionDAO getConcretePrescriptionDAO() {
        return new ConcretePrescriptionJdbcDAOImpl();
    }

}
