package model;

import java.time.LocalDate;

public class Bill {

	int bill_id;
	int patient_id;
	int prescription_id;
	float amount;
	LocalDate date;
	String description;
	String payment_status;
	
	public Bill(int bill_id, int patient_id, int prescription_id, float amount,
				LocalDate date, String description, String payment_status) {
		this.bill_id = bill_id;
		this.patient_id = patient_id;
		this.prescription_id = prescription_id;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.payment_status = payment_status;
	}
	
	
	//GET FUNCTIONS
		public int getBill_id() {
			return bill_id;
		}
		public int getPatient_id() {
			return patient_id;
		}
		public int getPrescription_id() {
			return prescription_id;
		}
		public float getAmount() {
			return amount;
		}
		public LocalDate getDate() {
			return date;
		}
		public String getDescription() {
			return description;
		}
		public String getPayment_status() {
			return payment_status;
		}
		
		//SET FUNCTIONS
		public void setBill_id(int bill_id) {
			this.bill_id = bill_id;
		}
		public void setPatient_id(int patient_id) {
			this.patient_id = patient_id;
		}
		public void setPrescription_id(int prescription_id) {
			this.prescription_id = prescription_id;
		}
		public void setAmount(float amount) {
			this.amount = amount;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public void setPayment_status(String payment_status) {
			this.payment_status = payment_status;
		}
			
	
	
	
	
}
