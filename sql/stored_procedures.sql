USE hospital_management;
DELIMITER $$

-- GetPatientPrescriptions
-- Retrieves all prescriptions for a patient

DROP PROCEDURE IF EXISTS GetPatientPrescriptions$$

CREATE PROCEDURE GetPatientPrescriptions(
    IN p_patient_id INT
)
BEGIN
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
        ON ps.staff_id = pharm.staff_id
    WHERE p.patient_id = p_patient_id;
END$$

-- GetPendingPrescriptions
-- Retrieves all processing prescriptions

DROP PROCEDURE IF EXISTS GetPendingPrescriptions$$

CREATE PROCEDURE GetPendingPrescriptions()
BEGIN
    SELECT p.prescription_id,
        pt.name AS patient_name,
        doc.name AS doctor_name,
        m.name AS medication_name,
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
        ON ps.staff_id = pharm.staff_id
    WHERE p.status = 'Pending';
END$$

-- GetUnpaidBills
-- Retrieves all unpaid bills

DROP PROCEDURE IF EXISTS GetUnpaidBills$$

CREATE PROCEDURE GetUnpaidBills()
BEGIN
    SELECT b.bill_id,
        pt.patient_id,
        pt.name AS patient_name,
        b.prescription_id,
        b.amount,
        b.date,
        b.description,
        b.payment_status
    FROM Bill b
    JOIN Patient pt
        ON b.patient_id = pt.patient_id
    WHERE b.payment_status = 'Unpaid'
    ORDER BY b.date DESC;
END$$

-- GetPatientBills
-- Retrieves all bills for a specific patient

DROP PROCEDURE IF EXISTS GetPatientBills$$

CREATE PROCEDURE GetPatientBills(
    IN p_patient_id INT
)
BEGIN
    SELECT b.bill_id,
        pt.name AS patient_name,
        b.prescription_id,
        m.name AS medication_name,
        b.amount,
        b.date,
        b.description,
        b.payment_status
    FROM Bill b
    JOIN Patient pt
        ON b.patient_id = pt.patient_id
    LEFT JOIN Prescription p
        ON b.prescription_id = p.prescription_id
    LEFT JOIN Medication m
        ON p.medication_id = m.medication_id
    WHERE b.patient_id = p_patient_id
    ORDER BY b.date DESC;
END$$

DELIMITER ;


