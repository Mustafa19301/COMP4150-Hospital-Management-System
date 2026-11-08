USE hospital_management;

-- 1. All of Dr. Yan's appointments
SELECT a.appointment_id,
       p.name  AS patient,
       n.name  AS nurse,
       a.`date`, a.`time`, a.reason, a.status
FROM Appointment a
JOIN Staff   d ON d.staff_id   = a.doctor_id
JOIN Patient p ON p.patient_id = a.patient_id
JOIN Staff   n ON n.staff_id   = a.nurse_id
WHERE d.username = 'xyan'
ORDER BY a.`date`, a.`time`;

-- 2. All appointments of one patient (Michael Brown)
SELECT a.appointment_id,
       d.name  AS doctor,
       n.name  AS nurse,
       a.`date`, a.`time`, a.reason, a.status
FROM Appointment a
JOIN Patient p ON p.patient_id = a.patient_id
JOIN Staff   d ON d.staff_id   = a.doctor_id
JOIN Staff   n ON n.staff_id   = a.nurse_id
WHERE p.name = 'Michael Brown'
ORDER BY a.`date`, a.`time`;

-- 3a. Number of active (not cancelled) appointments per doctor, busiest first.
SELECT s.name AS doctor,
       doc.specialization,
       COUNT(a.appointment_id) AS total_appointments
FROM Doctor doc
JOIN Staff s            ON s.staff_id  = doc.staff_id
LEFT JOIN Appointment a ON a.doctor_id = doc.staff_id
                       AND a.status   <> 'Cancelled'
GROUP BY doc.staff_id, s.name, doc.specialization
ORDER BY total_appointments DESC;

-- 3b. The busiest doctor (most active appointments)
SELECT s.name AS doctor,
       COUNT(*) AS total_appointments
FROM Appointment a
JOIN Staff s ON s.staff_id = a.doctor_id
WHERE a.status <> 'Cancelled'
GROUP BY a.doctor_id, s.name
ORDER BY total_appointments DESC
LIMIT 1;
