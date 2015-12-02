package com.ilkun.hospital.db.util;

import com.ilkun.hospital.db.entity.ConcretePrescription;
import com.ilkun.hospital.db.entity.Patient;
import com.ilkun.hospital.db.entity.Prescription;
import com.ilkun.hospital.db.entity.Role;
import com.ilkun.hospital.db.entity.User;
import com.ilkun.hospital.exception.GenericException;
import com.ilkun.hospital.exception.ResourceHelperException;
import com.ilkun.hospital.factory.ServiceFactory;
import com.ilkun.hospital.service.ConcretePrescriptionService;
import com.ilkun.hospital.service.PatientService;
import com.ilkun.hospital.service.PrescriptionService;
import com.ilkun.hospital.service.RoleService;
import com.ilkun.hospital.service.UserService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This utility initializes and fills the database.
 * Tested with Oracle 11g.
 *
 * @author alexander-ilkun
 */
public class InitializationUtil {

    private static void initDB() {
        LinkedProperties properties = new LinkedProperties();
        Connection conToUse = null;
        Statement s = null;
        try {
            properties.load(ConnectionPoolUtil.class.getClassLoader()
                    .getResourceAsStream("db-init.properties"));
            conToUse = ConnectionUtil.getConnection();
            s = conToUse.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_UPDATABLE);

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                //System.out.println("Key : " + key + ", Value : " + value);
                try {
                    s.executeUpdate(value);
                } catch (SQLException e) {
                    Logger.getLogger(InitializationUtil.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (IOException | SQLException | ResourceHelperException ex) {
            Logger.getLogger(InitializationUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionUtil.closeConnection(conToUse);
        }
    }

    private static void fillDB() {
        UserService userService = ServiceFactory.getUserService();
        RoleService roleService = ServiceFactory.getRoleService();
        PrescriptionService prescriptionService = ServiceFactory.getPrescriptionService();
        PatientService patientService = ServiceFactory.getPatientService();
        ConcretePrescriptionService concretePrescriptionService = ServiceFactory.getConcretePrescriptionService();

        Role doctorRole = new Role();
        Role nurseRole = new Role();
        doctorRole.setName("ROLE_DOCTOR");
        nurseRole.setName("ROLE_NURSE");

        User user = new User();
        user.setLogin("doctor");
        user.setPassword(BCrypt.hashpw("doctor", BCrypt.gensalt()));
        user.setName("Jhon Smith");
        user.setActivated(true);
        List<Role> roles = new ArrayList<>();
        roles.add(doctorRole);
        user.setRoles(roles);

        Prescription procedure = new Prescription();
        Prescription operation = new Prescription();
        procedure.setName("PROCEDURE");
        operation.setName("OPERATION");

        Patient patient = new Patient();
        patient.setName("Morgan");
        patient.setDiagnosis("Headache");
        patient.setDischarged(false);
        List<ConcretePrescription> concretePrescriptions = new ArrayList<>();
        patient.setConcretePrescriptions(concretePrescriptions);

        ConcretePrescription concretePrescription = new ConcretePrescription();
        concretePrescription.setDescription("Gymnastic");
        concretePrescription.setPerformed(false);
        concretePrescription.setPrescription(procedure);
        concretePrescription.setPatient(patient);
        concretePrescriptions.add(concretePrescription);

        try {
            roleService.save(doctorRole);
            roleService.save(nurseRole);
            userService.save(user);
            prescriptionService.save(procedure);
            prescriptionService.save(operation);
            patientService.save(patient);
            concretePrescriptionService.save(concretePrescription);
        } catch (GenericException ex) {
            Logger.getLogger(InitializationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws ResourceHelperException, GenericException {
        initDB();
        fillDB();
    }

}

class LinkedProperties extends Properties {

    private static final long serialVersionUID = 1L;

    private final Map<Object, Object> linkMap = new LinkedHashMap<>();

    @Override
    public synchronized Object put(Object key, Object value) {
        return linkMap.put(key, value);
    }

    @Override
    public synchronized boolean contains(Object value) {
        return linkMap.containsValue(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return linkMap.containsValue(value);
    }

    @Override
    public synchronized Enumeration<Object> elements() {
        throw new UnsupportedOperationException(
                "Enumerations are so old-school, don't use them, "
                + "use keySet() or entrySet() instead");
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkMap.entrySet();
    }

    @Override
    public synchronized void clear() {
        linkMap.clear();
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return linkMap.containsKey(key);
    }

}
