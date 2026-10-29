CREATE DATABASE IF NOT EXISTS hospital_management;
USE hospital_management;

CREATE TABLE IF NOT EXISTS Staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS Doctor (
    staff_id INT PRIMARY KEY,
    specialization VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    FOREIGN KEY (staff_id)
        REFERENCES Staff(staff_id)
);

CREATE TABLE IF NOT EXISTS Nurse (
    staff_id INT PRIMARY KEY,
    FOREIGN KEY (staff_id)
        REFERENCES Staff(staff_id)
);

CREATE TABLE IF NOT EXISTS PharmacyStaff (
    staff_id INT PRIMARY KEY,
    FOREIGN KEY (staff_id)
        REFERENCES Staff(staff_id)
);

CREATE TABLE IF NOT EXISTS Patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Appointment (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    nurse_id INT NOT NULL,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    reason VARCHAR(255),
    status VARCHAR(30) NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES Doctor(staff_id),
    FOREIGN KEY (nurse_id)
        REFERENCES Nurse(staff_id)
);

CREATE TABLE IF NOT EXISTS MedicalRecord (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    record_date DATE NOT NULL,
    diagnoses TEXT,
    treatments TEXT,
    medical_notes TEXT,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES Doctor(staff_id)
);

CREATE TABLE IF NOT EXISTS Medication (
    medication_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Prescription (
    prescription_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    medication_id INT NOT NULL,
    pharmacist_id INT NULL,
    `date` DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES Doctor(staff_id),
    FOREIGN KEY (medication_id)
        REFERENCES Medication(medication_id),
    FOREIGN KEY (pharmacist_id)
        REFERENCES PharmacyStaff(staff_id)
);

CREATE TABLE IF NOT EXISTS Bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    prescription_id INT NULL UNIQUE,
    amount DECIMAL(10, 2) NOT NULL,
    `date` DATE NOT NULL,
    description VARCHAR(255),
    payment_status VARCHAR(30) NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id),
    FOREIGN KEY (prescription_id)
        REFERENCES Prescription(prescription_id)
);

-- Display the tables in the selected database
SHOW TABLES;