USE hospital_management;

-- GET ALL PRESCRIPTIONS
SELECT * FROM Prescription;

-- GET PRESCRIPTION BY PATIENT
SELECT p.prescription_id,
    pt.patient_id,
    pt.name AS patient_name,
    m.medication_id,
    m.name AS medication_name,
    m.price,
    p.date,
    p.status
FROM Prescription p
JOIN Patient pt
    ON p.patient_id = pt.patient_id
JOIN Medication m
    ON p.medication_id = m.medication_id
WHERE p.patient_id = 1;
-- change id as necessary

-- GET PRESCRIPTION BY DOCTOR
SELECT p.prescription_id,
    s.name AS doctor_name,
    pt.name AS patient_name,
    m.name AS medication_name,
    p.date,
    p.status
FROM Prescription p
JOIN Doctor d
    ON p.doctor_id = d.staff_id
JOIN Staff s
    ON d.staff_id = s.staff_id
JOIN Patient pt
    ON p.patient_id = pt.patient_id
JOIN Medication m
    ON p.medication_id = m.medication_id
WHERE p.doctor_id = 1;

-- GET PENDING PRESCRIPTION
SELECT p.prescription_id,
    pt.name AS patient_name,
    m.name AS medication_name,
    s.name AS doctor_name,
    p.date,
    p.status
FROM Prescription p
JOIN Patient pt
    ON p.patient_id = pt.patient_id
JOIN Medication m
    ON p.medication_id = m.medication_id
JOIN Doctor d
    ON p.doctor_id = d.staff_id
JOIN Staff s
    ON d.staff_id = s.staff_id
WHERE p.status = 'Pending';

-- GET PRESCRIPTION BY PHARMACIST
SELECT p.prescription_id,
    pt.name AS patient_name,
    m.name AS medication_name,
    s.name AS pharmacist_name,
    p.date,
    p.status
FROM Prescription p
JOIN Patient pt
    ON p.patient_id = pt.patient_id
JOIN Medication m
    ON p.medication_id = m.medication_id
JOIN PharmacyStaff ps
    ON p.pharmacist_id = ps.staff_id
JOIN Staff s
    ON ps.staff_id = s.staff_id
WHERE p.pharmacist_id = 5;
 
-- GET PRESCRIPTION WITH MEDICATION DETAILS
SELECT p.prescription_id,
    pt.name AS patient_name,
    doc.name AS doctor_name,
    m.name AS medication,
    m.price,
    pharm.name AS pharmacist_name,
    p.date,
    p.status
FROM Prescription p
JOIN Patient pt
    ON p.patient_id = pt.patient_id
JOIN Staff doc
    ON p.doctor_id = doc.staff_id
JOIN Medication m
    ON p.medication_id = m.medication_id
LEFT JOIN Staff pharm
    ON p.pharmacist_id = pharm.staff_id;
    
-- GET COMPLETE PRESCRIPTION INFORMATION
SELECT p.prescription_id,
    pt.name AS patient_name,
    doc.name AS doctor_name,
    m.name AS medication_name,
    m.price,
    pharm.name AS pharmacist_name,
    p.date,
    p.status
FROM Prescription p
JOIN Patient pt
    ON p.patient_id = pt.patient_id
JOIN Doctor d
    ON p.doctor_id = d.staff_id
JOIN Staff doc
    ON d.staff_id = doc.staff_id
JOIN Medication m
    ON p.medication_id = m.medication_id
LEFT JOIN PharmacyStaff ps
    ON p.pharmacist_id = ps.staff_id
LEFT JOIN Staff pharm
    ON ps.staff_id = pharm.staff_id;
    
-- UPDATE PRESCRIPTION STATUS - PLACEHOLDER
UPDATE Prescription
SET status = 'Dispensed',
	pharmacist_id = 5
WHERE prescription_id = 4;

-- CREATE NEW PRESCRIPTION - PLACEHOLDER
INSERT INTO Prescription (
    patient_id, doctor_id, medication_id, pharmacist_id, `date`, status)
VALUES (1, 1, 2, NULL, CURDATE(), 'Pending');