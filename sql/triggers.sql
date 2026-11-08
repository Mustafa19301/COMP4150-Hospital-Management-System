USE hospital_management;

-- RECORDS WHEN PRESCRIPTIONS ARE CREATED OR UPDATED
CREATE TABLE IF NOT EXISTS PrescriptionAudit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    prescription_id INT NOT NULL,
    action VARCHAR(30) NOT NULL,
    old_status VARCHAR(30),
    new_status VARCHAR(30),
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RECORDS CHANGES TO BILL PAYMENT STATUS
CREATE TABLE IF NOT EXISTS BillAudit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    action VARCHAR(30) NOT NULL,
    old_payment_status VARCHAR(30),
    new_payment_status VARCHAR(30),
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- IMPLEMENTATION
DELIMITER $$

-- EXECUTES AFTER PRESCRIPTION IS INSERTED
DROP TRIGGER IF EXISTS AfterPrescriptionInsert$$

CREATE TRIGGER AfterPrescriptionInsert
AFTER INSERT ON Prescription
FOR EACH ROW
BEGIN
    INSERT INTO PrescriptionAudit (prescription_id, action, old_status,new_status)
    VALUES (NEW.prescription_id, 'INSERT', NULL, NEW.status);
END$$

-- EXECUTES WHEN PRESCRIPTION IS UPDATED
DROP TRIGGER IF EXISTS AfterPrescriptionUpdate$$

CREATE TRIGGER AfterPrescriptionUpdate
AFTER UPDATE ON Prescription
FOR EACH ROW
BEGIN
    IF OLD.status <> NEW.status THEN

        INSERT INTO PrescriptionAudit (prescription_id, action, old_status, new_status)
        VALUES (NEW.prescription_id, 'STATUS UPDATE', OLD.status, NEW.status);
	END IF;
END$$

-- EXECUTES WHEN BILL IS UPDATED
DROP TRIGGER IF EXISTS AfterBillUpdate$$

CREATE TRIGGER AfterBillUpdate
AFTER UPDATE ON Bill
FOR EACH ROW
BEGIN
    IF OLD.payment_status <> NEW.payment_status THEN

        INSERT INTO BillAudit (bill_id, action, old_payment_status, new_payment_status)
        VALUES (NEW.bill_id, 'PAYMENT UPDATE', OLD.payment_status, NEW.payment_status);
	END IF;
END$$

DELIMITER ;

