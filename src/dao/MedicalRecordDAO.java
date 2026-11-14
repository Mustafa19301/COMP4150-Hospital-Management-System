package dao;

import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MedicalRecordDAO {

    public List<Map<String, Object>> getPatientMedicalHistory(
            int patientId) throws SQLException {

        String sql = "CALL GetPatientMedicalHistory(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                return readRows(rs);
            }
        }
    }

    public List<Map<String, Object>> getMedicalRecordsByDoctor(
            int doctorId) throws SQLException {

        String sql = "CALL GetMedicalRecordsByDoctor(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, doctorId);

            try (ResultSet rs = stmt.executeQuery()) {
                return readRows(rs);
            }
        }
    }

    public int addMedicalRecord(
            int patientId,
            int doctorId,
            LocalDate recordDate,
            String diagnoses,
            String treatments,
            String medicalNotes) throws SQLException {

        String sql = "CALL AddMedicalRecord(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setDate(3, Date.valueOf(recordDate));
            stmt.setString(4, diagnoses);
            stmt.setString(5, treatments);
            stmt.setString(6, medicalNotes);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("new_record_id");
                }
            }
        }

        throw new SQLException("Medical record could not be added.");
    }

    public boolean updateMedicalRecord(
            int recordId,
            int patientId,
            int doctorId,
            LocalDate recordDate,
            String diagnoses,
            String treatments,
            String medicalNotes) throws SQLException {

        String sql = "CALL UpdateMedicalRecord(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, recordId);
            stmt.setInt(2, patientId);
            stmt.setInt(3, doctorId);
            stmt.setDate(4, Date.valueOf(recordDate));
            stmt.setString(5, diagnoses);
            stmt.setString(6, treatments);
            stmt.setString(7, medicalNotes);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("rows_updated") > 0;
            }
        }
    }


    public boolean deleteMedicalRecord(int recordId, int doctorId)
            throws SQLException {

        String sql = "{CALL DeleteMedicalRecord(?, ?)}";

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.setInt(1, recordId);
            statement.setInt(2, doctorId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("rows_deleted") > 0;
                }
            }
        }

        return false;
    }

    private List<Map<String, Object>> readRows(ResultSet rs)
            throws SQLException {

        List<Map<String, Object>> rows = new ArrayList<>();

        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();

        while (rs.next()) {

            Map<String, Object> row = new LinkedHashMap<>();

            for (int i = 1; i <= columnCount; i++) {

                Object value = rs.getObject(i);

                if (value instanceof Date) {
                    value = ((Date) value).toLocalDate();
                }

                row.put(metadata.getColumnLabel(i), value);
            }

            rows.add(row);
        }

        return rows;
    }
}