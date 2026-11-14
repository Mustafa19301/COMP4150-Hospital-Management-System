USE hospital_management;

DROP PROCEDURE IF EXISTS GetAllPatients;
DROP PROCEDURE IF EXISTS GetPatientByID;
DROP PROCEDURE IF EXISTS SearchPatientsByName;
DROP PROCEDURE IF EXISTS GetPatientMedicalHistory;
DROP PROCEDURE IF EXISTS GetMedicalRecordsByDoctor;
DROP PROCEDURE IF EXISTS GetPatientsWithoutMedicalRecords;
DROP PROCEDURE IF EXISTS AddPatient;
DROP PROCEDURE IF EXISTS UpdatePatient;
DROP PROCEDURE IF EXISTS DeletePatient;
DROP PROCEDURE IF EXISTS AddMedicalRecord;
DROP PROCEDURE IF EXISTS UpdateMedicalRecord;
DROP PROCEDURE IF EXISTS DeleteMedicalRecord;

DELIMITER //

-- Retrieve all patients
CREATE PROCEDURE GetAllPatients()
BEGIN
    SELECT *
    FROM Patient
    ORDER BY patient_id;
END //

-- Retrieve a patient by ID
CREATE PROCEDURE GetPatientByID(IN p_patient_id INT)
BEGIN
    SELECT *
    FROM Patient
    WHERE patient_id = p_patient_id;
END //

-- Search patients by name
CREATE PROCEDURE SearchPatientsByName(IN p_name VARCHAR(100))
BEGIN
    SELECT *
    FROM Patient
    WHERE name LIKE CONCAT('%', p_name, '%')
    ORDER BY name;
END //

-- Retrieve a patient's complete medical history
CREATE PROCEDURE GetPatientMedicalHistory(IN p_patient_id INT)
BEGIN
    SELECT
        mr.record_id,
        p.patient_id,
        p.name AS patient_name,
        mr.doctor_id,
        mr.record_date,
        s.name AS doctor_name,
        d.specialization,
        mr.diagnoses,
        mr.treatments,
        mr.medical_notes
    FROM MedicalRecord mr
             JOIN Patient p ON mr.patient_id = p.patient_id
             JOIN Doctor d ON mr.doctor_id = d.staff_id
             JOIN Staff s ON d.staff_id = s.staff_id
    WHERE mr.patient_id = p_patient_id
    ORDER BY mr.record_date DESC;
END //

-- 5. Retrieve medical records belonging to a doctor
CREATE PROCEDURE GetMedicalRecordsByDoctor(IN p_doctor_id INT)
BEGIN
    SELECT
        mr.record_id,
        p.name AS patient_name,
        mr.record_date,
        mr.diagnoses,
        mr.treatments,
        mr.medical_notes
    FROM MedicalRecord mr
             JOIN Patient p ON mr.patient_id = p.patient_id
    WHERE mr.doctor_id = p_doctor_id
    ORDER BY mr.record_date DESC;
END //

-- Find patients without medical records
CREATE PROCEDURE GetPatientsWithoutMedicalRecords()
BEGIN
    SELECT
        p.patient_id,
        p.name,
        p.date_of_birth,
        p.phone,
        p.address
    FROM Patient p
             LEFT JOIN MedicalRecord mr ON p.patient_id = mr.patient_id
    WHERE mr.record_id IS NULL
    ORDER BY p.name;
END //

-- Add a new patient
CREATE PROCEDURE AddPatient(
    IN p_name VARCHAR(100),
    IN p_date_of_birth DATE,
    IN p_phone VARCHAR(20),
    IN p_address VARCHAR(255)
)
BEGIN
    INSERT INTO Patient (name, date_of_birth, phone, address)
    VALUES (p_name, p_date_of_birth, p_phone, p_address);

    SELECT LAST_INSERT_ID() AS new_patient_id;
END //

-- Update patient information
CREATE PROCEDURE UpdatePatient(
    IN p_patient_id INT,
    IN p_name VARCHAR(100),
    IN p_date_of_birth DATE,
    IN p_phone VARCHAR(20),
    IN p_address VARCHAR(255)
)
BEGIN
    UPDATE Patient
    SET name = p_name,
        date_of_birth = p_date_of_birth,
        phone = p_phone,
        address = p_address
    WHERE patient_id = p_patient_id;

    SELECT ROW_COUNT() AS rows_updated;
END //

-- Delete a patient
-- Foreign-key constraints prevent deletion if related records exist.
CREATE PROCEDURE DeletePatient(IN p_patient_id INT)
BEGIN
    DELETE FROM Patient
    WHERE patient_id = p_patient_id;

    SELECT ROW_COUNT() AS rows_deleted;
END //

-- Add a medical record
CREATE PROCEDURE AddMedicalRecord(
    IN p_patient_id INT,
    IN p_doctor_id INT,
    IN p_record_date DATE,
    IN p_diagnoses TEXT,
    IN p_treatments TEXT,
    IN p_medical_notes TEXT
)
BEGIN
    INSERT INTO MedicalRecord
    (patient_id, doctor_id, record_date,
     diagnoses, treatments, medical_notes)
    VALUES
        (p_patient_id, p_doctor_id, p_record_date,
         p_diagnoses, p_treatments, p_medical_notes);

    SELECT LAST_INSERT_ID() AS new_record_id;
END //

-- Update a medical record
CREATE PROCEDURE UpdateMedicalRecord(
    IN p_record_id INT,
    IN p_patient_id INT,
    IN p_doctor_id INT,
    IN p_record_date DATE,
    IN p_diagnoses TEXT,
    IN p_treatments TEXT,
    IN p_medical_notes TEXT
)
BEGIN
    UPDATE MedicalRecord
    SET patient_id = p_patient_id,
        doctor_id = p_doctor_id,
        record_date = p_record_date,
        diagnoses = p_diagnoses,
        treatments = p_treatments,
        medical_notes = p_medical_notes
    WHERE record_id = p_record_id;

    SELECT ROW_COUNT() AS rows_updated;
END //

-- Delete a medical record
CREATE PROCEDURE DeleteMedicalRecord(IN p_record_id INT)
BEGIN
    DELETE FROM MedicalRecord
    WHERE record_id = p_record_id;

    SELECT ROW_COUNT() AS rows_deleted;
END //

DELIMITER ;