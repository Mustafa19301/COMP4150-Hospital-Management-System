USE hospital_management;

-- Patient 1: John Smith
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 1, 1, '2026-10-25',
       'Elevated blood pressure',
       'Blood pressure monitoring and lifestyle recommendations',
       'Patient advised to monitor blood pressure regularly.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 1 AND doctor_id = 1
      AND record_date = '2026-10-25'
      AND diagnoses = 'Elevated blood pressure'
);

-- Patient 2: Jane Doe
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 2, 2, '2026-10-26',
       'Seasonal allergic rhinitis',
       'Allergy management and symptom monitoring',
       'Discussed common allergy triggers and follow-up if symptoms persist.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 2 AND doctor_id = 2
      AND record_date = '2026-10-26'
      AND diagnoses = 'Seasonal allergic rhinitis'
);

-- Patient 3: Robert Johnson
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 3, 8, '2026-10-27',
       'High cholesterol',
       'Dietary changes and regular lipid monitoring',
       'Discussed exercise and dietary adjustments.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 3 AND doctor_id = 8
      AND record_date = '2026-10-27'
      AND diagnoses = 'High cholesterol'
);

-- Patient 4: Mary Williams
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 4, 2, '2026-10-28',
       'Iron deficiency',
       'Dietary guidance and follow-up blood work',
       'Follow-up testing recommended to monitor iron levels.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 4 AND doctor_id = 2
      AND record_date = '2026-10-28'
      AND diagnoses = 'Iron deficiency'
);

-- Patient 5: David Brown
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 5, 1, '2026-10-29',
       'Hypertension follow-up',
       'Continued blood pressure management',
       'Reviewed blood pressure readings and ongoing management plan.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 5 AND doctor_id = 1
      AND record_date = '2026-10-29'
      AND diagnoses = 'Hypertension follow-up'
);

-- Patient 6: Michael Brown
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 6, 7, '2026-10-30',
       'Routine health assessment',
       'General health recommendations',
       'Routine assessment completed. Follow-up as needed.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 6 AND doctor_id = 7
      AND record_date = '2026-10-30'
      AND diagnoses = 'Routine health assessment'
);

-- Patient 7: Linda Garcia
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 7, 9, '2026-11-02',
       'Seasonal respiratory symptoms',
       'Symptom monitoring and supportive care',
       'Patient advised to return if symptoms worsen.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 7 AND doctor_id = 9
      AND record_date = '2026-11-02'
      AND diagnoses = 'Seasonal respiratory symptoms'
);

-- Patient 8: Noah Thompson
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 8, 9, '2026-11-04',
       'Routine pediatric assessment',
       'Routine monitoring and preventive care guidance',
       'Routine pediatric assessment completed.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 8 AND doctor_id = 9
      AND record_date = '2026-11-04'
      AND diagnoses = 'Routine pediatric assessment'
);

-- Patient 9: Ava Robinson
INSERT INTO MedicalRecord
(patient_id, doctor_id, record_date, diagnoses, treatments, medical_notes)
SELECT 9, 8, '2026-11-06',
       'General health consultation',
       'Lifestyle and preventive health recommendations',
       'Discussed general wellness and preventive care.'
WHERE NOT EXISTS (
    SELECT 1 FROM MedicalRecord
    WHERE patient_id = 9 AND doctor_id = 8
      AND record_date = '2026-11-06'
      AND diagnoses = 'General health consultation'
);

-- Verify resulting data
SELECT COUNT(*) AS TotalPatients FROM Patient;
SELECT COUNT(*) AS TotalMedicalRecords FROM MedicalRecord;