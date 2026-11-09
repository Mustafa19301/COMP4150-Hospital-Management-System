USE hospital_management;

-- Run this file only once: a second run fails because usernames must be unique.
-- test passwords: doctors = doctor1/2/3, nurses = nurse1/2/3
INSERT INTO Staff (name, phone, username, password_hash, role) VALUES
    ('Dr. Xinlong Yan',   '519-555-0101', 'xyan',    SHA2('doctor1', 256), 'Doctor'),
    ('Dr. Marcus Ortiz',  '519-555-0102', 'mortiz',  SHA2('doctor2', 256), 'Doctor'),
    ('Dr. Priya Patel',   '519-555-0103', 'ppatel',  SHA2('doctor3', 256), 'Doctor'),
    ('Emily Brooks',      '519-555-0104', 'ebrooks', SHA2('nurse1', 256),  'Nurse'),
    ('Daniel Kim',        '519-555-0105', 'dkim',    SHA2('nurse2', 256),  'Nurse'),
    ('Olivia Martin',     '519-555-0106', 'omartin', SHA2('nurse3', 256),  'Nurse');

INSERT INTO Doctor (staff_id, specialization, department) VALUES
    ((SELECT staff_id FROM Staff WHERE username = 'xyan'),    'Family Medicine', 'General Medicine'),
    ((SELECT staff_id FROM Staff WHERE username = 'mortiz'),  'Cardiology',      'Cardiology'),
    ((SELECT staff_id FROM Staff WHERE username = 'ppatel'),  'Pediatrics',      'Pediatrics');

INSERT INTO Nurse (staff_id) VALUES
    ((SELECT staff_id FROM Staff WHERE username = 'ebrooks')),
    ((SELECT staff_id FROM Staff WHERE username = 'dkim')),
    ((SELECT staff_id FROM Staff WHERE username = 'omartin'));

SET @dr_yan    = (SELECT staff_id   FROM Staff   WHERE username = 'xyan');
SET @dr_ortiz  = (SELECT staff_id   FROM Staff   WHERE username = 'mortiz');
SET @dr_patel  = (SELECT staff_id   FROM Staff   WHERE username = 'ppatel');
SET @n_brooks  = (SELECT staff_id   FROM Staff   WHERE username = 'ebrooks');
SET @n_kim     = (SELECT staff_id   FROM Staff   WHERE username = 'dkim');
SET @n_martin  = (SELECT staff_id   FROM Staff   WHERE username = 'omartin');
SET @michael   = (SELECT patient_id FROM Patient WHERE phone = '519-555-0201');
SET @linda     = (SELECT patient_id FROM Patient WHERE phone = '519-555-0202');
SET @noah      = (SELECT patient_id FROM Patient WHERE phone = '519-555-0203');
SET @ava       = (SELECT patient_id FROM Patient WHERE phone = '519-555-0204');

INSERT INTO Appointment (patient_id, doctor_id, nurse_id, `date`, `time`, reason, status) VALUES
    (@michael, @dr_yan,    @n_brooks, '2026-10-20', '09:00:00', 'Annual check-up',      'Completed'),
    (@linda,   @dr_ortiz,  @n_kim,    '2026-10-22', '10:30:00', 'Chest pain',           'Completed'),
    (@noah,    @dr_patel,  @n_martin, '2026-10-27', '14:00:00', 'Fever and cough',      'Completed'),
    (@michael, @dr_ortiz,  @n_kim,    '2026-11-03', '11:00:00', 'ECG follow-up',        'Completed'),
    (@ava,     @dr_yan,    @n_brooks, '2026-11-05', '15:30:00', 'Sore throat',          'Cancelled'),
    (@michael, @dr_yan,    @n_brooks, '2026-11-10', '09:00:00', 'Blood test results',   'Scheduled'),
    (@linda,   @dr_yan,    @n_brooks, '2026-11-10', '09:30:00', 'Flu shot',             'Scheduled'),
    (@linda,   @dr_yan,    @n_brooks, '2026-11-10', '10:30:00', 'Back pain',            'Cancelled'),
    (@ava,     @dr_yan,    @n_brooks, '2026-11-10', '10:30:00', 'Seasonal allergies',   'Scheduled'),
    (@linda,   @dr_ortiz,  @n_kim,    '2026-11-12', '13:00:00', 'Blood pressure check', 'Scheduled'),
    (@noah,    @dr_patel,  @n_martin, '2026-11-17', '16:00:00', 'Vaccination',          'Scheduled'),
    (@michael, @dr_yan,    @n_brooks, '2026-11-20', '09:00:00', 'Follow-up',            'Scheduled');











