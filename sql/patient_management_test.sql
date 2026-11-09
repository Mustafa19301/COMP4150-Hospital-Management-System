USE hospital_management;

-- Verify patient and medical-record counts
SELECT COUNT(*) AS TotalPatients
FROM Patient;

SELECT COUNT(*) AS TotalMedicalRecords
FROM MedicalRecord;

-- Display all patients
SELECT *
FROM Patient
ORDER BY patient_id;

-- Display all medical records with patient and doctor names
SELECT
    mr.record_id,
    p.name AS patient_name,
    s.name AS doctor_name,
    mr.record_date,
    mr.diagnoses,
    mr.treatments,
    mr.medical_notes
FROM MedicalRecord mr
         JOIN Patient p ON mr.patient_id = p.patient_id
         JOIN Doctor d ON mr.doctor_id = d.staff_id
         JOIN Staff s ON d.staff_id = s.staff_id
ORDER BY mr.record_date DESC;

-- Test retrieving all patients
CALL GetAllPatients();

-- Test retrieving one patient
CALL GetPatientByID(1);

-- Test searching patients by name
CALL SearchPatientsByName('John');

-- Test retrieving a patient's medical history
CALL GetPatientMedicalHistory(1);

-- Test retrieving records for a doctor
CALL GetMedicalRecordsByDoctor(1);

-- Test finding patients without medical records
CALL GetPatientsWithoutMedicalRecords();

-- Verify medical-record counts by patient
SELECT
    p.patient_id,
    p.name,
    COUNT(mr.record_id) AS total_records
FROM Patient p
         LEFT JOIN MedicalRecord mr ON p.patient_id = mr.patient_id
GROUP BY p.patient_id, p.name
ORDER BY p.patient_id;

-- Verify installed patient and medical-record triggers
SHOW TRIGGERS
    WHERE `Table` IN ('Patient', 'MedicalRecord');

-- Verify installed stored procedures
SHOW PROCEDURE STATUS
    WHERE Db = 'hospital_management'
        AND Name IN (
                     'GetAllPatients',
                     'GetPatientByID',
                     'SearchPatientsByName',
                     'GetPatientMedicalHistory',
                     'GetMedicalRecordsByDoctor',
                     'GetPatientsWithoutMedicalRecords',
                     'AddPatient',
                     'UpdatePatient',
                     'DeletePatient',
                     'AddMedicalRecord',
                     'UpdateMedicalRecord',
                     'DeleteMedicalRecord'
            );