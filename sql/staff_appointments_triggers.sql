USE hospital_management;

DROP TRIGGER IF EXISTS AppointmentCheckInsert;
DROP TRIGGER IF EXISTS AppointmentCheckUpdate;

DELIMITER //

CREATE TRIGGER AppointmentCheckInsert
    BEFORE INSERT ON Appointment
    FOR EACH ROW
BEGIN
    DECLARE doctorBusy INT DEFAULT 0;
    DECLARE nurseBusy INT DEFAULT 0;
    DECLARE patientBusy INT DEFAULT 0;

    IF NEW.status NOT IN ('Scheduled', 'Completed', 'Cancelled') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Invalid appointment status';
    END IF;

    IF NEW.status <> 'Cancelled' THEN

        SELECT COUNT(*) INTO doctorBusy
        FROM Appointment
        WHERE doctor_id = NEW.doctor_id
          AND `date` = NEW.`date`
          AND `time` = NEW.`time`
          AND status <> 'Cancelled';

        IF doctorBusy > 0 THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Doctor already has an appointment at this time';
        END IF;

        SELECT COUNT(*) INTO nurseBusy
        FROM Appointment
        WHERE nurse_id = NEW.nurse_id
          AND `date` = NEW.`date`
          AND `time` = NEW.`time`
          AND status <> 'Cancelled';

        IF nurseBusy > 0 THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Nurse already has an appointment at this time';
        END IF;

        SELECT COUNT(*) INTO patientBusy
        FROM Appointment
        WHERE patient_id = NEW.patient_id
          AND `date` = NEW.`date`
          AND `time` = NEW.`time`
          AND status <> 'Cancelled';

        IF patientBusy > 0 THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Patient already has an appointment at this time';
        END IF;

    END IF;
END //

CREATE TRIGGER AppointmentCheckUpdate
    BEFORE UPDATE ON Appointment
    FOR EACH ROW
BEGIN
    DECLARE doctorBusy INT DEFAULT 0;
    DECLARE nurseBusy INT DEFAULT 0;
    DECLARE patientBusy INT DEFAULT 0;

    IF NEW.status NOT IN ('Scheduled', 'Completed', 'Cancelled') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Invalid appointment status';
    END IF;

    IF NEW.status <> 'Cancelled' THEN

        SELECT COUNT(*) INTO doctorBusy
        FROM Appointment
        WHERE doctor_id = NEW.doctor_id
          AND `date` = NEW.`date`
          AND `time` = NEW.`time`
          AND status <> 'Cancelled'
          AND appointment_id <> NEW.appointment_id;

        IF doctorBusy > 0 THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Doctor already has an appointment at this time';
        END IF;

        SELECT COUNT(*) INTO nurseBusy
        FROM Appointment
        WHERE nurse_id = NEW.nurse_id
          AND `date` = NEW.`date`
          AND `time` = NEW.`time`
          AND status <> 'Cancelled'
          AND appointment_id <> NEW.appointment_id;

        IF nurseBusy > 0 THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Nurse already has an appointment at this time';
        END IF;

        SELECT COUNT(*) INTO patientBusy
        FROM Appointment
        WHERE patient_id = NEW.patient_id
          AND `date` = NEW.`date`
          AND `time` = NEW.`time`
          AND status <> 'Cancelled'
          AND appointment_id <> NEW.appointment_id;

        IF patientBusy > 0 THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Patient already has an appointment at this time';
        END IF;

    END IF;
END //

DELIMITER ;