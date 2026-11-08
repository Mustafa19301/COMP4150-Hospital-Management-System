USE hospital_management;

DROP PROCEDURE IF EXISTS BookAppointment;

DELIMITER //

-- Books an appointment after checking that the patient, doctor and nurse exist
-- and that none of them already has an appointment at that date and time.
-- The result is returned in the OUT parameter message.
CREATE PROCEDURE BookAppointment(
    IN  patientId  INT,
    IN  doctorId   INT,
    IN  nurseId    INT,
    IN  apptDate   DATE,
    IN  apptTime   TIME,
    IN  apptReason VARCHAR(255),
    OUT message    VARCHAR(100)
)
BEGIN
    DECLARE patientCount INT;
    DECLARE doctorCount  INT;
    DECLARE nurseCount   INT;
    DECLARE doctorBusy   INT;
    DECLARE nurseBusy    INT;
    DECLARE patientBusy  INT;

    -- Do the patient, doctor and nurse exist?
    SELECT COUNT(*) INTO patientCount FROM Patient WHERE patient_id = patientId;
    SELECT COUNT(*) INTO doctorCount  FROM Doctor  WHERE staff_id   = doctorId;
    SELECT COUNT(*) INTO nurseCount   FROM Nurse   WHERE staff_id   = nurseId;

    -- Does anyone already have an active appointment at this date and time?
    SELECT COUNT(*) INTO doctorBusy FROM Appointment
    WHERE doctor_id = doctorId AND `date` = apptDate AND `time` = apptTime
      AND status <> 'Cancelled';

    SELECT COUNT(*) INTO nurseBusy FROM Appointment
    WHERE nurse_id = nurseId AND `date` = apptDate AND `time` = apptTime
      AND status <> 'Cancelled';

    SELECT COUNT(*) INTO patientBusy FROM Appointment
    WHERE patient_id = patientId AND `date` = apptDate AND `time` = apptTime
      AND status <> 'Cancelled';

    IF patientCount = 0 THEN
        SET message = 'Patient not found';
    ELSEIF doctorCount = 0 THEN
        SET message = 'Doctor not found';
    ELSEIF nurseCount = 0 THEN
        SET message = 'Nurse not found';
    ELSEIF doctorBusy > 0 THEN
        SET message = 'Doctor already has an appointment at this time';
    ELSEIF nurseBusy > 0 THEN
        SET message = 'Nurse already has an appointment at this time';
    ELSEIF patientBusy > 0 THEN
        SET message = 'Patient already has an appointment at this time';
    ELSE
        INSERT INTO Appointment (patient_id, doctor_id, nurse_id, `date`, `time`, reason, status)
        VALUES (patientId, doctorId, nurseId, apptDate, apptTime, apptReason, 'Scheduled');
        SET message = 'Appointment booked';
    END IF;
END //

DELIMITER ;
