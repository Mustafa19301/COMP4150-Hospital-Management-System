USE hospital_management;

-- Test file for BookAppointment and the status triggers.
-- Run after staff_appointments.sql, the triggers file and the procedure file.
-- It removes the rows it adds, so it can be run again.

SET @dr_yan   = (SELECT staff_id   FROM Staff   WHERE username = 'xyan');
SET @n_brooks = (SELECT staff_id   FROM Staff   WHERE username = 'ebrooks');
SET @n_kim    = (SELECT staff_id   FROM Staff   WHERE username = 'dkim');
SET @noah     = (SELECT patient_id FROM Patient WHERE phone    = '519-555-0203');

-- 1. A valid booking: message should be 'Appointment booked'
CALL BookAppointment(@noah, @dr_yan, @n_brooks, '2026-12-01', '14:15:00', 'Ear infection', @msg);
SELECT @msg;

-- 2. Time Conflict
-- Dr. Yan already has an appointment on 2026-11-10 at 09:00:
-- message should be 'Doctor already has an appointment at this time'
CALL BookAppointment(@noah, @dr_yan, @n_kim, '2026-11-10', '09:00:00', 'Should not be booked', @msg);
SELECT @msg;

-- 3. Patient not existed 
-- Patient id 999 does not exist: message should be 'Patient not found'
CALL BookAppointment(999, @dr_yan, @n_kim, '2026-12-01', '15:00:00', 'Should not be booked', @msg);
SELECT @msg;

-- clean up the row this file added
-- Workbench's "safe update mode" blocks a DELETE whose WHERE doesn't use the
-- primary key, so turn it off for this one statement and back on afterwards.
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Appointment WHERE reason = 'Ear infection' AND `date` = '2026-12-01';
SET SQL_SAFE_UPDATES = 1;

-- 4. Triggers: these two INSERTs skip the procedure and must be rejected.
--    They are commented out so this file runs without errors.
--    To see a trigger work, remove the -- from one INSERT and run it on its own.
-- 4a. EXPECTED ERROR: 'Status must be Scheduled, Completed or Cancelled'
-- INSERT INTO Appointment (patient_id, doctor_id, nurse_id, `date`, `time`, reason, status)
-- VALUES (@noah, @dr_yan, @n_kim, '2026-12-02', '10:00:00', 'Trigger test', 'Canceled');

-- 4b. EXPECTED ERROR: 'Doctor already has an appointment at this time'
--     (Dr. Yan already has an appointment on 2026-11-10 at 09:00)
-- INSERT INTO Appointment (patient_id, doctor_id, nurse_id, `date`, `time`, reason, status)
-- VALUES (@noah, @dr_yan, @n_kim, '2026-11-10', '09:00:00', 'Trigger test', 'Scheduled');
