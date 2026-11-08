USE hospital_management;

-- remove older versions of the triggers
DROP TRIGGER IF EXISTS AppointmentCheckInsert;
DROP TRIGGER IF EXISTS AppointmentCheckUpdate;

DELIMITER //

-- Runs before every new appointment is saved. It rejects the row when:
-- 1. status is not Scheduled, Completed or Cancelled
-- 2. the doctor already has an active appointment at the same date and time
-- SIGNAL SQLSTATE '45000' raises an error, so the INSERT is cancelled.
CREATE TRIGGER AppointmentCheckInsert
BEFORE INSERT
ON Appointment
FOR EACH ROW
BEGIN
    DECLARE doctorBusy INT;

    IF NEW.status NOT IN ('Scheduled', 'Completed', 'Cancelled') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Status must be Scheduled, Completed or Cancelled';
    END IF;

    SELECT COUNT(*) INTO doctorBusy FROM Appointment
    WHERE doctor_id = NEW.doctor_id AND `date` = NEW.`date` AND `time` = NEW.`time`
      AND status <> 'Cancelled';

    IF NEW.status <> 'Cancelled' AND doctorBusy > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Doctor already has an appointment at this time';
    END IF;
END //

-- Same two checks when an appointment is changed.
-- The row being changed is skipped, so it is not compared with itself.
CREATE TRIGGER AppointmentCheckUpdate
BEFORE UPDATE
ON Appointment
FOR EACH ROW
BEGIN
    DECLARE doctorBusy INT;

    IF NEW.status NOT IN ('Scheduled', 'Completed', 'Cancelled') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Status must be Scheduled, Completed or Cancelled';
    END IF;

    SELECT COUNT(*) INTO doctorBusy FROM Appointment
    WHERE doctor_id = NEW.doctor_id AND `date` = NEW.`date` AND `time` = NEW.`time`
      AND status <> 'Cancelled'
      AND appointment_id <> NEW.appointment_id;

    IF NEW.status <> 'Cancelled' AND doctorBusy > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Doctor already has an appointment at this time';
    END IF;
END //

DELIMITER ;
