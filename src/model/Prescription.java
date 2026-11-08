package model;
import java.time.LocalDate;
public class Prescription {

	int prescription_id;
	int patient_id;
	int doctor_id;
	int medication_id;
	int pharmacist_id;
	LocalDate date;
	String status;
	
	public Prescription() {
		prescription_id = 0;
		patient_id = 0;
		doctor_id = 0;
		medication_id = 0;
		pharmacist_id = 0;
		status = "";
	}
	
	
	public Prescription(int prescription_id, int patient_id, int doctor_id,
						int medication_id, int pharmacist_id, LocalDate date, String status ) {
		
		this.prescription_id = prescription_id;
		this.patient_id = patient_id;
		this.doctor_id = doctor_id;
		this.medication_id = medication_id;
		this.pharmacist_id = pharmacist_id;
		this.date = date;
		this.status = status;
	}
	
	//GET FUNCTIONS
	public int getPrescription_id() {
		return prescription_id;
	}
	public int getPatient_id() {
		return patient_id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public int getMedication_id() {
		return medication_id;
	}
	public int getPharmacist_id() {
		return pharmacist_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public String getStatus_id() {
		return status;
	}
	
	//SET FUNCTIONS
	public void setPrescription_id(int prescription_id) {
		this.prescription_id = prescription_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public void setMedication_id(int medication_id) {
		this.medication_id = medication_id;
	}
	public void setPharmacist_id(int pharmacist_id) {
		this.pharmacist_id = pharmacist_id;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setStatus_id(String status) {
		this.status = status;
	}


}
