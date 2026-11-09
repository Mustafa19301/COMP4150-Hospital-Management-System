USE hospital_management;

DROP TRIGGER IF EXISTS PatientValidateInsert;
DROP TRIGGER IF EXISTS PatientValidateUpdate;
DROP TRIGGER IF EXISTS MedicalRecordValidateInsert;
DROP TRIGGER IF EXISTS MedicalRecordValidateUpdate;

DELIMITER //

-- Validate a patient before insertion
CREATE TRIGGER PatientValidateInsert
    BEFORE INSERT ON Patient
    FOR EACH ROW
BEGIN
    IF NEW.name IS NULL OR TRIM(NEW.name) = '' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Patient name cannot be empty';
    END IF;

    IF NEW.date_of_birth > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Patient date of birth cannot be in the future';
    END IF;
END //

-- Validate a patient before updating
CREATE TRIGGER PatientValidateUpdate
    BEFORE UPDATE ON Patient
    FOR EACH ROW
BEGIN
    IF NEW.name IS NULL OR TRIM(NEW.name) = '' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Patient name cannot be empty';
    END IF;

    IF NEW.date_of_birth > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Patient date of birth cannot be in the future';
    END IF;
END //

-- Validate a medical record before insertion
CREATE TRIGGER MedicalRecordValidateInsert
    BEFORE INSERT ON MedicalRecord
    FOR EACH ROW
BEGIN
    IF NEW.record_date > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Medical record date cannot be in the future';
    END IF;

    IF (NEW.diagnoses IS NULL OR TRIM(NEW.diagnoses) = '')
        AND (NEW.treatments IS NULL OR TRIM(NEW.treatments) = '')
        AND (NEW.medical_notes IS NULL OR TRIM(NEW.medical_notes) = '') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'A medical record must contain a diagnosis, treatment, or medical note';
    END IF;
END //

-- Validate a medical record before updating
CREATE TRIGGER MedicalRecordValidateUpdate
    BEFORE UPDATE ON MedicalRecord
    FOR EACH ROW
BEGIN
    IF NEW.record_date > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Medical record date cannot be in the future';
    END IF;

    IF (NEW.diagnoses IS NULL OR TRIM(NEW.diagnoses) = '')
        AND (NEW.treatments IS NULL OR TRIM(NEW.treatments) = '')
        AND (NEW.medical_notes IS NULL OR TRIM(NEW.medical_notes) = '') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'A medical record must contain a diagnosis, treatment, or medical note';
    END IF;
END //

DELIMITER ;