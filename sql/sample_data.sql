
USE hospital_management;
-- STAFF
INSERT INTO Staff (name, phone, username, password_hash, role)
VALUES
('Dr. James Wilson', '519-555-1001', 'jwilson', 'demo_hash_001', 'Doctor'),
('Dr. Sarah Miller', '519-555-1002', 'smiller', 'demo_hash_002', 'Doctor'),
('Emily Brown', '519-555-1003', 'ebrown', 'demo_hash_003', 'Nurse'),
('Michael Davis', '519-555-1004', 'mdavis', 'demo_hash_004', 'Nurse'),
('Olivia Taylor', '519-555-1005', 'otaylor', 'demo_hash_005', 'PharmacyStaff'),
('Daniel White', '519-555-1006', 'dwhite', 'demo_hash_006', 'PharmacyStaff');

-- DOCTOR
INSERT INTO Doctor (staff_id, specialization, department)
VALUES
(1, 'Cardiology', 'Cardiology'),
(2, 'General Medicine', 'General Medicine');

-- NURSE
INSERT INTO Nurse (staff_id)
VALUES
(3),
(4);

-- PHARMACY STAFF
INSERT INTO PharmacyStaff (staff_id)
VALUES
(5),
(6);

-- PATIENT
INSERT INTO Patient (name, date_of_birth, phone, address)
VALUES
('John Smith', '1985-04-12', '519-555-2001', 'Windsor, Ontario'),
('Jane Doe', '1992-09-23', '519-555-2002', 'Windsor, Ontario'),
('Robert Johnson', '1978-02-15', '519-555-2003', 'LaSalle, Ontario'),
('Mary Williams', '2000-11-05', '519-555-2004', 'Windsor, Ontario'),
('David Brown', '1965-07-19', '519-555-2005', 'Tecumseh, Ontario');

-- MEDICATION
INSERT INTO Medication (name, price)
VALUES
('Amoxicillin', 15.00),
('Ibuprofen', 8.50),
('Metformin', 12.00),
('Atorvastatin', 18.00),
('Lisinopril', 14.00),
('Acetaminophen', 7.50);

-- APPOINTMENT
INSERT INTO Appointment
(patient_id, doctor_id, nurse_id, `date`, `time`, reason, status)
VALUES
(1, 1, 3, '2026-10-20', '09:00:00', 'Heart examination', 'Completed'),
(2, 2, 4, '2026-10-21', '10:30:00', 'General checkup', 'Completed'),
(3, 1, 3, '2026-10-22', '11:00:00', 'Follow-up consultation', 'Completed'),
(4, 2, 4, '2026-10-23', '13:00:00', 'Routine examination', 'Scheduled'),
(5, 1, 3, '2026-10-24', '14:00:00', 'Blood pressure check', 'Scheduled');

-- MEDICAL RECORD
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
VALUES
(1, 1, '2026-10-20', 'Hypertension', 'Blood pressure management', 'Follow-up recommended'),
(2, 2, '2026-10-21', 'Bacterial infection', 'Antibiotic treatment', 'Monitor symptoms'),
(3, 1, '2026-10-22', 'High cholesterol', 'Lifestyle changes', 'Regular monitoring recommended');

-- PRESCRIPTION
INSERT INTO Prescription
(patient_id, doctor_id, medication_id, pharmacist_id, `date`, status)
VALUES
(1, 1, 5, 5, '2026-10-20', 'Dispensed'),
(2, 2, 1, 5, '2026-10-21', 'Dispensed'),
(3, 1, 4, 6, '2026-10-22', 'Pending'),
(4, 2, 2, NULL, '2026-10-23', 'Pending'),
(5, 1, 3, 6, '2026-10-24', 'Dispensed');

-- BILL
INSERT INTO Bill
(patient_id, prescription_id, amount, `date`, description, payment_status)
VALUES
(1, 1, 14.00, '2026-10-20', 'Lisinopril prescription', 'Paid'),
(2, 2, 15.00, '2026-10-21', 'Amoxicillin prescription', 'Paid'),
(3, 3, 18.00, '2026-10-22', 'Atorvastatin prescription', 'Unpaid'),
(4, 4, 8.50, '2026-10-23', 'Ibuprofen prescription', 'Unpaid'),
(5, 5, 12.00, '2026-10-24', 'Metformin prescription', 'Paid');




-- VERIFICATION
SELECT * FROM Patient;
SELECT * FROM Medication;
SELECT * FROM Prescription;
SELECT * FROM Bill;
