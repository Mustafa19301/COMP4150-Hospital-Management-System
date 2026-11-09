
package main;

import dao.PatientDAO;
import dao.MedicalRecordDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DAOTest {

    public static void main(String[] args) {

        PatientDAO patientDAO = new PatientDAO();
        MedicalRecordDAO recordDAO = new MedicalRecordDAO();

        int temporaryPatientId = -1;
        int temporaryRecordId = -1;

        try {

            System.out.println("\nALL PATIENTS");

            List<Map<String, Object>> patients =
                    patientDAO.getAllPatients();

            for (Map<String, Object> patient : patients) {
                System.out.println(patient);
            }

            System.out.println("Patients found: " + patients.size());


            System.out.println("\nPATIENT BY ID");

            Map<String, Object> patient =
                    patientDAO.getPatientById(1);

            System.out.println(patient);


            System.out.println("\nSEARCH PATIENTS");

            List<Map<String, Object>> searchResults =
                    patientDAO.searchPatientsByName("John");

            for (Map<String, Object> result : searchResults) {
                System.out.println(result);
            }

            System.out.println("\nPATIENTS WITHOUT RECORDS");

            List<Map<String, Object>> patientsWithoutRecords =
                    patientDAO.getPatientsWithoutMedicalRecords();

            System.out.println(
                    "Patients without records: "
                            + patientsWithoutRecords.size()
            );

            System.out.println("\nADD TEMPORARY PATIENT");

            temporaryPatientId = patientDAO.addPatient(
                    "Java DAO Test Patient",
                    LocalDate.of(1995, 5, 15),
                    "519-555-7777",
                    "Java Testing Address, Windsor"
            );

            System.out.println(
                    "Created patient ID: " + temporaryPatientId
            );

            System.out.println("\n UPDATE PATIENT");

            boolean patientUpdated = patientDAO.updatePatient(
                    temporaryPatientId,
                    "Java DAO Updated Patient",
                    LocalDate.of(1995, 5, 15),
                    "519-555-8888",
                    "Updated Java Testing Address, Windsor"
            );

            System.out.println("Patient updated: " + patientUpdated);

            System.out.println("\n ADD MEDICAL RECORD");

            temporaryRecordId = recordDAO.addMedicalRecord(
                    temporaryPatientId,
                    1,
                    LocalDate.now(),
                    "Java DAO test diagnosis",
                    "Java DAO test treatment",
                    "Created through MedicalRecordDAO"
            );

            System.out.println(
                    "Created medical record ID: " + temporaryRecordId
            );

            System.out.println("\n PATIENT MEDICAL HISTORY");

            List<Map<String, Object>> history =
                    recordDAO.getPatientMedicalHistory(
                            temporaryPatientId
                    );

            for (Map<String, Object> record : history) {
                System.out.println(record);
            }


            System.out.println("\n RECORDS BY DOCTOR 1");

            List<Map<String, Object>> doctorRecords =
                    recordDAO.getMedicalRecordsByDoctor(1);

            System.out.println(
                    "Records found: " + doctorRecords.size()
            );


            System.out.println("\nUPDATE MEDICAL RECORD");

            boolean recordUpdated = recordDAO.updateMedicalRecord(
                    temporaryRecordId,
                    temporaryPatientId,
                    1,
                    LocalDate.now(),
                    "Updated Java DAO diagnosis",
                    "Updated Java DAO treatment",
                    "Updated through MedicalRecordDAO"
            );

            System.out.println("Record updated: " + recordUpdated);


        } catch (SQLException e) {

            System.out.println("\nA database error occurred:");
            e.printStackTrace();

        } finally {

            System.out.println("\nCLEANUP");

            if (temporaryRecordId != -1) {
                try {
                    boolean deleted =
                            recordDAO.deleteMedicalRecord(
                                    temporaryRecordId
                            );

                    System.out.println(
                            "Temporary medical record deleted: "
                                    + deleted
                    );

                } catch (SQLException e) {
                    System.out.println(
                            "Could not delete temporary medical record: "
                                    + e.getMessage()
                    );
                }
            }

            if (temporaryPatientId != -1) {
                try {
                    boolean deleted =
                            patientDAO.deletePatient(
                                    temporaryPatientId
                            );

                    System.out.println(
                            "Temporary patient deleted: " + deleted
                    );

                } catch (SQLException e) {
                    System.out.println(
                            "Could not delete temporary patient: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }
}