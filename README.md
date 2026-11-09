# COMP4150-Hospital-Management-System
Hospital Management System developed for COMP4150 Fall 2026

Group Members:
- Mustafa Ahmed
- Murtaza Ahmed
- Xinglon Yan
- Michael Powell

## Project Overview
The Hospital Management System is designed to manage essential hospital operations through a relational database. We used
MySQL to store and organize information related to patients, medical staff, appointments, medical records, prescriptions,
medications and billing.

The system focuses on database design, SQL queries and triggers with a GUI (Planned soon)

## Technologies
- MySQL (Also MySQL Workbench)
- Java & JDBC (GUI)
- Git & GitHub (For Version Control)

## Database Features
- Staff
- Doctor
- Nurse
- PharmacyStaff
- Patient
- Appointment
- MedicalRecord
- Medication
- Prescription
- Bill
- BillAudit
- PrescriptionAudit

### Functionality
#### Patient Management
- Add, update, delete and retrieve patient information
- Search patients by ID, name and phone number
- Retrieve patient medical hisotires

#### Medical Records
- Create, update, delete and retrieve medical records
- Associate medical records with patient and doctors
- Retrieve medical records by doctor

#### Appointments
- Manage staff and appointment information
- Validate doctor, nurse and patient scheduling conflicts
- Support appointment booking through stored procedures

#### Prescriptions and Billing
- Manage prescription and billing information
- Track changes using audit tables and triggers
- Support queries for outstanding bills and prescription information

### Database integrity
- Preventing blank patient names
- Preventing future patient date of birth
- Preventing future medical record dates
- Preventing invalid appointment scheduling conflicts

## GUI (Soon)
Will be ready next week...