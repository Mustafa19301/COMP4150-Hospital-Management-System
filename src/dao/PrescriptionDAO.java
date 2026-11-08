
package dao;

import database.DatabaseConnection;
import model.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

  
    //GET PRESCRIPTIONS FOR A SPECIFIC PATIENT
    public List<Prescription> getPatientPrescriptions(int patient_id)
            throws SQLException {

        List<Prescription> prescriptions = new ArrayList<>();

        String sql = "SELECT * FROM Prescription " +
                     "WHERE patient_id = ? ORDER BY `date` DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patient_id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    int pharmacist_id = rs.getInt("pharmacist_id");

                    prescriptions.add(new Prescription(
                        rs.getInt("prescription_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("medication_id"),
                        rs.wasNull() ? 0 : pharmacist_id,
                        rs.getDate("date").toLocalDate(),
                        rs.getString("status")
                    ));
                }
            }
        }

        return prescriptions;
    }


   
    //GET ALL PENDING PRESCRIPTIONS
    public List<Prescription> getPendingPrescriptions()
            throws SQLException {

        List<Prescription> prescriptions = new ArrayList<>();

        String sql = "SELECT * FROM Prescription " +
                     "WHERE status = 'Pending' ORDER BY `date` DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int pharmacist_id = rs.getInt("pharmacist_id");

                prescriptions.add(new Prescription(
                    rs.getInt("prescription_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getInt("medication_id"),
                    rs.wasNull() ? 0 : pharmacist_id,
                    rs.getDate("date").toLocalDate(),
                    rs.getString("status")
                ));
            }
        }

        return prescriptions;
    }


    
    //ADD A NEW PRESCRIPTION
    public boolean addPrescription(Prescription prescription)
            throws SQLException {

        String sql = "INSERT INTO Prescription " +
                     "(patient_id, doctor_id, medication_id, " +
                     "pharmacist_id, `date`, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prescription.getPatient_id());
            stmt.setInt(2, prescription.getDoctor_id());
            stmt.setInt(3, prescription.getMedication_id());

            if (prescription.getPharmacist_id() == 0) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, prescription.getPharmacist_id());
            }

            stmt.setDate(5, Date.valueOf(prescription.getDate()));
            stmt.setString(6, prescription.getStatus_id());

            return stmt.executeUpdate() > 0;
        }
    }


    
    //UPDATE PRESCRIPTION STATUS
    public boolean updatePrescriptionStatus(
            int prescription_id, String status)
            throws SQLException {

        String sql = "UPDATE Prescription SET status = ? " +
                     "WHERE prescription_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, prescription_id);

            return stmt.executeUpdate() > 0;
        }
    }
}
