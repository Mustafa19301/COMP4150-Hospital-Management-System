USE hospital_management;

-- GET ALL BILLS
SELECT * FROM Bill;

-- GET UNPAID BILLS 
SELECT b.bill_id,
	b.amount,
    b.date,
    b.description,
    b.payment_status,
	pt.patient_id,
    pt.name AS patient_name
FROM Bill b
JOIN Patient pt
	ON b.patient_id = pt.patient_id
WHERE b.payment_status = 'Unpaid'
ORDER BY b.date DESC;

-- GET BILLS BY PATIENT
SELECT b.bill_id,
	b.amount,
    b.date,
    b.prescription_id,
    b.payment_status,
    pt.patient_id,
    pt.name AS patient_name
FROM Bill b
JOIN Patient pt
	ON b.patient_id = pt.patient_id
WHERE b.patient_id = 1;

-- CALCULATE TOTAL OUTSTANDING BALANCE
SELECT COALESCE(SUM(amount), 0) AS outstanding_balance
FROM Bill
WHERE payment_status = 'Unpaid';

-- GET BILLS ASSOCIATED WITH PRESCRIPTION
SELECT b.bill_id,
    pt.name AS patient_name,
    p.prescription_id,
    m.name AS medication_name,
    b.amount,
    b.date,
    b.description,
    b.payment_status
FROM Bill b
JOIN Patient pt
    ON b.patient_id = pt.patient_id
JOIN Prescription p
    ON b.prescription_id = p.prescription_id
JOIN Medication m
    ON p.medication_id = m.medication_id;
    
-- GET TOTAL OWED BY PATIENT
SELECT pt.patient_id,
    pt.name AS patient_name,
    COUNT(b.bill_id) AS total_bills,
    COALESCE(SUM(b.amount), 0) AS total_owed
FROM Patient pt
LEFT JOIN Bill b
    ON pt.patient_id = b.patient_id
GROUP BY
    pt.patient_id,
    pt.name;
    
    
-- GET BILLING HISTORY W/ PRESCRIPTION
SELECT b.bill_id,
    pt.name AS patient_name,
    p.prescription_id,
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
WHERE b.patient_id = 1
ORDER BY b.date DESC;


-- UPDATE BILL STATUS - PLCAEHOLDER
UPDATE Bill
SET payment_status = 'Paid'
WHERE bill_id = 3;

-- CREATE NEW BILL - PLACEHOLDER
INSERT INTO Bill (patient_id, prescription_id, amount, `date`, description,
	payment_status)
VALUES (1, NULL, 50.00, CURDATE(), 'General hospital service', 'Unpaid');