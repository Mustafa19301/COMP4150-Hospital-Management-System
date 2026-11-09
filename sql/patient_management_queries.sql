USE hospital_management;

-- Retrieve all patients
SELECT *
FROM Patient
ORDER BY patient_id;

-- Retrieve one patient by ID
SELECT *
FROM Patient
WHERE patient_id = 1;

-- Search patients by name
SELECT *
FROM Patient
WHERE name LIKE '%John%'
ORDER BY name;

-- Search patients by phone number
SELECT *
FROM Patient
WHERE phone LIKE '%2001%';

-- Retrieve all medical records with patient and doctor details
SELECT
    mr.record_id,
    p.patient_id,
    p.name AS patient_name,
    s.name AS doctor_name,
    d.specialization,
    mr.record_date,
    mr.diagnoses,
    mr.treatments,
    mr.medical_notes
FROM MedicalRecord mr
         JOIN Patient p ON mr.patient_id = p.patient_id
         JOIN Doctor d ON mr.doctor_id = d.staff_id
         JOIN Staff s ON d.staff_id = s.staff_id
ORDER BY mr.record_date DESC;

-- Retrieve the complete medical history of one patient
SELECT
    mr.record_id,
    mr.record_date,
    s.name AS doctor_name,
    d.specialization,
    mr.diagnoses,
    mr.treatments,
    mr.medical_notes
FROM MedicalRecord mr
         JOIN Doctor d ON mr.doctor_id = d.staff_id
         JOIN Staff s ON d.staff_id = s.staff_id
WHERE mr.patient_id = 1
ORDER BY mr.record_date DESC;

-- Count medical records for each patient
SELECT
    p.patient_id,
    p.name AS patient_name,
    COUNT(mr.record_id) AS total_medical_records
FROM Patient p
         LEFT JOIN MedicalRecord mr ON p.patient_id = mr.patient_id
GROUP BY p.patient_id, p.name
ORDER BY total_medical_records DESC, p.name;

-- Find patients who have no medical records
SELECT
    p.patient_id,
    p.name,
    p.phone
FROM Patient p
         LEFT JOIN MedicalRecord mr ON p.patient_id = mr.patient_id
WHERE mr.record_id IS NULL
ORDER BY p.name;

-- Count medical records by doctor
SELECT
    d.staff_id AS doctor_id,
    s.name AS doctor_name,
    d.specialization,
    COUNT(mr.record_id) AS total_records
FROM Doctor d
         JOIN Staff s ON d.staff_id = s.staff_id
         LEFT JOIN MedicalRecord mr ON d.staff_id = mr.doctor_id
GROUP BY d.staff_id, s.name, d.specialization
ORDER BY total_records DESC;

-- Search medical records by diagnosis
SELECT
    mr.record_id,
    p.name AS patient_name,
    mr.record_date,
    mr.diagnoses,
    s.name AS doctor_name
FROM MedicalRecord mr
         JOIN Patient p ON mr.patient_id = p.patient_id
         JOIN Doctor d ON mr.doctor_id = d.staff_id
         JOIN Staff s ON d.staff_id = s.staff_id
WHERE mr.diagnoses LIKE '%blood%'
ORDER BY mr.record_date DESC;

-- Retrieve records within a date range
SELECT
    mr.record_id,
    p.name AS patient_name,
    mr.record_date,
    mr.diagnoses,
    s.name AS doctor_name
FROM MedicalRecord mr
         JOIN Patient p ON mr.patient_id = p.patient_id
         JOIN Doctor d ON mr.doctor_id = d.staff_id
         JOIN Staff s ON d.staff_id = s.staff_id
WHERE mr.record_date BETWEEN '2026-10-20' AND '2026-11-08'
ORDER BY mr.record_date;

-- CRUD EXAMPLES

-- INSERT a patient
-- INSERT INTO Patient (name, date_of_birth, phone, address)
-- VALUES ('Sample Patient', '1990-05-15', '519-555-9999', 'Windsor, Ontario');

-- UPDATE a patient's contact information
-- UPDATE Patient
-- SET phone = '519-555-8888',
--     address = 'Windsor, Ontario'
-- WHERE patient_id = 1;

-- DELETE a patient
-- This will fail if the patient is referenced by appointments,
-- medical records, prescriptions, or bills.
-- DELETE FROM Patient
-- WHERE patient_id = 1;

-- INSERT a medical record
-- INSERT INTO MedicalRecord
--     (patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
-- VALUES
--     (1, 1, '2026-11-08', 'Sample diagnosis',
--      'Sample treatment', 'Sample medical notes');

-- UPDATE a medical record
-- UPDATE MedicalRecord
-- SET diagnoses = 'Updated diagnosis',
--     treatments = 'Updated treatment',
--     medical_notes = 'Updated notes'
-- WHERE record_id = 1;

-- DELETE a medical record
-- DELETE FROM MedicalRecord
-- WHERE record_id = 1;